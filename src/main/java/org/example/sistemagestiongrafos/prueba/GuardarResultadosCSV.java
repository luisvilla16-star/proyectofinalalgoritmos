
package org.example.sistemagestiongrafos.prueba;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GuardarResultadosCSV {

    private static String archivo =
            "src/main/resources/archivos/resultados_importacion.csv";

    public static void iniciar() {

        try {
            BufferedWriter escritor =
                    new BufferedWriter(new FileWriter(archivo, false));

            escritor.write("Vertices,Aristas,Tiempo");
            escritor.newLine();

            escritor.close();

        } catch (IOException e) {
            System.out.println("Error al crear el archivo");
        }
    }

    public static void guardar(int vertices, int aristas, long tiempo) {

        try {
            BufferedWriter escritor =
                    new BufferedWriter(new FileWriter(archivo, true));

            escritor.write(vertices + "," + aristas + "," + tiempo);
            escritor.newLine();

            escritor.close();

            System.out.println("Resultado guardado correctamente");

        } catch (IOException e) {
            System.out.println("Error al guardar los resultados");
        }
    }
}
