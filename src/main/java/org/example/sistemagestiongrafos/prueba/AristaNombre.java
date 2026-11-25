
package org.example.sistemagestiongrafos.prueba;

import org.example.sistemagestiongrafos.modelo.Arista;

public class AristaNombre implements Comparable<AristaNombre> {

    Arista arista;
    String origen;
    String destino;

    public AristaNombre(Arista arista, String origen, String destino) {
        this.arista = arista;
        this.origen = origen;
        this.destino = destino;
    }

    @Override
    public int compareTo(AristaNombre otra) {

        int resultado = origen.compareToIgnoreCase(otra.origen);

        if (resultado == 0) {
            resultado = destino.compareToIgnoreCase(otra.destino);
        }

        return resultado;
    }
}
