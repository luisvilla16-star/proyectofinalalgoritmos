package org.example.sistemagestiongrafos.modelo;

import java.util.ArrayList;

public class Grafo {

    private ArrayList<Vertice> vertices;
    private ArrayList<Arista>[] listaAdyacencia;
    private double[][] matrizAdyacencia;
    private boolean dirigido;

    public Grafo(int cantidadMaxima, boolean dirigido) {

        this.dirigido = dirigido;

        vertices = new ArrayList<>();

        listaAdyacencia = new ArrayList[cantidadMaxima];

        matrizAdyacencia = new double[cantidadMaxima][cantidadMaxima];

        for (int i = 0; i < cantidadMaxima; i++) {

            vertices.add(null);

            listaAdyacencia[i] = new ArrayList<>();

            for (int j = 0; j < cantidadMaxima; j++) {

                matrizAdyacencia[i][j] = -1;
            }

            matrizAdyacencia[i][i] = 0;
        }
    }

    public int agregarVertice(String nombre) {

        if (nombre == null || nombre.equals("")) {
            return -1;
        }

        for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i) == null) {
                vertices.set(i, new Vertice(i, nombre));
                return i;
            }
        }

        return -1;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public boolean existeVertice(int vertice) {

        if (vertice < 0 || vertice >= vertices.size()) {
            return false;
        }

        if (vertices.get(vertice) == null) {
            return false;
        }

        return true;
    }


    public int buscarArista(int origen, int destino) {

        for (int i = 0; i < listaAdyacencia[origen].size(); i++) {

            if (listaAdyacencia[origen].get(i).getDestino() == destino) {
                return i;
            }
        }

        return -1;
    }


    public boolean agregarArista(int origen, int destino, double peso) {

        // Verificar que los dos vertices existan
        if (!existeVertice(origen) || !existeVertice(destino)) {
            return false;
        }

        // No permitir conexiones consigo mismo ni pesos negativos
        if (origen == destino || peso < 0) {
            return false;
        }

        // Verificar que la conexion no exista
        if (buscarArista(origen, destino) != -1) {
            return false;
        }

        // Crear una nueva arista
        Arista nueva = new Arista(origen, destino, peso);

        // Agregar la conexion a la lista de adyacencia
        listaAdyacencia[origen].add(nueva);

        // Guardar el peso en la matriz de adyacencia
        matrizAdyacencia[origen][destino] = peso;

        // Si el grafo no es dirigido, agregar tambien la conexion inversa
        if (!dirigido) {

            Arista inversa = new Arista(destino, origen, peso);

            listaAdyacencia[destino].add(inversa);

            matrizAdyacencia[destino][origen] = peso;
        }

        return true;
    }


    public boolean eliminarArista(int origen, int destino) {

        if (!existeVertice(origen) || !existeVertice(destino)) {
            return false;
        }

        if (buscarArista(origen, destino) == -1) {
            return false;
        }

        listaAdyacencia[origen].remove(buscarArista(origen, destino));

        matrizAdyacencia[origen][destino] = -1;

        if (!dirigido) {

            listaAdyacencia[destino].remove(buscarArista(destino, origen));

            matrizAdyacencia[destino][origen] = -1;
        }

        return true;
    }

    public boolean modificarArista(int origen, int destino, double peso) {

        if (!existeVertice(origen) || !existeVertice(destino)) {
            return false;
        }

        if (peso < 0 || buscarArista(origen, destino) == -1) {
            return false;
        }

        listaAdyacencia[origen].get(buscarArista(origen, destino)).setPeso(peso);

        matrizAdyacencia[origen][destino] = peso;

        if (!dirigido) {

            listaAdyacencia[destino].get(buscarArista(destino, origen)).setPeso(peso);

            matrizAdyacencia[destino][origen] = peso;
        }

        return true;
    }


    public boolean modificarVertice(int id, String nombre) {

        if (!existeVertice(id)) {
            return false;
        }

        if (nombre == null || nombre.equals("")) {
            return false;
        }

        vertices.get(id).setNombre(nombre);

        return true;
    }



    public boolean eliminarVertice(int id) {

        if (!existeVertice(id)) {
            return false;
        }

        // Eliminar las conexiones
        for (int i = 0; i < listaAdyacencia.length; i++) {

            for (int j = 0; j < listaAdyacencia[i].size(); j++) {

                if (listaAdyacencia[i].get(j).getOrigen() == id ||
                        listaAdyacencia[i].get(j).getDestino() == id) {

                    listaAdyacencia[i].remove(j);
                    j--;
                }
            }
        }

        // Limpiar la matriz
        for (int i = 0; i < matrizAdyacencia.length; i++) {
            matrizAdyacencia[id][i] = -1;
            matrizAdyacencia[i][id] = -1;
        }

        // Mantener el valor de la diagonal
        matrizAdyacencia[id][id] = 0;

        // Eliminar el vertice
        vertices.set(id, null);

        return true;
    }


    public ArrayList<Arista>[] getListaAdyacencia() {
        return listaAdyacencia;
    }



    public double[][] getMatrizAdyacencia() {
        return matrizAdyacencia;
    }

}