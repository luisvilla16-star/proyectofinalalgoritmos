
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.CSV.ImportarCSV;
public class PruebaImportarCSV {

    public static void main(String[] args) {

        Grafo grafo = ImportarCSV.importar(
                "src/main/resources/archivos/resultado.csv",
                false
        );

        if (grafo != null) {

            System.out.println("Grafo importado correctamente");

            System.out.println("Vertices:");

            for (int i = 0; i < grafo.getVertices().size(); i++) {

                if (grafo.existeVertice(i)) {
                    System.out.println(grafo.getVertices().get(i).getNombre());
                }
            }

            System.out.println("Matriz de adyacencia:");

            double[][] matriz = grafo.getMatrizAdyacencia();

            for (int i = 0; i < matriz.length; i++) {

                for (int j = 0; j < matriz.length; j++) {
                    System.out.print(matriz[i][j] + " ");
                }

                System.out.println();
            }
        }
    }
}
