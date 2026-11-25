
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.algoritmos.BFS;
import org.example.sistemagestiongrafos.algoritmos.DFS;
import org.example.sistemagestiongrafos.algoritmos.Dijkstra;

public class PruebaAlgoritmos {

    public static void main(String[] args) {

        Grafo grafo = new Grafo(5, false);

        // AGREGAR VERTICES
        grafo.agregarVertice("Biblioteca");
        grafo.agregarVertice("Laboratorio");
        grafo.agregarVertice("Cafeteria");
        grafo.agregarVertice("Auditorio");
        grafo.agregarVertice("Gimnasio");

        // AGREGAR CONEXIONES
        grafo.agregarArista(0, 1, 100);
        grafo.agregarArista(0, 2, 150);
        grafo.agregarArista(1, 3, 200);
        grafo.agregarArista(2, 3, 50);

        // PROBAR BFS
        System.out.println("\nRECORRIDO BFS:");
        BFS.recorrer(grafo, 0);

        // PROBAR DFS
        System.out.println("\nRECORRIDO DFS:");
        DFS.recorrer(grafo, 0);

        // PROBAR DIJKSTRA
        System.out.println("\nCAMINO MAS CORTO:");
        Dijkstra.buscar(grafo, 0, 3);

        // PROBAR VERTICE INEXISTENTE
        System.out.println("\nPROBAR VERTICE INEXISTENTE:");
        BFS.recorrer(grafo, 8);
        DFS.recorrer(grafo, 8);
        Dijkstra.buscar(grafo, 0, 8);

        // PROBAR CAMINO SIN CONEXION
        System.out.println("\nPROBAR CAMINO SIN CONEXION:");
        Dijkstra.buscar(grafo, 0, 4);
    }
}
