package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.IOException;


public class EvenementClientController {


    @FXML
    private Button rev;
    @FXML
    private Button Sv;
    @FXML
    private Button Hall;
    @FXML
    private BorderPane bp;

    @FXML
    void On_HallClick(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface Reveillon.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Hallowein.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec l'interface chargée
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) bp.getScene().getWindow();

            // Définir la nouvelle scène dans la fenêtre
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

          /*  @FXML
            void Reveillon(MouseEvent event) {
                try {
                    // Charger le fichier FXML de l'interface Reveillon.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Reveillon.fxml"));
                    Parent root = loader.load();

                    // Créer une nouvelle scène avec l'interface chargée
                    Scene scene = new Scene(root);

                    // Obtenir la fenêtre actuelle à partir de l'événement
                    Stage stage = (Stage) bp.getScene().getWindow();

                    // Définir la nouvelle scène dans la fenêtre
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }*/



    @FXML
    void On_RevClick(ActionEvent event) {

// RC.afficherEvenements();
        try {
            // Charger le fichier FXML de l'interface Reveillon.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Reveillon.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec l'interface chargée
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) bp.getScene().getWindow();

            // Définir la nouvelle scène dans la fenêtre
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void On_SvClick(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface Reveillon.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SaintValentin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec l'interface chargée
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) bp.getScene().getWindow();

            // Définir la nouvelle scène dans la fenêtre
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
