package org.example.sistemagestiongrafos.modelo;

public class Vertice implements Comparable<Vertice> {

    private int id;
    private String nombre;

    public Vertice(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }

    @Override
    public int compareTo(Vertice otro) {
        return this.nombre.compareTo(otro.nombre);
    }
}
