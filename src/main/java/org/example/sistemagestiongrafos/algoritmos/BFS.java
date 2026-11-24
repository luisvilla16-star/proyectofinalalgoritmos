
package org.example.sistemagestiongrafos.algoritmos;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;
import java.util.ArrayList;

public class BFS {

    public static void recorrer(Grafo grafo, int inicio) {

        if (!grafo.existeVertice(inicio)) {
            System.out.println("Vertice inexistente");
            return;
        }

        boolean[] visitado = new boolean[grafo.getVertices().size()];
        int[] cola = new int[visitado.length];

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

        int frente = 0;
        int fin = 0;

        cola[fin] = inicio;
        fin++;

        visitado[inicio] = true;

        System.out.println("Recorrido BFS:");

        while (frente < fin) {

            int actual = cola[frente];
            frente++;

            System.out.println(grafo.getVertices().get(actual).getNombre());

            for (int i = 0; i < lista[actual].size(); i++) {

                int destino = lista[actual].get(i).getDestino();

                if (grafo.existeVertice(destino) && !visitado[destino]) {

                    visitado[destino] = true;

                    cola[fin] = destino;
                    fin++;
                }
            }
        }
    }
}
