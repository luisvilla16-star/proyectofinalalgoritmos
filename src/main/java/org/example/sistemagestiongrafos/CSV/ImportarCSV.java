
package org.example.sistemagestiongrafos.CSV;

import org.example.sistemagestiongrafos.modelo.Grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportarCSV {

    public static Grafo importar(String archivo, boolean dirigido) {

        ArrayList<String> nombres = new ArrayList<>();

        try {

            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea;

            // Leer el archivo para obtener los vertices
            linea = lector.readLine();

            if (linea == null || !linea.replace("\uFEFF", "").trim()
                    .equalsIgnoreCase("origen,destino,peso")) {

                System.out.println("Error: encabezado incorrecto");
                lector.close();
                return null;
            }

            while ((linea = lector.readLine()) != null) {

                linea = linea.trim();

                if (linea.equals("")) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                if (datos.length != 3) {
                    System.out.println("Fila incorrecta: " + linea);
                    continue;
                }

                String origen = datos[0].trim();
                String destino = datos[1].trim();

                if (origen.equals("") || destino.equals("") ||
                        datos[2].trim().equals("")) {

                    System.out.println("Hay campos vacios: " + linea);
                    continue;
                }

                try {

                    double peso = Double.parseDouble(datos[2].trim());

                    if (peso >= 0 && Double.isFinite(peso)) {

                        if (!nombres.contains(origen)) {
                            nombres.add(origen);
                        }

                        if (!nombres.contains(destino)) {
                            nombres.add(destino);
                        }

                    } else {
                        System.out.println("Peso incorrecto: " + linea);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Peso incorrecto: " + linea);
                }
            }

            lector.close();

            // Crear el grafo
            Grafo grafo = new Grafo(nombres.size(), dirigido);

            for (int i = 0; i < nombres.size(); i++) {
                grafo.agregarVertice(nombres.get(i));
            }

            // Leer nuevamente el archivo
            lector = new BufferedReader(new FileReader(archivo));

            lector.readLine(); // Saltar encabezado

            while ((linea = lector.readLine()) != null) {

                linea = linea.trim();

                if (linea.equals("")) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                if (datos.length != 3) {
                    continue;
                }

                String origen = datos[0].trim();
                String destino = datos[1].trim();

                if (origen.equals("") || destino.equals("") ||
                        datos[2].trim().equals("")) {
                    continue;
                }

                try {

                    double peso = Double.parseDouble(datos[2].trim());

                    int idOrigen = nombres.indexOf(origen);
                    int idDestino = nombres.indexOf(destino);

                    if (idOrigen != -1 && idDestino != -1 &&
                            peso >= 0 && Double.isFinite(peso)) {

                        if (!grafo.agregarArista(idOrigen, idDestino, peso)) {
                            System.out.println("Conexion duplicada o incorrecta: " + linea);
                        }
                    }

                } catch (NumberFormatException e) {
                    // No se agrega la conexion
                }
            }

            lector.close();

            System.out.println("Importacion terminada");
            System.out.println("Vertices: " + nombres.size());

            int aristas = 0;

            for (int i = 0; i < nombres.size(); i++) {
                aristas += grafo.getListaAdyacencia()[i].size();
            }

            if (!dirigido) {
                aristas = aristas / 2;
            }

            System.out.println("Aristas: " + aristas);

            return grafo;

        } catch (IOException e) {

            System.out.println("Error al leer el archivo");
            return null;
        }
    }
}
