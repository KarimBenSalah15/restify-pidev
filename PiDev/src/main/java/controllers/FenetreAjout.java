package controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.MyConnection;

public class FenetreAjout {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_ajoutemp;

    @FXML
    private ComboBox<String> combobox_role;

    @FXML
    private TextField tf_emailemp;

    @FXML
    private TextField tf_loginemp;

    @FXML
    private TextField tf_mdpemp;

    @FXML
    private TextField tf_nomemp;

    @FXML
    private TextField tf_posteemp;

    @FXML
    private TextField tf_prenomemp;

    @FXML
    private TextField tf_salaireemp;

    @FXML
    private TextField tf_telemp;

    private FenetreAffichage hp;
    Connection cnx2;
    Encryptor encryptor = new Encryptor();

    public FenetreAjout() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public void setHp(FenetreAffichage hp) {
        this.hp = hp;
    }

    @FXML
    void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList("Employé", "Client");
        combobox_role.setItems(list);
        combobox_role.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if ("Client".equals(newVal)) {
                tf_posteemp.setDisable(true);
                tf_salaireemp.setDisable(true);
                tf_posteemp.clear();
                tf_salaireemp.clear();
            } else if ("Employé".equals(newVal)) {
                tf_posteemp.setDisable(false);
                tf_salaireemp.setDisable(false);
            }
        });
    }

    @FXML
    void ajout(ActionEvent event) {
        String nom = tf_nomemp.getText().trim();
        String prenom = tf_prenomemp.getText().trim();
        String emailcheck = tf_emailemp.getText().trim();
        String tel = tf_telemp.getText().trim();
        String login = tf_loginemp.getText().trim();
        String poste = tf_posteemp.getText().trim();
        String mdp = tf_mdpemp.getText().trim();
        String role = combobox_role.getValue();
        String salaire = tf_salaireemp.getText().trim();

        if ("Client".equals(role)){
            if (login.isEmpty() || mdp.isEmpty() || nom.isEmpty() || prenom.isEmpty() || emailcheck.isEmpty() || tel.isEmpty() || role == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }
        } else if ("Employé".equals(role)) {
            if (login.isEmpty() || mdp.isEmpty() || nom.isEmpty() || prenom.isEmpty() || emailcheck.isEmpty() || tel.isEmpty() || poste.isEmpty() || salaire.isEmpty() || role == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un rôle.");
            return;
        }
        if (utilisateurExistelogin(login)) {
            showAlert("Erreur", "Un utilisateur avec le même login existe déjà.");
            return;
        }
        else if (utilisateurExisteemail(emailcheck)) {
            showAlert("Erreur", "Un utilisateur avec le même email existe déjà.");
            return;
        }
        else if (utilisateurExistetel(Integer.parseInt(tf_telemp.getText()))) {
            showAlert("Erreur", "Un utilisateur avec le même téléphone existe déjà.");
            return;
        }

        String req = "INSERT INTO Utilisateur (nom, prenom, email, tel, login, mdp, role, poste, salaire, dateembauche) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        java.util.Date today = new java.util.Date();
        java.sql.Date sqlDate =  new java.sql.Date(today.getTime());

        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, tf_nomemp.getText());
            pst.setString(2, tf_prenomemp.getText());
            String email = tf_emailemp.getText();
            if (!isValidEmail(email)) {
                showAlert("Erreur", "Veuillez entrer une adresse email valide.");
                return;
            }
            pst.setString(3, tf_emailemp.getText());
            pst.setInt(4, Integer.parseInt(tf_telemp.getText()));
            pst.setString(5, tf_loginemp.getText());
            pst.setString(6, encryptor.encryptString(tf_mdpemp.getText()));
            pst.setString(7, combobox_role.getValue());
            if ("Client".equals(role)){
                pst.setNull(8, java.sql.Types.VARCHAR);
                pst.setNull(9, java.sql.Types.INTEGER);
                pst.setNull(10, java.sql.Types.DATE);
                pst.executeUpdate();
                showAlert("Succès", "Vous avez bien ajouté le nouveau client.");
            } else if ("Employé".equals(role)) {
                pst.setString(8, tf_posteemp.getText());
                pst.setString(9, tf_salaireemp.getText());
                pst.setDate(10, sqlDate);
                pst.executeUpdate();
                hp.afficheru();
                showAlert("Succès", "Vous avez bien ajouté le nouvel employé.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Stage ajout = (Stage) tf_nomemp.getScene().getWindow();
        ajout.close();
    }

    private boolean utilisateurExistelogin(String login) {
        String req = "SELECT * FROM Utilisateur WHERE login = ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, login);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean utilisateurExisteemail(String email) {
        String req = "SELECT * FROM Utilisateur WHERE email = ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean utilisateurExistetel(int tel) {
        String req = "SELECT * FROM Utilisateur WHERE tel = ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(1, tel);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
