
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;
import org.example.sistemagestiongrafos.modelo.Vertice;
import org.example.sistemagestiongrafos.algoritmos.Ordenar;

import java.util.ArrayList;

public class OrdenarAristas {

    public static Arista[] obtenerAristas(Grafo grafo) {

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();
        ArrayList<Arista> aristas = new ArrayList<>();

        for (int i = 0; i < lista.length; i++) {

            for (int j = 0; j < lista[i].size(); j++) {

                Arista arista = lista[i].get(j);

                if (arista.getOrigen() < arista.getDestino()) {
                    aristas.add(arista);
                }
            }
        }

        Arista[] arreglo = new Arista[aristas.size()];

        for (int i = 0; i < aristas.size(); i++) {
            arreglo[i] = aristas.get(i);
        }

        return arreglo;
    }

    // ShellSort por peso
    public static void shellSort(Grafo grafo) {

        Arista[] arreglo = obtenerAristas(grafo);

        Ordenar<Arista> ordenar = new Ordenar<>();

        ordenar.Shell(arreglo, arreglo.length);

        mostrar(arreglo);
    }

    // QuickSort por peso
    public static void quickSort(Grafo grafo) {

        Arista[] arreglo = obtenerAristas(grafo);

        Ordenar<Arista> ordenar = new Ordenar<>();

        ordenar.recursivo(arreglo, 0, arreglo.length - 1);

        mostrar(arreglo);
    }

    // Obtener aristas con sus nombres
    public static AristaNombre[] obtenerAristasNombre(Grafo grafo) {

        Arista[] aristas = obtenerAristas(grafo);

        ArrayList<Vertice> vertices = grafo.getVertices();

        AristaNombre[] arreglo = new AristaNombre[aristas.length];

        for (int i = 0; i < aristas.length; i++) {

            String origen = vertices.get(aristas[i].getOrigen()).getNombre();
            String destino = vertices.get(aristas[i].getDestino()).getNombre();

            arreglo[i] = new AristaNombre(aristas[i], origen, destino);
        }

        return arreglo;
    }

    // ShellSort por nombre
    public static void shellSortNombre(Grafo grafo) {

        AristaNombre[] arreglo = obtenerAristasNombre(grafo);

        Ordenar<AristaNombre> ordenar = new Ordenar<>();

        ordenar.Shell(arreglo, arreglo.length);

        mostrarNombre(arreglo);
    }

    // QuickSort por nombre
    public static void quickSortNombre(Grafo grafo) {

        AristaNombre[] arreglo = obtenerAristasNombre(grafo);

        Ordenar<AristaNombre> ordenar = new Ordenar<>();

        ordenar.recursivo(arreglo, 0, arreglo.length - 1);

        mostrarNombre(arreglo);
    }

    // Mostrar aristas por peso
    public static void mostrar(Arista[] arreglo) {

        for (int i = 0; i < arreglo.length; i++) {

            System.out.println(
                    arreglo[i].getOrigen() + " - " +
                            arreglo[i].getDestino() + " : " +
                            arreglo[i].getPeso()
            );
        }
    }

    // Mostrar aristas por nombre
    public static void mostrarNombre(AristaNombre[] arreglo) {

        for (int i = 0; i < arreglo.length; i++) {

            System.out.println(
                    arreglo[i].origen + " - " +
                            arreglo[i].destino + " : " +
                            arreglo[i].arista.getPeso()
            );
        }
    }
}
