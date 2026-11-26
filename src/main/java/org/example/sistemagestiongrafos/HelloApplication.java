
package org.example.sistemagestiongrafos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

        HelloController controlador = new HelloController();

        Scene escena = new Scene(controlador.crearVista(), 1000, 700);

        stage.setTitle("Sistema de gestión de grafos");
        stage.setScene(escena);

        // Tamaño de la ventana
        stage.setWidth(1000);
        stage.setHeight(700);

        // Tamaño mínimo
        stage.setMinWidth(850);
        stage.setMinHeight(600);

        // Permitir cambiar el tamaño
        stage.setResizable(true);

        // Mostrar ventana
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
