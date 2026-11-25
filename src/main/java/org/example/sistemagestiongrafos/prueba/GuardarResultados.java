
package org.example.sistemagestiongrafos.prueba;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GuardarResultados {

    public static void guardar(
            String archivo,
            int cantidad,
            long shellSort,
            long quickSort) {

        try {

            BufferedWriter escritor = new BufferedWriter(
                    new FileWriter(archivo, true)
            );

            escritor.write(
                    cantidad + "," + shellSort + "," + quickSort
            );

            escritor.newLine();
            escritor.close();

            System.out.println("Resultados guardados correctamente");

        } catch (IOException e) {

            System.out.println("Error al guardar los resultados");
        }
    }
}
