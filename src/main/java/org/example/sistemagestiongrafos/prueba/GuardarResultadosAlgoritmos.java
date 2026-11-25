
package org.example.sistemagestiongrafos.prueba;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GuardarResultadosAlgoritmos {

    private static String archivo =
            "src/main/resources/archivos/resultados_algoritmos.csv";

    public static void iniciar() {

        try {
            BufferedWriter escritor = new BufferedWriter(
                    new FileWriter(archivo, false));

            escritor.write("Vertices,BFS,DFS,Dijkstra");
            escritor.newLine();

            escritor.close();

        } catch (IOException e) {
            System.out.println("Error al crear el archivo");
        }
    }

    public static void guardar(int vertices, long bfs,
                               long dfs, long dijkstra) {

        try {
            BufferedWriter escritor = new BufferedWriter(
                    new FileWriter(archivo, true));

            escritor.write(vertices + "," + bfs + "," +
                    dfs + "," + dijkstra);

            escritor.newLine();
            escritor.close();

            System.out.println("Resultados guardados correctamente");

        } catch (IOException e) {
            System.out.println("Error al guardar los resultados");
        }
    }
}
