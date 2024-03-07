package projectfinder.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/projectfinder/sidebar.fxml"));
        Scene scene = new Scene(root);
        stage.setHeight(800);
        stage.setWidth(1500);
        stage.setTitle("Product - Search!");
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }





    }



