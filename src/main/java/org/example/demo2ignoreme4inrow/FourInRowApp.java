package org.example.demo2ignoreme4inrow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class FourInRowApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FourInRowApp.class.getResource("four-in-row-app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        VBox v = new VBox();
        v.setOnMouseMoved(mouseEvent -> {

        });
    }

    public static void main(String[] args) {
        launch();
    }
}