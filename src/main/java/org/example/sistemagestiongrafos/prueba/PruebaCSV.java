
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Vertice;
import org.example.sistemagestiongrafos.CSV.ImportarCSV;
import org.example.sistemagestiongrafos.CSV.ExportarCSV;

public class PruebaCSV {

    public static void main(String[] args) {

        // IMPORTAR ARCHIVO CSV
        System.out.println("IMPORTAR CSV:");

        Grafo grafo = ImportarCSV.importar(
                "src/main/resources/archivos/conexiones1.csv", false);

        if (grafo == null) {
            System.out.println("No se pudo importar el archivo");
            return;
        }

        // MOSTRAR VERTICES
        System.out.println("\nVERTICES:");

        for (int i = 0; i < grafo.getVertices().size(); i++) {

            Vertice vertice = grafo.getVertices().get(i);

            if (vertice != null) {
                System.out.println(vertice);
            }
        }

        // MOSTRAR CONEXIONES
        System.out.println("\nCONEXIONES:");

        for (int i = 0; i < grafo.getListaAdyacencia().length; i++) {

            for (int j = 0; j < grafo.getListaAdyacencia()[i].size(); j++) {

                if (grafo.getListaAdyacencia()[i].get(j).getOrigen() == i) {

                    System.out.println(
                            grafo.getListaAdyacencia()[i].get(j).getOrigen()
                                    + " - "
                                    + grafo.getListaAdyacencia()[i].get(j).getDestino()
                                    + " : "
                                    + grafo.getListaAdyacencia()[i].get(j).getPeso()
                    );
                }
            }
        }

        // EXPORTAR CSV
        System.out.println("\nEXPORTAR CSV:");

        ExportarCSV.exportar(
                grafo,
                "src/main/resources/archivos/prueba_exportacion.csv",
                false
        );

        System.out.println("Proceso terminado");
    }
}

