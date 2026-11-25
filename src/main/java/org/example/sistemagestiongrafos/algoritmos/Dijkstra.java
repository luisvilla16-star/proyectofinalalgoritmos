
package org.example.sistemagestiongrafos.algoritmos;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;
import java.util.ArrayList;

public class Dijkstra {

    public static void buscar(Grafo grafo, int inicio, int destino) {

        if (!grafo.existeVertice(inicio) || !grafo.existeVertice(destino)) {
            System.out.println("Vertice inexistente");
            return;
        }

        int n = grafo.getVertices().size();

        double[] distancia = new double[n];
        boolean[] visitado = new boolean[n];
        int[] anterior = new int[n];

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < lista[i].size(); j++) {

                double peso = lista[i].get(j).getPeso();

                if (peso < 0 || !Double.isFinite(peso)) {
                    System.out.println("El grafo contiene pesos invalidos");
                    return;
                }
            }

            distancia[i] = Double.MAX_VALUE;
            anterior[i] = -1;
        }

        distancia[inicio] = 0;

        for (int i = 0; i < n; i++) {

            int actual = -1;

            for (int j = 0; j < n; j++) {

                if (grafo.existeVertice(j) && !visitado[j] &&
                        (actual == -1 || distancia[j] < distancia[actual])) {
                    actual = j;
                }
            }

            if (actual == -1 || distancia[actual] == Double.MAX_VALUE) {
                break;
            }

            visitado[actual] = true;

            for (int j = 0; j < lista[actual].size(); j++) {

                Arista arista = lista[actual].get(j);
                int siguiente = arista.getDestino();

                double nueva = distancia[actual] + arista.getPeso();

                if (!visitado[siguiente] && nueva < distancia[siguiente]) {
                    distancia[siguiente] = nueva;
                    anterior[siguiente] = actual;
                }
            }
        }

        if (distancia[destino] == Double.MAX_VALUE) {
            System.out.println("No existe una ruta");
            return;
        }

        System.out.println("Costo total: " + distancia[destino]);
        System.out.println("Ruta:");

        mostrarRuta(anterior, destino);
    }

    public static void mostrarRuta(int[] anterior, int actual) {

        if (anterior[actual] != -1) {
            mostrarRuta(anterior, anterior[actual]);
        }

        System.out.print(actual + " ");
    }
}
