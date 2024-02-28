package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.MyConnection;

public class FenetreInscription {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_insc;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_login;

    @FXML
    private TextField tf_mdp;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_prenom;

    @FXML
    private TextField tf_tel;

    Connection cnx2;

    public FenetreInscription() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    @FXML
    void inscription(ActionEvent event) {
        String nom = tf_nom.getText().trim();
        String prenom = tf_prenom.getText().trim();
        String emailcheck = tf_email.getText().trim();
        String tel = tf_tel.getText().trim();
        String login = tf_login.getText().trim();
        String mdp = tf_mdp.getText().trim();

        if (login.isEmpty() || mdp.isEmpty() || nom.isEmpty() || prenom.isEmpty() || emailcheck.isEmpty() || tel.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        String emailExiste = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
        String loginExiste = "SELECT COUNT(*) FROM Utilisateur WHERE login = ?";
        String telExiste = "SELECT COUNT(*) FROM Utilisateur WHERE tel = ?";

        try {
            PreparedStatement emailPst = cnx2.prepareStatement(emailExiste);
            emailPst.setString(1, tf_email.getText());
            ResultSet emailRs = emailPst.executeQuery();
            emailRs.next();
            int nbEmails = emailRs.getInt(1);

            PreparedStatement loginPst = cnx2.prepareStatement(loginExiste);
            loginPst.setString(1, tf_login.getText());
            ResultSet loginRs = loginPst.executeQuery();
            loginRs.next();
            int nbLogins = loginRs.getInt(1);

            PreparedStatement telPst = cnx2.prepareStatement(telExiste);
            telPst.setInt(1, Integer.parseInt(tf_tel.getText()));
            ResultSet telRs = telPst.executeQuery();
            telRs.next();
            int nbTels = telRs.getInt(1);

            if (nbEmails > 0) {
                showAlert("Erreur", "Cet email est déjà utilisé.");
            } else if (nbLogins > 0) {
                showAlert("Erreur", "Ce login est déjà utilisé.");

            }
            else if (nbTels > 0) {
                showAlert("Erreur", "Ce téléphone est déjà utilisé.");

            }else {
                String req1 = "INSERT INTO Utilisateur (nom, prenom, email, tel, login, mdp) " +
                        "VALUES (?,?,?,?,?,?)";
                try {
                    PreparedStatement pst = cnx2.prepareStatement(req1);
                    pst.setString(1, tf_nom.getText());
                    pst.setString(2, tf_prenom.getText());
                    String email = tf_email.getText();
                    if (!isValidEmail(email)) {
                        showAlert("Erreur", "Veuillez entrer une adresse email valide.");
                        return;
                    }
                    pst.setString(3, tf_email.getText());
                    pst.setInt(4, Integer.parseInt(tf_tel.getText()));
                    pst.setString(5, tf_login.getText());
                    pst.setString(6, tf_mdp.getText());
                    pst.executeUpdate();
                    showAlert("Succès", "Vous vous êtes bien inscrit.");
                    String req2 = "SELECT id from Utilisateur where login = '"+ tf_login.getText() +"' and mdp = '"+ tf_mdp.getText() +"'";
                    Statement st2 = cnx2.createStatement();
                    ResultSet rs2 = st2.executeQuery(req2);
                    if (rs2.next()) {
                        int idenvoi = rs2.getInt(1);
                        MyConnection.getInstance().setIdenvoi(idenvoi);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreDashboardClient.fxml"));
                        try {
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 1315, 890);
                            Stage stage = (Stage) tf_nom.getScene().getWindow();
                            stage.setScene(scene);
                            stage.setFullScreen(true);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    else {
                            System.err.println("ERREUR.");
                    }
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
            }
        } catch (SQLException e) {
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

    @FXML
    void emmeneconnexion(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreConnexion.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) tf_login.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void initialize() {
    }

}
