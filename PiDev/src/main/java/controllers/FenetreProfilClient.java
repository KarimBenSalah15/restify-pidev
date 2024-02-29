package controllers;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    @FXML
    private TextField tf_telprof;

    Connection cnx2;
    Encryptor encryptor = new Encryptor();

    public FenetreProfilClient() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public TextField getTf_emailprof() {
        return tf_emailprof;
    }

    public TextField getTf_loginprof() {
        return tf_loginprof;
    }

    public TextField getTf_mdpprof() {
        return tf_mdpprof;
    }

    public TextField getTf_nomprof() {
        return tf_nomprof;
    }

    public TextField getTf_prenomprof() {
        return tf_prenomprof;
    }

    public TextField getTf_telprof() {
        return tf_telprof;
    }

    public void setTf_emailprof(TextField tf_emailprof) {
        this.tf_emailprof = tf_emailprof;
    }

    public void setTf_loginprof(TextField tf_loginprof) {
        this.tf_loginprof = tf_loginprof;
    }

    public void setTf_mdpprof(TextField tf_mdpprof) {
        this.tf_mdpprof = tf_mdpprof;
    }

    public void setTf_nomprof(TextField tf_nomprof) {
        this.tf_nomprof = tf_nomprof;
    }

    public void setTf_prenomprof(TextField tf_prenomprof) {
        this.tf_prenomprof = tf_prenomprof;
    }

    public void setTf_telprof(TextField tf_telprof) {
        this.tf_telprof = tf_telprof;
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
            pst.setString(6, encryptor.encryptString(tf_mdpprof.getText()));
            pst.setInt(7,MyConnection.getInstance().getIdenvoi());
            if (utilisateurExistelogin(login, MyConnection.getInstance().getIdenvoi())) {
                showAlert("Erreur", "Un utilisateur avec le même login existe déjà.");
                return;
            }
            else if (utilisateurExisteemail(MyConnection.getInstance().getIdenvoi(),email)) {
                showAlert("Erreur", "Un utilisateur avec le même email existe déjà.");
                return;
            }
            else if (utilisateurExistetel(Integer.parseInt(tf_telprof.getText()), MyConnection.getInstance().getIdenvoi())) {
                showAlert("Erreur", "Un utilisateur avec le même téléphone existe déjà.");
                return;
            }
            pst.executeUpdate();
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Succès", "Personne mise à jour avec succès.");
                FenetreDashboardClient refresh = new FenetreDashboardClient();
                refresh.affichagemodif();
            } else {
                showAlert("Erreur", "Échec de la mise à jour de la personne.");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerprofil(ActionEvent event) {
        String req3 = "DELETE from Utilisateur where id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req3);
            pst.setInt(1,MyConnection.getInstance().getIdenvoi());
            pst.executeUpdate();
            showAlert("Succès", "Personne supprimée avec succès.");
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        Stage suppr = (Stage) tf_nomprof.getScene().getWindow();
        suppr.close();
    }

    private boolean utilisateurExistelogin(String login, int id) {
        String req = "SELECT * FROM Utilisateur WHERE login = ? AND id <> ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, login);
            pst.setInt(2, id);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean utilisateurExisteemail(int id, String email) {
        String req = "SELECT * FROM Utilisateur WHERE email = ? AND id <> ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, email);
            pst.setInt(2, id);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean utilisateurExistetel(int tel, int id) {
        String req = "SELECT * FROM Utilisateur WHERE tel = ? AND id <> ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(1, tel);
            pst.setInt(2, id);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
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
