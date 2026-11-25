
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.algoritmos.Dijkstra;

public class PruebaDijkstra {

    public static void main(String[] args) {

        Grafo grafo = new Grafo(4, false);

        grafo.agregarVertice("Biblioteca");
        grafo.agregarVertice("Laboratorio");
        grafo.agregarVertice("Cafeteria");
        grafo.agregarVertice("Auditorio");

        grafo.agregarArista(0, 1, 100);
        grafo.agregarArista(0, 2, 150);
        grafo.agregarArista(1, 3, 200);
        grafo.agregarArista(2, 3, 50);

        Dijkstra.buscar(grafo, 0, 3);
    }
}
