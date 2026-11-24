
package org.example.sistemagestiongrafos.algoritmos;

import java.util.Arrays;

public class Ordenar<T extends Comparable<T>> {


    public void Shell(T[] a, int n) {
        int inter = n + 1;
        T aux;

        while (inter > 1) {
            inter = inter / 2;
            int bandera = 1;

            while (bandera == 1) {
                bandera = 0;
                int i = 0;

                while ((i + inter) < n) {
                    if (a[i].compareTo(a[i + inter]) > 0) {
                        aux = a[i];
                        a[i] = a[i + inter];
                        a[i + inter] = aux;
                        bandera = 1;
                    }

                    i = i + 1;
                }
            }
        }
    }

    public void recursivo(T[] a, int inicio, int fin) {
        int izquierda = inicio;
        int derecha = fin;
        int pos = inicio;
        T aux;
        boolean bander = true;

        if (inicio >= fin) {
            return;
        }

        while (bander == true) {
            bander = false;

            while (pos != derecha &&
                    a[pos].compareTo(a[derecha]) <= 0) {
                derecha = derecha - 1;
            }

            if (pos != derecha) {
                aux = a[pos];
                a[pos] = a[derecha];
                a[derecha] = aux;
                pos = derecha;
            }

            while (pos != izquierda &&
                    a[pos].compareTo(a[izquierda]) >= 0) {
                izquierda = izquierda + 1;
            }

            if (pos != izquierda) {
                bander = true;
                aux = a[pos];
                a[pos] = a[izquierda];
                a[izquierda] = aux;
                pos = izquierda;
            }
        }

        if (inicio < pos - 1) {
            recursivo(a, inicio, pos - 1);
        }

        if (pos + 1 < fin) {
            recursivo(a, pos + 1, fin);
        }
    }


}
