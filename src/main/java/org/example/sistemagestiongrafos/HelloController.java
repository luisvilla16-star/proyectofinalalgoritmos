
package org.example.sistemagestiongrafos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import org.example.sistemagestiongrafos.modelo.*;
import org.example.sistemagestiongrafos.algoritmos.*;
import org.example.sistemagestiongrafos.CSV.*;

import java.io.File;
import java.util.ArrayList;

public class HelloController {

    private Grafo grafo;
    private Pane panelGrafo;
    private TextArea resultados;
    private PieChart graficaTiempos;

    private boolean dirigido = false;

    private final int MAX_VERTICES = 30;

    public HelloController() {
        grafo = new Grafo(MAX_VERTICES, dirigido);
    }

    // CREAR LA INTERFAZ
    public Parent crearVista() {

        BorderPane principal = new BorderPane();

        VBox menu = new VBox(8);
        menu.setPadding(new Insets(15));
        menu.setPrefWidth(230);
        menu.setStyle("-fx-background-color: #eeeeee;");

        Label titulo = new Label("GESTIÓN DE GRAFOS");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        CheckBox dirigidoCheck = new CheckBox("Grafo dirigido");

        dirigidoCheck.setOnAction(e -> {
            cambiarTipoGrafo(dirigidoCheck.isSelected());
        });

        menu.getChildren().addAll(
                titulo,
                dirigidoCheck,
                new Separator(),

                new Label("VÉRTICES"),
                boton("Agregar vértice", e -> agregarVertice()),
                boton("Modificar vértice", e -> modificarVertice()),
                boton("Eliminar vértice", e -> eliminarVertice()),

                new Separator(),

                new Label("ARISTAS"),
                boton("Agregar arista", e -> agregarArista()),
                boton("Modificar arista", e -> modificarArista()),
                boton("Eliminar arista", e -> eliminarArista()),

                new Separator(),

                new Label("ALGORITMOS"),
                boton("BFS", e -> ejecutarBFS()),
                boton("DFS", e -> ejecutarDFS()),
                boton("Dijkstra", e -> ejecutarDijkstra()),
                boton("QuickSort", e -> ordenar(true)),
                boton("ShellSort", e -> ordenar(false)),

                new Separator(),

                new Label("ARCHIVOS"),
                boton("Importar CSV", e -> importarCSV()),
                boton("Exportar CSV", e -> exportarCSV()),

                new Separator(),

                new Label("CONSULTAS"),
                boton("Lista de adyacencia", e -> mostrarLista()),
                boton("Matriz de adyacencia", e -> mostrarMatriz()),
                boton("Comparar tiempos de algoritmos", e -> medirTiempos())
        );

        ScrollPane scrollMenu = new ScrollPane(menu);
        scrollMenu.setFitToWidth(true);
        scrollMenu.setPrefWidth(250);

        principal.setLeft(scrollMenu);

        VBox derecha = new VBox(10);
        derecha.setPadding(new Insets(10));

        Label tituloGrafo = new Label("Representación gráfica del grafo");
        tituloGrafo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button limpiar = new Button("Limpiar todo");
        limpiar.setOnAction(e -> limpiarTodo());

        HBox parteSuperior = new HBox(10);
        parteSuperior.setAlignment(Pos.CENTER_LEFT);
        parteSuperior.getChildren().addAll(tituloGrafo, limpiar);

        panelGrafo = new Pane();
        panelGrafo.setPrefSize(850, 450);
        panelGrafo.setMinHeight(350);
        panelGrafo.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: gray;"
        );

        resultados = new TextArea();
        resultados.setEditable(false);
        resultados.setPrefRowCount(6);

        graficaTiempos = new PieChart();
        graficaTiempos.setTitle("Tiempos de ejecución de los algoritmos");
        graficaTiempos.setPrefHeight(300);
        graficaTiempos.setLabelsVisible(true);

