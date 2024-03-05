package sample.Evenement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Charger l'interface utilisateur et afficher la fenêtre principale
      // Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/Formulaire.fxml"));
      //Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/Evenement.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/participantClient.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/participantAdmin.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Event!");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}
