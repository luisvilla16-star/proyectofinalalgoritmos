package org.example.sistemagestiongrafos.CSV;

import org.example.sistemagestiongrafos.modelo.Grafo;
import org.example.sistemagestiongrafos.modelo.Arista;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportarCSV {

    public static void exportar(Grafo grafo, String archivo, boolean dirigido) {

        try {

            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo));

            escritor.write("origen,destino,peso");
            escritor.newLine();

            ArrayList<Arista>[] lista = grafo.getListaAdyacencia();

            for (int i = 0; i < lista.length; i++) {

                if (grafo.existeVertice(i)) {

                    for (int j = 0; j < lista[i].size(); j++) {

                        Arista arista = lista[i].get(j);

                        int destino = arista.getDestino();

                        if (dirigido || i < destino) {

                            String origen = grafo.getVertices().get(i).getNombre();
                            String nombreDestino = grafo.getVertices().get(destino).getNombre();

                            escritor.write(origen + "," + nombreDestino + "," + arista.getPeso());
                            escritor.newLine();
                        }
                    }
                }
            }

            escritor.close();

            System.out.println("Grafo exportado correctamente");

        } catch (IOException e) {

            System.out.println("Error al exportar el grafo");
        }
    }
}
