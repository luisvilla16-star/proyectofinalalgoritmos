
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;

public class PruebaOrdenamientos {

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

        // Medir ShellSort
        long inicioShell = System.nanoTime();

        OrdenarAristas.shellSort(grafo);

        long finShell = System.nanoTime();

        // Medir QuickSort
        long inicioQuick = System.nanoTime();

        OrdenarAristas.quickSort(grafo);

        long finQuick = System.nanoTime();

        System.out.println();

        System.out.println("Tiempo de ShellSort: "
                + (finShell - inicioShell) + " nanosegundos");

        System.out.println("Tiempo de QuickSort: "
                + (finQuick - inicioQuick) + " nanosegundos");
    }
}
