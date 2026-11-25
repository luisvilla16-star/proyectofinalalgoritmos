
package org.example.sistemagestiongrafos.algoritmos;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;
import org.example.sistemagestiongrafos.modelo.Vertice;

import java.util.ArrayList;

public class DFS {

    public static void recorrer(Grafo grafo, int inicio) {

        if (!grafo.existeVertice(inicio)) {
            System.out.println("Vertice inexistente");
            return;
        }

        boolean[] visitado = new boolean[grafo.getVertices().size()];

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

        ArrayList<Vertice> vertices = grafo.getVertices();

        visitar(inicio, visitado, lista, vertices);
    }

    public static void visitar(int actual,
                               boolean[] visitado,
                               ArrayList<Arista>[] lista,
                               ArrayList<Vertice> vertices) {

        visitado[actual] = true;

        System.out.println(vertices.get(actual).getNombre());

        for (int i = 0; i < lista[actual].size(); i++) {

            int destino = lista[actual].get(i).getDestino();

            if (destino >= 0 && destino < visitado.length
                    && !visitado[destino]) {

                if (vertices.get(destino) != null) {
                    visitar(destino, visitado, lista, vertices);
                }
            }
        }
    }
}
