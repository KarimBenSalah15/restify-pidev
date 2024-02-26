package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import entities.Utilisateur;
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

public class FenetreConnexion {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_con;

    @FXML
    private Button btn_insc2;

    @FXML
    private TextField tf_login_con;

    @FXML
    private TextField tf_mdp_con;
    Connection cnx2;


    public FenetreConnexion() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    @FXML
    void connexion(ActionEvent event) {
        String login = tf_login_con.getText().trim();
        String mdp = tf_mdp_con.getText().trim();

        // Vérification si les champs sont vides
        if (login.isEmpty() || mdp.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        String req = "SELECT * from Utilisateur where login = '"+ tf_login_con.getText() +"' and mdp = '"+ tf_mdp_con.getText() +"'";
        try {
            Statement st1 = cnx2.createStatement();
            ResultSet rs = st1.executeQuery(req);
            try {
                if (rs.next()) {
                    String role = rs.getString("role").toUpperCase();
                    if ("CLIENT".equals(role)){
                        String req2 = "SELECT id from Utilisateur where login = '"+ tf_login_con.getText() +"' and mdp = '"+ tf_mdp_con.getText() +"'";
                        Statement st2 = cnx2.createStatement();
                        ResultSet rs2 = st2.executeQuery(req2);
                        if (rs2.next()) {
                            int idenvoi = rs2.getInt(1);
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreDashboardClient.fxml"));
                            try {
                                Parent root = loader.load();
                                Scene scene = new Scene(root, 1315, 890);
                                Stage stage = (Stage) tf_login_con.getScene().getWindow();
                                FenetreProfilClient pc = loader.getController();
                                pc.setId(idenvoi);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                System.err.println(e.getMessage());
                            }
                        } else {
                            System.err.println("Aucun utilisateur CLIENT trouvé.");
                        }
                    } else if ("EMPLOYE".equals(role)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreDashboardEmploye.fxml"));
                        try {
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 1315, 890);
                            Stage stage = (Stage) tf_login_con.getScene().getWindow();
                            stage.setScene(scene);
                            stage.setFullScreen(true);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    } else if ("ADMIN".equals(role)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreDashboardAdmin.fxml"));
                        try {
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 1315, 890);
                            Stage stage = (Stage) tf_login_con.getScene().getWindow();
                            stage.setScene(scene);
                            stage.setFullScreen(true);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    void emmeneinscription(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreInscription.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) tf_login_con.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
    }

}
