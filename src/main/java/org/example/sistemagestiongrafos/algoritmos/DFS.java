
package org.example.sistemagestiongrafos.algoritmos;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;
import java.util.ArrayList;

public class DFS {

    public static void recorrer(Grafo grafo, int inicio) {

        if (!grafo.existeVertice(inicio)) {
            System.out.println("Vertice inexistente");
            return;
        }

        boolean[] visitado = new boolean[grafo.getVertices().size()];

        visitar(grafo, inicio, visitado);
    }

    public static void visitar(Grafo grafo, int actual, boolean[] visitado) {

        visitado[actual] = true;

        System.out.println(grafo.getVertices().get(actual).getNombre());

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

        for (int i = 0; i < lista[actual].size(); i++) {

            int destino = lista[actual].get(i).getDestino();

            if (grafo.existeVertice(destino) && !visitado[destino]) {
                visitar(grafo, destino, visitado);
            }
        }
    }
}
