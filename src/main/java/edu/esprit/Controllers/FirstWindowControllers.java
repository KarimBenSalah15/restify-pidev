package edu.esprit.Controllers;

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

public class FirstWindowControllers {

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
    void savepersonne(ActionEvent event) {

        //savgarde de personnbe de bd

        Personne p=new Personne(tfnom.getText(),tfprenom.getText());
        PersonneCrud ps =new PersonneCrud();
        ps.ajouterEntite(p);
        Alert alert=new Alert(Alert.AlertType.INFORMATION,"Persoone ajoute", ButtonType.OK);
        alert.show();//
        //////////////////redrecetion
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/PersonneDEtails.fxml"));
        try {
            Parent root=loader.load();
            PersonneDetailsControllers pc=loader.getController();
            pc.setResnom(tfnom.getText());
            pc.setResprnom(tfprenom.getText());


            tfnom.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void initialize() {

    }

}
