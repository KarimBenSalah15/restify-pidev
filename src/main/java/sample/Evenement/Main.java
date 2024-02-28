package sample.Evenement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.Parent;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/participantClient.fxml"));
        Scene scene = new Scene(root);
        stage.setHeight(800);
        stage.setWidth(1500);
        stage.setTitle("Event!");
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }





    }



