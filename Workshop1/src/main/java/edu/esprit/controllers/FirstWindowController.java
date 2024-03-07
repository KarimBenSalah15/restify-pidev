package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.esprit.entities.Personne;
import edu.esprit.services.PersonneCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class FirstWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;

    @FXML
    void savePerson(ActionEvent event) {
        //Sauvegarde de la personne dans la BD
        Personne p = new Personne(tfnom.getText(), tfprenom.getText());
        PersonneCrud pcd = new PersonneCrud();
        pcd.ajouterEntite2(p);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Personne Ajoutée", ButtonType.OK);
        alert.setTitle("Confirmation");
        alert.show();

        //Redirection
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonDetails.fxml"));
        try {
            Parent root = loader.load();
            PersonDetailsController pc = loader.getController();
            pc.setTfnomaff(tfnom.getText());
            pc.setTfprenomaff(tfprenom.getText());
            tfnom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void initialize() {

    }

}
