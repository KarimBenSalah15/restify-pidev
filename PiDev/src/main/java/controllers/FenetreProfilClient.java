package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tools.MyConnection;

public class FenetreProfilClient {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_modifprof;

    @FXML
    private TextField tf_emailprof;

    @FXML
    private TextField tf_loginprof;

    @FXML
    private TextField tf_mdpprof;

    @FXML
    private TextField tf_nomprof;

    @FXML
    private TextField tf_prenomprof;

    private int idpersonneco;


    @FXML
    private TextField tf_telprof;

    Connection cnx2;

    public FenetreProfilClient() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public int getId() {
        return idpersonneco;
    }

    public void setId(int id) {
        this.idpersonneco = id;
    }

    @FXML
    void initialize() {
    }

    @FXML
    void modifierprofil(ActionEvent event) {
        String nom = tf_nomprof.getText().trim();
        String prenom = tf_prenomprof.getText().trim();
        String emailcheck = tf_emailprof.getText().trim();
        String tel = tf_telprof.getText().trim();
        String login = tf_loginprof.getText().trim();
        String mdp = tf_mdpprof.getText().trim();

        if (login.isEmpty() || mdp.isEmpty() || nom.isEmpty() || prenom.isEmpty() || emailcheck.isEmpty() || tel.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        try {
            String query = "UPDATE Utilisateur set nom=?, prenom=?, email=?, tel=?, login=?, mdp=?" +
                    " where id = ?";
            PreparedStatement pst = cnx2.prepareStatement(query);
            pst.setString(1, tf_nomprof.getText());
            pst.setString(2, tf_prenomprof.getText());
            String email = tf_emailprof.getText();
            if (!isValidEmail(email)) {
                showAlert("Erreur", "Veuillez entrer une adresse email valide.");
                return;
            }
            pst.setString(3, tf_emailprof.getText());
            pst.setInt(4, Integer.parseInt(tf_telprof.getText()));
            pst.setString(5, tf_loginprof.getText());
            pst.setString(6, tf_mdpprof.getText());
            pst.setInt(7,idpersonneco);
            pst.executeUpdate();
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Succès", "Personne mise à jour avec succès.");
            } else {
                showAlert("Erreur", "Échec de la mise à jour de la personne.");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void supprimerprofil(ActionEvent event) {
        String req3 = "DELETE from Utilisateur where id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req3);
            pst.setInt(1,idpersonneco);
            pst.executeUpdate();
            showAlert("Succès", "Personne supprimée avec succès.");
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

}