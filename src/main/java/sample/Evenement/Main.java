package sample.Evenement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.nio.file.Paths;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        music();

        //Charger l'interface utilisateur et afficher la fenêtre principale
      // Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/Formulaire.fxml"));
    // Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/Evenement.fxml"));
      //   Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/participantClient.fxml"));
       // Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/participantAdmin.fxml"));
       Parent root = FXMLLoader.load(getClass().getResource("/sample/Evenement/EvenementClient.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Event!");
        stage.setScene(scene);
        stage.show();

    }
    MediaPlayer mediaPlayer;
    public void music(){
        String s= "C:\\Users\\AzizB\\Downloads\\ME.wav";
        Media h = new Media (Paths.get(s).toUri().toString());
        mediaPlayer=new MediaPlayer(h);
        mediaPlayer.play();

    }


    public static void main(String[] args) {
        launch();
    }
}
