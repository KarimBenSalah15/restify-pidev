package edu.esprit.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePage extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    private static Stage window;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffComm.fxml"));
           // root.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            primaryStage.setTitle("Ajouter Plat");
            primaryStage.setScene(new Scene(root));

            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static Stage getWindow() {
        return window;
    }

    private void makeDraggable(Parent root){
        root.setOnMousePressed( e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        root.setOnMouseDragged( e -> {
            window.setX( e.getScreenX() - xOffset);
            window.setY( e.getScreenY() - yOffset);
        });
    }

}
