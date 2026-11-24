module org.example.sistemagestiongrafos {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sistemagestiongrafos to javafx.fxml;
    exports org.example.sistemagestiongrafos;
}