        derecha.getChildren().addAll(
                parteSuperior,
                panelGrafo,
                new Label("Resultados"),
                resultados,
                graficaTiempos
        );

        ScrollPane scrollDerecha = new ScrollPane(derecha);
        scrollDerecha.setFitToWidth(true);
        scrollDerecha.setFitToHeight(false);

        principal.setCenter(scrollDerecha);

        return principal;
    }

    // CREAR BOTONES
    private Button boton(String nombre,
                         javafx.event.EventHandler<javafx.event.ActionEvent> accion) {

        Button b = new Button(nombre);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setOnAction(accion);

        return b;
    }

    // PEDIR UN DATO
    private String pedirDato(String mensaje) {

        TextInputDialog dialogo = new TextInputDialog();

        dialogo.setTitle("Sistema de grafos");
        dialogo.setHeaderText(mensaje);

        return dialogo.showAndWait().orElse(null);
    }

    // CAMBIAR ENTRE GRAFO DIRIGIDO Y NO DIRIGIDO
    private void cambiarTipoGrafo(boolean nuevoTipo) {

        ArrayList<Vertice> vertices = grafo.getVertices();
        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

        Grafo nuevoGrafo = new Grafo(MAX_VERTICES, nuevoTipo);

        // Guardar los nuevos ID de los vértices
        int[] nuevosId = new int[vertices.size()];

        for (int i = 0; i < nuevosId.length; i++) {
            nuevosId[i] = -1;
        }

        // Copiar los vértices
        for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i) != null) {

                int id = nuevoGrafo.agregarVertice(
                        vertices.get(i).getNombre()
                );

                nuevosId[i] = id;
            }
        }

        // Copiar las aristas
        for (int i = 0; i < lista.length; i++) {

            if (nuevosId[i] != -1) {

                for (Arista a : lista[i]) {

                    int origen = nuevosId[a.getOrigen()];
                    int destino = nuevosId[a.getDestino()];

                    if (origen != -1 && destino != -1) {

                        nuevoGrafo.agregarArista(
                                origen,
                                destino,
                                a.getPeso()
                        );
                    }
                }
            }
        }

        grafo = nuevoGrafo;
        dirigido = nuevoTipo;

        dibujarGrafo();

        if (dirigido) {
            resultados.setText("El grafo ahora es dirigido.");
        } else {
            resultados.setText("El grafo ahora es no dirigido.");
        }
    }

    // LIMPIAR TODO EL GRAFO
    private void limpiarTodo() {

        grafo = new Grafo(MAX_VERTICES, dirigido);

        panelGrafo.getChildren().clear();
        resultados.clear();
        graficaTiempos.getData().clear();

        resultados.setText("Se eliminó toda la información del grafo.");
    }

    // AGREGAR VÉRTICE
    private void agregarVertice() {

        String nombre = pedirDato("Nombre del vértice");

        if (nombre != null) {

            int id = grafo.agregarVertice(nombre);

            if (id != -1) {

                resultados.setText(
                        "Vértice agregado correctamente.\nID: " + id
                );

                dibujarGrafo();

            } else {

                resultados.setText("No se pudo agregar el vértice.");
            }
        }
    }

    // MODIFICAR VÉRTICE
    private void modificarVertice() {

        try {

            String dato = pedirDato("ID del vértice");

            if (dato == null) {
                return;
            }

            int id = Integer.parseInt(dato);

            String nombre = pedirDato("Nuevo nombre");

            if (nombre == null) {
                return;
            }

            if (grafo.modificarVertice(id, nombre)) {

                resultados.setText("Vértice modificado correctamente.");
                dibujarGrafo();

            } else {

                resultados.setText("No se pudo modificar el vértice.");
            }

        } catch (Exception e) {

            resultados.setText("ID incorrecto.");
        }
    }

    // ELIMINAR VÉRTICE
    private void eliminarVertice() {

        try {

            String dato = pedirDato("ID del vértice");

            if (dato == null) {
                return;
            }

            int id = Integer.parseInt(dato);

            if (grafo.eliminarVertice(id)) {

                resultados.setText("Vértice eliminado correctamente.");
                dibujarGrafo();

            } else {

                resultados.setText("No se pudo eliminar el vértice.");
            }

        } catch (Exception e) {

            resultados.setText("ID incorrecto.");
        }
    }

    // AGREGAR ARISTA
    private void agregarArista() {
        manejarArista(1);
    }

    // MODIFICAR ARISTA
    private void modificarArista() {
        manejarArista(2);
    }

    // ELIMINAR ARISTA
    private void eliminarArista() {
        manejarArista(3);
    }

    // OPERACIONES CON ARISTAS
    private void manejarArista(int opcion) {

        try {

            String datoOrigen = pedirDato("ID del vértice de origen");

            if (datoOrigen == null) {
                return;
            }

            int origen = Integer.parseInt(datoOrigen);

            String datoDestino = pedirDato("ID del vértice de destino");

            if (datoDestino == null) {
                return;
            }

            int destino = Integer.parseInt(datoDestino);

            boolean correcto;

            if (opcion == 3) {

                correcto = grafo.eliminarArista(origen, destino);

            } else {

                String datoPeso = pedirDato("Peso de la arista");

                if (datoPeso == null) {
                    return;
                }

                double peso = Double.parseDouble(datoPeso);

                if (opcion == 1) {

                    correcto = grafo.agregarArista(origen, destino, peso);

                } else {

                    correcto = grafo.modificarArista(origen, destino, peso);
                }
            }

            if (correcto) {

                resultados.setText("Operación realizada correctamente.");
                dibujarGrafo();

            } else {

                resultados.setText("No se pudo realizar la operación.");
            }

        } catch (Exception e) {

            resultados.setText("Datos incorrectos.");
        }
    }

    // DIBUJAR EL GRAFO
    private void dibujarGrafo() {

        panelGrafo.getChildren().clear();

        ArrayList<Vertice> vertices = grafo.getVertices();
        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

        int cantidad = 0;

        for (Vertice v : vertices) {

            if (v != null) {
                cantidad++;
            }
        }

        if (cantidad == 0) {
            return;
        }

        double centroX = panelGrafo.getWidth() / 2;
        double centroY = panelGrafo.getHeight() / 2;

        if (centroX == 0 || centroY == 0) {
            centroX = 400;
            centroY = 220;
        }

        double radio = 160;

        double[] x = new double[vertices.size()];
        double[] y = new double[vertices.size()];

        int posicion = 0;

        // CALCULAR POSICIONES
        for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i) != null) {

                double angulo = 2 * Math.PI * posicion / cantidad;

                x[i] = centroX + radio * Math.cos(angulo);
                y[i] = centroY + radio * Math.sin(angulo);

                posicion++;
            }
        }

        // DIBUJAR ARISTAS
        for (int i = 0; i < lista.length; i++) {

            if (!grafo.existeVertice(i)) {
                continue;
            }

            for (Arista a : lista[i]) {

                int destino = a.getDestino();

                if (!grafo.existeVertice(destino)) {
                    continue;
                }

                if (!grafo.esDirigido() && i > destino) {
                    continue;
                }

                dibujarArista(
                        x[i], y[i],
                        x[destino], y[destino],
                        a.getPeso(),
                        grafo.esDirigido()
                );
            }
        }

        // DIBUJAR VÉRTICES
        for (int i = 0; i < vertices.size(); i++) {

            Vertice v = vertices.get(i);

            if (v != null) {

                Circle circulo = new Circle(x[i], y[i], 25);

                circulo.setFill(Color.LIGHTBLUE);
                circulo.setStroke(Color.DARKBLUE);

                Text nombre = new Text(
                        x[i] - 20,
                        y[i] + 5,
                        v.getNombre()
                );

                panelGrafo.getChildren().addAll(circulo, nombre);
            }
        }
    }

    // DIBUJAR UNA ARISTA
    private void dibujarArista(double x1, double y1,
                               double x2, double y2,
                               double peso, boolean tieneFlecha) {

        double dx = x2 - x1;
        double dy = y2 - y1;

        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia == 0) {
            return;
        }

        double ux = dx / distancia;
        double uy = dy / distancia;

        // Evitar que la línea entre dentro de los círculos
        double inicioX = x1 + ux * 25;
        double inicioY = y1 + uy * 25;

        double finalX = x2 - ux * 25;
        double finalY = y2 - uy * 25;

        Line linea = new Line(
                inicioX, inicioY,
                finalX, finalY
        );

        linea.setStroke(Color.GRAY);

        Text textoPeso = new Text(
                (x1 + x2) / 2,
                (y1 + y2) / 2,
                String.valueOf(peso)
        );

        panelGrafo.getChildren().addAll(linea, textoPeso);

        if (tieneFlecha) {
            dibujarFlecha(finalX, finalY, ux, uy);
        }
    }

    // DIBUJAR LA PUNTA DE LA FLECHA
    private void dibujarFlecha(double x, double y,
                               double ux, double uy) {

        double tamano = 12;

        double angulo = Math.PI / 6;

        double x1 = x - tamano * (ux * Math.cos(angulo) - uy * Math.sin(angulo));
        double y1 = y - tamano * (uy * Math.cos(angulo) + ux * Math.sin(angulo));

        double x2 = x - tamano * (ux * Math.cos(angulo) + uy * Math.sin(angulo));
        double y2 = y - tamano * (uy * Math.cos(angulo) - ux * Math.sin(angulo));

        Line flecha1 = new Line(x, y, x1, y1);
        Line flecha2 = new Line(x, y, x2, y2);

        flecha1.setStroke(Color.DARKBLUE);
        flecha2.setStroke(Color.DARKBLUE);

        panelGrafo.getChildren().addAll(flecha1, flecha2);
    }

    // EJECUTAR BFS
    private void ejecutarBFS() {

        try {

            String dato = pedirDato("ID inicial para BFS");

            if (dato == null) {
                return;
            }

            int inicio = Integer.parseInt(dato);

            if (!grafo.existeVertice(inicio)) {
                resultados.setText("El vértice no existe.");
                return;
            }

            resultados.setText("El resultado de BFS aparece en la consola.");
            BFS.recorrer(grafo, inicio);

        } catch (Exception e) {

            resultados.setText("ID incorrecto.");
        }
    }

    // EJECUTAR DFS
    private void ejecutarDFS() {

        try {

            String dato = pedirDato("ID inicial para DFS");

            if (dato == null) {
                return;
            }

            int inicio = Integer.parseInt(dato);

            if (!grafo.existeVertice(inicio)) {
                resultados.setText("El vértice no existe.");
                return;
            }

            resultados.setText("El resultado de DFS aparece en la consola.");
            DFS.recorrer(grafo, inicio);

        } catch (Exception e) {

            resultados.setText("ID incorrecto.");
        }
    }

    // EJECUTAR DIJKSTRA
    private void ejecutarDijkstra() {

        try {

            String datoInicio = pedirDato("ID inicial");

            if (datoInicio == null) {
                return;
            }

            int inicio = Integer.parseInt(datoInicio);

            String datoDestino = pedirDato("ID destino");

            if (datoDestino == null) {
                return;
            }

            int destino = Integer.parseInt(datoDestino);

            if (!grafo.existeVertice(inicio) ||
                    !grafo.existeVertice(destino)) {

                resultados.setText("Uno de los vértices no existe.");
                return;
            }

            resultados.setText(
                    "El resultado de Dijkstra aparece en la consola."
            );

            Dijkstra.buscar(grafo, inicio, destino);

        } catch (Exception e) {

            resultados.setText("Datos incorrectos.");
        }
    }

    // ORDENAR ARISTAS
    private void ordenar(boolean quick) {

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

        ArrayList<Arista> aristas = new ArrayList<>();

        for (int i = 0; i < lista.length; i++) {

            for (Arista a : lista[i]) {

                if (grafo.esDirigido() || i < a.getDestino()) {
                    aristas.add(a);
                }
            }
        }

        Arista[] arreglo = aristas.toArray(new Arista[0]);

        Ordenar<Arista> ordenar = new Ordenar<>();

        if (quick) {
            ordenar.recursivo(arreglo, 0, arreglo.length - 1);
        } else {
            ordenar.Shell(arreglo, arreglo.length);
        }

        String texto;

        if (quick) {
            texto = "RESULTADO DE QUICKSORT\n\n";
        } else {
            texto = "RESULTADO DE SHELLSORT\n\n";
        }

        for (Arista a : arreglo) {

            texto += a.getOrigen() + " - " +
                    a.getDestino() + " : " +
                    a.getPeso() + "\n";
        }

        resultados.setText(texto);
    }

    // IMPORTAR CSV
    private void importarCSV() {

        FileChooser selector = new FileChooser();
        selector.setTitle("Importar CSV");

        File archivo = selector.showOpenDialog(
                panelGrafo.getScene().getWindow()
        );

        if (archivo != null) {

            Grafo nuevo = ImportarCSV.importar(
                    archivo.getAbsolutePath(),
                    dirigido
            );

            if (nuevo != null) {

                grafo = nuevo;

                resultados.setText("CSV importado correctamente.");
                dibujarGrafo();

            } else {

                resultados.setText("No se pudo importar el archivo.");
            }
        }
    }

    // EXPORTAR CSV
    private void exportarCSV() {

        FileChooser selector = new FileChooser();

        selector.setTitle("Exportar CSV");
        selector.setInitialFileName("grafo.csv");

        File archivo = selector.showSaveDialog(
                panelGrafo.getScene().getWindow()
        );

        if (archivo != null) {

            ExportarCSV.exportar(
                    grafo,
                    archivo.getAbsolutePath(),
                    dirigido
            );

            resultados.setText("CSV exportado.");
        }
    }

    // MOSTRAR LISTA DE ADYACENCIA
    private void mostrarLista() {

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();
        ArrayList<Vertice> vertices = grafo.getVertices();

        String texto = "LISTA DE ADYACENCIA\n\n";

        for (int i = 0; i < lista.length; i++) {

            if (grafo.existeVertice(i)) {

                texto += vertices.get(i).getNombre() + ": ";

                for (Arista a : lista[i]) {

                    texto += vertices.get(a.getDestino()).getNombre();
                    texto += " (" + a.getPeso() + "), ";
                }

                texto += "\n";
            }
        }

        resultados.setText(texto);
    }

    // MOSTRAR MATRIZ DE ADYACENCIA
    private void mostrarMatriz() {

        double[][] matriz = grafo.getMatrizAdyacencia();
        ArrayList<Vertice> vertices = grafo.getVertices();

        String texto = "MATRIZ DE ADYACENCIA\n\n";

        texto += "\t";

        for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i) != null) {
                texto += vertices.get(i).getNombre() + "\t";
            }
        }

        texto += "\n";

        for (int i = 0; i < matriz.length; i++) {

            if (vertices.get(i) != null) {

                texto += vertices.get(i).getNombre() + "\t";

                for (int j = 0; j < matriz[i].length; j++) {

                    if (vertices.get(j) != null) {
                        texto += matriz[i][j] + "\t";
                    }
                }

                texto += "\n";
            }
        }

        resultados.setText(texto);
    }

    // MEDIR LOS TIEMPOS DE LOS CINCO ALGORITMOS
    private void medirTiempos() {

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();
        ArrayList<Vertice> vertices = grafo.getVertices();

        ArrayList<Arista> aristas = new ArrayList<>();

        for (int i = 0; i < lista.length; i++) {

            for (Arista a : lista[i]) {

                if (grafo.esDirigido() || i < a.getDestino()) {
                    aristas.add(a);
                }
            }
        }

        int inicio = -1;
        int destino = -1;

        for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i) != null) {

                if (inicio == -1) {
                    inicio = i;
                }

                destino = i;
            }
        }

        if (inicio == -1 || inicio == destino) {

            resultados.setText(
                    "Se necesitan al menos dos vértices."
            );

            return;
        }

        Ordenar<Arista> ordenar = new Ordenar<>();

        long tiempoQuick = 0;
        long tiempoShell = 0;
        long tiempoBFS = 0;
        long tiempoDFS = 0;
        long tiempoDijkstra = 0;

        int repeticiones = 5;

        // MEDIR QUICKSORT
        for (int i = 0; i < repeticiones; i++) {

            Arista[] copia = aristas.toArray(new Arista[0]);

            long inicioTiempo = System.nanoTime();

            if (copia.length > 1) {
                ordenar.recursivo(copia, 0, copia.length - 1);
            }

            tiempoQuick += System.nanoTime() - inicioTiempo;
        }

        // MEDIR SHELLSORT
        for (int i = 0; i < repeticiones; i++) {

            Arista[] copia = aristas.toArray(new Arista[0]);

            long inicioTiempo = System.nanoTime();

            ordenar.Shell(copia, copia.length);

            tiempoShell += System.nanoTime() - inicioTiempo;
        }

        // MEDIR BFS
        for (int i = 0; i < repeticiones; i++) {

            long inicioTiempo = System.nanoTime();

            BFS.recorrer(grafo, inicio);

            tiempoBFS += System.nanoTime() - inicioTiempo;
        }

        // MEDIR DFS
        for (int i = 0; i < repeticiones; i++) {

            long inicioTiempo = System.nanoTime();

            DFS.recorrer(grafo, inicio);

            tiempoDFS += System.nanoTime() - inicioTiempo;
        }

        // MEDIR DIJKSTRA
        for (int i = 0; i < repeticiones; i++) {

            long inicioTiempo = System.nanoTime();

            Dijkstra.buscar(grafo, inicio, destino);

            tiempoDijkstra += System.nanoTime() - inicioTiempo;
        }

        // CALCULAR PROMEDIOS
        tiempoQuick = tiempoQuick / repeticiones;
        tiempoShell = tiempoShell / repeticiones;
        tiempoBFS = tiempoBFS / repeticiones;
        tiempoDFS = tiempoDFS / repeticiones;
        tiempoDijkstra = tiempoDijkstra / repeticiones;

        String texto = "TIEMPOS PROMEDIO DE EJECUCIÓN\n\n";

        texto += "QuickSort: " + tiempoQuick + " ns\n";
        texto += "ShellSort: " + tiempoShell + " ns\n";
        texto += "BFS: " + tiempoBFS + " ns\n";
        texto += "DFS: " + tiempoDFS + " ns\n";
        texto += "Dijkstra: " + tiempoDijkstra + " ns\n";

        resultados.setText(texto);

        // CREAR GRÁFICA DE PASTEL
        graficaTiempos.getData().clear();

        graficaTiempos.getData().add(
                new PieChart.Data("QuickSort", tiempoQuick)
        );

        graficaTiempos.getData().add(
                new PieChart.Data("ShellSort", tiempoShell)
        );

        graficaTiempos.getData().add(
                new PieChart.Data("BFS", tiempoBFS)
        );

        graficaTiempos.getData().add(
                new PieChart.Data("DFS", tiempoDFS)
        );

        graficaTiempos.getData().add(
                new PieChart.Data("Dijkstra", tiempoDijkstra)
        );
    }
}
