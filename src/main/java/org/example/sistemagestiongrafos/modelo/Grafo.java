
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

        if (nombre == null || nombre.trim().equals("")) {
            return -1;
        }

        for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i) != null) {

                if (vertices.get(i).getNombre().equalsIgnoreCase(nombre.trim())) {
                    return -1;
                }
            }
        }

        for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i) == null) {
                vertices.set(i, new Vertice(i, nombre.trim()));
                return i;
            }
        }

        return -1;
    }

    public ArrayList<Vertice> getVertices() {
        return new ArrayList<>(vertices);
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

        if (!existeVertice(origen) || !existeVertice(destino)) {
            return -1;
        }

        for (int i = 0; i < listaAdyacencia[origen].size(); i++) {

            if (listaAdyacencia[origen].get(i).getDestino() == destino) {
                return i;
            }
        }

        return -1;
    }

    public boolean agregarArista(int origen, int destino, double peso) {

        if (!existeVertice(origen) || !existeVertice(destino)) {
            return false;
        }

        if (origen == destino || peso < 0 || !Double.isFinite(peso)) {
            return false;
        }

        if (buscarArista(origen, destino) != -1) {
            return false;
        }

        Arista nueva = new Arista(origen, destino, peso);

        listaAdyacencia[origen].add(nueva);
        matrizAdyacencia[origen][destino] = peso;

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

        if (peso < 0 || !Double.isFinite(peso)) {
            return false;
        }

        int posicion = buscarArista(origen, destino);

        if (posicion == -1) {
            return false;
        }

        listaAdyacencia[origen].get(posicion).setPeso(peso);
        matrizAdyacencia[origen][destino] = peso;

        if (!dirigido) {

            int posicionInversa = buscarArista(destino, origen);

            listaAdyacencia[destino].get(posicionInversa).setPeso(peso);
            matrizAdyacencia[destino][origen] = peso;
        }

        return true;
    }

    public boolean modificarVertice(int id, String nombre) {

        if (!existeVertice(id)) {
            return false;
        }

        if (nombre == null || nombre.trim().equals("")) {
            return false;
        }

        for (int i = 0; i < vertices.size(); i++) {

            if (i != id && vertices.get(i) != null) {

                if (vertices.get(i).getNombre().equalsIgnoreCase(nombre.trim())) {
                    return false;
                }
            }
        }

        vertices.get(id).setNombre(nombre.trim());

        return true;
    }

    public boolean eliminarVertice(int id) {

        if (!existeVertice(id)) {
            return false;
        }

        for (int i = 0; i < listaAdyacencia.length; i++) {

            for (int j = 0; j < listaAdyacencia[i].size(); j++) {

                if (listaAdyacencia[i].get(j).getOrigen() == id ||
                        listaAdyacencia[i].get(j).getDestino() == id) {

                    listaAdyacencia[i].remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < matrizAdyacencia.length; i++) {
            matrizAdyacencia[id][i] = -1;
            matrizAdyacencia[i][id] = -1;
        }

        matrizAdyacencia[id][id] = 0;

        vertices.set(id, null);

        return true;
    }

    public ArrayList<Arista>[] getListaAdyacencia() {

        ArrayList<Arista>[] copia = new ArrayList[listaAdyacencia.length];

        for (int i = 0; i < listaAdyacencia.length; i++) {

            copia[i] = new ArrayList<>();

            for (int j = 0; j < listaAdyacencia[i].size(); j++) {

                Arista arista = listaAdyacencia[i].get(j);

                Arista nueva = new Arista(
                        arista.getOrigen(),
                        arista.getDestino(),
                        arista.getPeso()
                );

                copia[i].add(nueva);
            }
        }

        return copia;
    }

    public double[][] getMatrizAdyacencia() {

        double[][] copia = new double[matrizAdyacencia.length][matrizAdyacencia.length];

        for (int i = 0; i < matrizAdyacencia.length; i++) {

            for (int j = 0; j < matrizAdyacencia[i].length; j++) {
                copia[i][j] = matrizAdyacencia[i][j];
            }
        }

        return copia;
    }

    public boolean esDirigido() {
        return dirigido;
    }
}
