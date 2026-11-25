
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;
import java.util.ArrayList;

public class PruebaGrafo {

    public static void main(String[] args) {

        Grafo grafo = new Grafo(5, false);

        // AGREGAR VERTICES
        System.out.println("AGREGAR VERTICES:");

        grafo.agregarVertice("Biblioteca");
        grafo.agregarVertice("Laboratorio");
        grafo.agregarVertice("Cafeteria");

        for (int i = 0; i < grafo.getVertices().size(); i++) {

            if (grafo.getVertices().get(i) != null) {
                System.out.println(grafo.getVertices().get(i));
            }
        }

        // PROBAR NOMBRES INCORRECTOS
        System.out.println("\nPROBAR NOMBRES INCORRECTOS:");

        System.out.println("Nombre repetido:");
        System.out.println(grafo.agregarVertice("biblioteca"));

        System.out.println("Nombre vacio:");
        System.out.println(grafo.agregarVertice(""));

        System.out.println("Nombre con espacios:");
        System.out.println(grafo.agregarVertice("   "));

        // AGREGAR CONEXIONES
        System.out.println("\nAGREGAR CONEXIONES:");

        System.out.println(grafo.agregarArista(0, 1, 100));
        System.out.println(grafo.agregarArista(0, 2, 150));

        // PROBAR PESOS INCORRECTOS
        System.out.println("\nPROBAR PESOS INCORRECTOS:");

        System.out.println("Peso negativo:");
        System.out.println(grafo.agregarArista(1, 2, -50));

        System.out.println("Peso infinito:");
        System.out.println(grafo.agregarArista(1, 2, Double.POSITIVE_INFINITY));

        System.out.println("Peso NaN:");
        System.out.println(grafo.agregarArista(1, 2, Double.NaN));

        // BUSCAR CONEXION
        System.out.println("\nBUSCAR CONEXION:");

        System.out.println(grafo.buscarArista(0, 1));

        // MODIFICAR CONEXION
        System.out.println("\nMODIFICAR CONEXION:");

        System.out.println(grafo.modificarArista(0, 1, 300));

        // PROBAR MODIFICAR CON PESOS INCORRECTOS
        System.out.println("\nMODIFICAR CON PESOS INCORRECTOS:");

        System.out.println(grafo.modificarArista(0, 1, -50));
        System.out.println(grafo.modificarArista(0, 1, Double.POSITIVE_INFINITY));
        System.out.println(grafo.modificarArista(0, 1, Double.NaN));

        // MODIFICAR VERTICE
        System.out.println("\nMODIFICAR VERTICE:");

        System.out.println(grafo.modificarVertice(2, "Cafeteria Nueva"));

        System.out.println("Intentar nombre repetido:");
        System.out.println(grafo.modificarVertice(2, "Biblioteca"));

        System.out.println("Intentar nombre vacio:");
        System.out.println(grafo.modificarVertice(2, "   "));

        // MOSTRAR LISTA DE ADYACENCIA
        System.out.println("\nLISTA DE ADYACENCIA:");

        ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

        for (int i = 0; i < lista.length; i++) {

            System.out.print("Vertice " + i + ": ");

            for (int j = 0; j < lista[i].size(); j++) {

                System.out.print(lista[i].get(j).getDestino() +
                        " Peso: " + lista[i].get(j).getPeso() + " ");
            }

            System.out.println();
        }

        // MOSTRAR MATRIZ
        System.out.println("\nMATRIZ DE ADYACENCIA:");

        double[][] matriz = grafo.getMatrizAdyacencia();

        for (int i = 0; i < matriz.length; i++) {

            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + "\t");
            }

            System.out.println();
        }

        // PROBAR COPIAS DE LAS ESTRUCTURAS
        System.out.println("\nPROBAR COPIAS:");

        lista[0].clear();
        matriz[0][1] = 999;

        System.out.println("Conexion original despues de modificar copias:");
        System.out.println(grafo.buscarArista(0, 1));

        System.out.println("Peso original en la matriz:");
        System.out.println(grafo.getMatrizAdyacencia()[0][1]);

        // ELIMINAR CONEXION
        System.out.println("\nELIMINAR CONEXION:");

        System.out.println(grafo.eliminarArista(0, 2));

        // ELIMINAR VERTICE
        System.out.println("\nELIMINAR VERTICE:");

        System.out.println(grafo.eliminarVertice(1));

        // MOSTRAR VERTICES FINALES
        for (int i = 0; i < grafo.getVertices().size(); i++) {

            if (grafo.getVertices().get(i) != null) {
                System.out.println(grafo.getVertices().get(i));
            }
        }

        // MOSTRAR MATRIZ FINAL
        System.out.println("\nMATRIZ FINAL:");

        matriz = grafo.getMatrizAdyacencia();

        for (int i = 0; i < matriz.length; i++) {

            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + "\t");
            }

            System.out.println();
        }

        // PROBAR GRAFO DIRIGIDO
        System.out.println("\nGRAFO DIRIGIDO:");

        Grafo dirigido = new Grafo(3, true);

        dirigido.agregarVertice("Biblioteca");
        dirigido.agregarVertice("Laboratorio");
        dirigido.agregarVertice("Cafeteria");

        System.out.println("Agregar conexion:");
        System.out.println(dirigido.agregarArista(0, 1, 100));

        System.out.println("Buscar conexion 0 a 1:");
        System.out.println(dirigido.buscarArista(0, 1));

        System.out.println("Buscar conexion 1 a 0:");
        System.out.println(dirigido.buscarArista(1, 0));

        // PROBAR CONEXIONES INCORRECTAS
        System.out.println("\nCONEXIONES INCORRECTAS:");

        System.out.println("Vertice inexistente:");
        System.out.println(dirigido.agregarArista(0, 5, 100));

        System.out.println("Peso negativo:");
        System.out.println(dirigido.agregarArista(0, 2, -50));

        System.out.println("Conexion repetida:");
        System.out.println(dirigido.agregarArista(0, 1, 100));

        System.out.println("Conexion consigo mismo:");
        System.out.println(dirigido.agregarArista(0, 0, 100));

        // PROBAR EXISTENCIA DE VERTICES
        System.out.println("\nEXISTENCIA DE VERTICES:");

        System.out.println(dirigido.existeVertice(0));
        System.out.println(dirigido.existeVertice(5));
    }
}
