module org.example.demo2ignoreme4inrow {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens org.example.demo2ignoreme4inrow to javafx.fxml;
    exports org.example.demo2ignoreme4inrow;
}