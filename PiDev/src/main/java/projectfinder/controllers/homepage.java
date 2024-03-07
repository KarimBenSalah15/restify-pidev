package projectfinder.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class homepage extends Application {
    public static final String CURRENCY ="$";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/market.fxml"));
            primaryStage.setTitle("Produit");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
}}
