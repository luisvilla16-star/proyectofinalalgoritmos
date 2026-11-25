
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
            while ((linea = lector.readLine()) != null) {

                linea = linea.replace("\uFEFF", "").trim();

                if (linea.equals("")) {
                    continue;
                }

                if (linea.toLowerCase().startsWith("origen,")) {
                    continue;
                }

                String[] datos = linea.split(",");

                if (datos.length == 3) {

                    String origen = datos[0].trim();
                    String destino = datos[1].trim();

                    try {

                        double peso = Double.parseDouble(datos[2].trim());

                        if (peso >= 0 && !origen.equals("") && !destino.equals("")) {

                            if (!nombres.contains(origen)) {
                                nombres.add(origen);
                            }

                            if (!nombres.contains(destino)) {
                                nombres.add(destino);
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Peso incorrecto: " + linea);
                    }
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

            while ((linea = lector.readLine()) != null) {

                linea = linea.replace("\uFEFF", "").trim();

                if (linea.equals("")) {
                    continue;
                }

                if (linea.toLowerCase().startsWith("origen,")) {
                    continue;
                }

                String[] datos = linea.split(",");

                if (datos.length == 3) {

                    String origen = datos[0].trim();
                    String destino = datos[1].trim();

                    try {

                        double peso = Double.parseDouble(datos[2].trim());

                        int idOrigen = nombres.indexOf(origen);
                        int idDestino = nombres.indexOf(destino);

                        if (idOrigen != -1 && idDestino != -1 && peso >= 0) {
                            grafo.agregarArista(idOrigen, idDestino, peso);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Peso incorrecto: " + linea);
                    }
                }
            }

            lector.close();

            return grafo;

        } catch (IOException e) {

            System.out.println("Error al leer el archivo");
            return null;
        }
    }
}
