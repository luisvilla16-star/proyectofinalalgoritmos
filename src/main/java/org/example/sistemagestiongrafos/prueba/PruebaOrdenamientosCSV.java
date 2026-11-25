
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;
import org.example.sistemagestiongrafos.CSV.ImportarCSV;
import org.example.sistemagestiongrafos.algoritmos.Ordenar;

import java.util.ArrayList;

public class PruebaOrdenamientosCSV {

    public static void main(String[] args) {

        String[] archivos = {
                "src/main/resources/archivos/conexiones1.csv",
                "src/main/resources/archivos/conexiones2.csv",
                "src/main/resources/archivos/conexiones3.csv"
        };

        String resultado = "src/main/resources/archivos/resultados.csv";

        // CREAR ARCHIVO DE RESULTADOS
        try {
            java.io.FileWriter escritor = new java.io.FileWriter(resultado);

            escritor.write("Cantidad,ShellSort,QuickSort");
            escritor.write("\n");

            escritor.close();

        } catch (java.io.IOException e) {
            System.out.println("Error al crear el archivo de resultados");
            return;
        }

        // PROBAR CADA ARCHIVO
        for (int i = 0; i < archivos.length; i++) {

            System.out.println("\nArchivo: " + archivos[i]);

            Grafo grafo = ImportarCSV.importar(archivos[i], false);

            if (grafo != null) {

                // COMPROBAR CANTIDAD DE VERTICES
                System.out.println("Cantidad de vertices: "
                        + grafo.getVertices().size());

                // CONTAR CONEXIONES EN LA LISTA
                ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

                int totalLista = 0;

                for (int j = 0; j < lista.length; j++) {
                    totalLista = totalLista + lista[j].size();
                }

                System.out.println("Conexiones en lista: " + totalLista);

                // OBTENER ARISTAS SIN DUPLICAR
                Arista[] original = OrdenarAristas.obtenerAristas(grafo);

                System.out.println("Aristas obtenidas: " + original.length);

                // MEDIR TIEMPOS
                long tiempoShell = 0;
                long tiempoQuick = 0;

                for (int j = 0; j < 5; j++) {

                    Arista[] arregloShell = original.clone();
                    Arista[] arregloQuick = original.clone();

                    Ordenar<Arista> ordenar = new Ordenar<>();

                    long inicio = System.nanoTime();

                    ordenar.Shell(arregloShell, arregloShell.length);

                    long fin = System.nanoTime();

                    tiempoShell = tiempoShell + (fin - inicio);

                    inicio = System.nanoTime();

                    ordenar.recursivo(arregloQuick, 0, arregloQuick.length - 1);

                    fin = System.nanoTime();

                    tiempoQuick = tiempoQuick + (fin - inicio);
                }

                // CALCULAR PROMEDIOS
                long promedioShell = tiempoShell / 5;
                long promedioQuick = tiempoQuick / 5;

                System.out.println("Promedio ShellSort: "
                        + promedioShell + " nanosegundos");

                System.out.println("Promedio QuickSort: "
                        + promedioQuick + " nanosegundos");

                // GUARDAR RESULTADOS
                GuardarResultados.guardar(
                        resultado,
                        original.length,
                        promedioShell,
                        promedioQuick
                );
            }
        }
    }
}
