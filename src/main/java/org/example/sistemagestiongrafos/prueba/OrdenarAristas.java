
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;
import org.example.sistemagestiongrafos.algoritmos.Ordenar;

import java.util.ArrayList;

public class OrdenarAristas {

    public static Arista[] obtenerAristas(Grafo grafo) {

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();
        ArrayList<Arista> aristas = new ArrayList<>();

        for (int i = 0; i < lista.length; i++) {

            if (grafo.existeVertice(i)) {

                for (int j = 0; j < lista[i].size(); j++) {

                    Arista arista = lista[i].get(j);

                    if (arista.getOrigen() == i) {
                        aristas.add(arista);
                    }
                }
            }
        }

        Arista[] arreglo = new Arista[aristas.size()];

        for (int i = 0; i < aristas.size(); i++) {
            arreglo[i] = aristas.get(i);
        }

        return arreglo;
    }

    public static void shellSort(Grafo grafo) {

        Arista[] arreglo = obtenerAristas(grafo);

        Ordenar<Arista> ordenar = new Ordenar<>();

        ordenar.Shell(arreglo, arreglo.length);

        System.out.println("Aristas ordenadas con ShellSort:");

        mostrar(arreglo);
    }

    public static void quickSort(Grafo grafo) {

        Arista[] arreglo = obtenerAristas(grafo);

        Ordenar<Arista> ordenar = new Ordenar<>();

        ordenar.recursivo(arreglo, 0, arreglo.length - 1);

        System.out.println("Aristas ordenadas con QuickSort:");

        mostrar(arreglo);
    }

    public static void mostrar(Arista[] arreglo) {

        for (int i = 0; i < arreglo.length; i++) {

            System.out.println(
                    arreglo[i].getOrigen() + " - " +
                            arreglo[i].getDestino() + " : " +
                            arreglo[i].getPeso()
            );
        }
    }
}
