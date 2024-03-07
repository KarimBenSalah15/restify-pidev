package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;
import tools.MyConnection;

public class FenetreAffichage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_supp;

    @FXML
    private Button btn_ajout2;

    @FXML
    private TableColumn<Utilisateur, java.util.Date> col_date;

    @FXML
    private TableColumn<Utilisateur, String> col_email;

    @FXML
    private TableColumn<Utilisateur, Integer> col_id;

    @FXML
    private TableColumn<Utilisateur, String> col_login;

    @FXML
    private TableColumn<Utilisateur, String> col_nom;

    @FXML
    private TableColumn<Utilisateur, String> col_poste;

    @FXML
    private TableColumn<Utilisateur, String> col_prenom;

    @FXML
    private TableColumn<Utilisateur, String> col_role;

    @FXML
    private TableColumn<Utilisateur, Integer> col_salaire;

    @FXML
    private TableColumn<Utilisateur, Integer> col_tel;

    @FXML
    private TableView<Utilisateur> tabview_aff;

    Connection cnx2;

    @FXML
    public void initialize() {
        afficheru();
        activerEdition();
    }

    public FenetreAffichage() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public ObservableList<Utilisateur> afficher() {
        ObservableList<Utilisateur> list = FXCollections.observableArrayList();
        String req2 = "SELECT * from Utilisateur";
        try {
            PreparedStatement st1 = cnx2.prepareStatement(req2);
            ResultSet rs = st1.executeQuery(req2);
            while (rs.next()) {
                Utilisateur p = new Utilisateur();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));
                p.setTel(rs.getInt("tel"));
                p.setLogin(rs.getString("login"));
                p.setRole(rs.getString("role"));
                p.setPoste(rs.getString("poste"));
                p.setSalaire(rs.getInt("salaire"));
                p.setDateembauche(rs.getDate("dateembauche"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public void afficheru() {
        ObservableList<Utilisateur> list = afficher();
        tabview_aff.setItems(list);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        col_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_poste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        col_salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("dateembauche"));
    }

    @FXML
    void modifier(Utilisateur employe) {
        try {
            String query = "UPDATE Utilisateur set nom=?, prenom=?, email=?, tel=?, login=?, poste=?, salaire =?" +
                    " where id = ?";
            PreparedStatement pst = cnx2.prepareStatement(query);
            pst.setString(1, employe.getNom());
            pst.setString(2, employe.getPrenom());
            String email = employe.getEmail();
            if (!isValidEmail(email)) {
                showAlert("Erreur", "Veuillez entrer une adresse email valide.");
                afficheru();
                return;
            }
            pst.setString(3, employe.getEmail());
            pst.setInt(4, employe.getTel());
            pst.setString(5, employe.getLogin());
            pst.setString(6, employe.getPoste());
            pst.setInt(7, employe.getSalaire());
            pst.setInt(8, employe.getId());
            if (utilisateurExistelogin(employe.getLogin(), employe.getId())) {
                showAlert("Erreur", "Un utilisateur avec le même login existe déjà.");
                afficheru();
                return;
            }
            else if (utilisateurExisteemail(employe.getId(),employe.getEmail())) {
                showAlert("Erreur", "Un utilisateur avec le même email existe déjà.");
                afficheru();
                return;
            }
            else if (utilisateurExistetel(employe.getTel(), employe.getId())) {
                showAlert("Erreur", "Un utilisateur avec le même téléphone existe déjà.");
                afficheru();
                return;
            }
            pst.executeUpdate();
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Succès", "Personne mise à jour avec succès.");
                afficheru();
            } else {
                showAlert("Erreur", "Échec de la mise à jour de la personne.");
                afficheru();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean confirmationmodif() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment appliquer les modifications ?");

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");

        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeOui;
    }

    public void modificationlogin(TableColumn.CellEditEvent<Utilisateur, String> utilisateurStringCellEditEvent) {
        Utilisateur employe = utilisateurStringCellEditEvent.getRowValue();
        employe.setLogin(utilisateurStringCellEditEvent.getNewValue());
        if (confirmationmodif()) {
            modifier(employe);
        } else {
        }
    }

    public void modificationnom(TableColumn.CellEditEvent<Utilisateur, String> utilisateurStringCellEditEvent) {
        Utilisateur employe = utilisateurStringCellEditEvent.getRowValue();
        employe.setNom(utilisateurStringCellEditEvent.getNewValue());
        if (confirmationmodif()) {
            modifier(employe);
        } else {
        }
    }

    public void modificationprenom(TableColumn.CellEditEvent<Utilisateur, String> utilisateurStringCellEditEvent) {
        Utilisateur employe = utilisateurStringCellEditEvent.getRowValue();
        employe.setPrenom(utilisateurStringCellEditEvent.getNewValue());
        if (confirmationmodif()) {
            modifier(employe);
        } else {
        }
    }

    public void modificationemail(TableColumn.CellEditEvent<Utilisateur, String> utilisateurStringCellEditEvent) {
        Utilisateur employe = utilisateurStringCellEditEvent.getRowValue();
        employe.setEmail(utilisateurStringCellEditEvent.getNewValue());
        if (confirmationmodif()) {
            modifier(employe);
        } else {
        }
    }

    public void modificationtel(TableColumn.CellEditEvent<Utilisateur, Integer> utilisateurStringCellEditEvent) {
        Utilisateur employe = utilisateurStringCellEditEvent.getRowValue();
        employe.setTel(utilisateurStringCellEditEvent.getNewValue());
        if (confirmationmodif()) {
            modifier(employe);
        } else {
        }
    }

    public void modificationposte(TableColumn.CellEditEvent<Utilisateur, String> utilisateurStringCellEditEvent) {
        Utilisateur employe = utilisateurStringCellEditEvent.getRowValue();
        employe.setPoste(utilisateurStringCellEditEvent.getNewValue());
        if (confirmationmodif()) {
            modifier(employe);
        } else {
        }
    }

    public void modificationsalaire(TableColumn.CellEditEvent<Utilisateur, Integer> utilisateurStringCellEditEvent) {
        Utilisateur employe = utilisateurStringCellEditEvent.getRowValue();
        employe.setSalaire(utilisateurStringCellEditEvent.getNewValue());
        if (confirmationmodif()) {
            modifier(employe);
        } else {
        }
    }

    private void activerEdition() {
        col_login.setCellFactory(TextFieldTableCell.forTableColumn());
        col_login.setOnEditCommit(event -> modificationlogin(event));
        col_nom.setCellFactory(TextFieldTableCell.forTableColumn());
        col_nom.setOnEditCommit(event -> modificationnom(event));
        col_prenom.setCellFactory(TextFieldTableCell.forTableColumn());
        col_prenom.setOnEditCommit(event -> modificationprenom(event));
        col_email.setCellFactory(TextFieldTableCell.forTableColumn());
        col_email.setOnEditCommit(event -> modificationemail(event));
        col_tel.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_tel.setOnEditCommit(event -> modificationtel(event));
        col_poste.setCellFactory(TextFieldTableCell.forTableColumn());
        col_poste.setOnEditCommit(event -> modificationposte(event));
        col_salaire.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_salaire.setOnEditCommit(event -> modificationsalaire(event));
    }

    @FXML
    void supprimer(ActionEvent event) {
        Utilisateur selectionUtilisateur = tabview_aff.getSelectionModel().getSelectedItem();
        if (selectionUtilisateur == null) {
            showAlert("Aucune personne sélectionnée", "Veuillez sélectionner une personne à supprimer.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer l'utilisateur N°" + selectionUtilisateur.getId() + " ?");

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");

        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOui) {
            String req3 = "DELETE from Utilisateur where id=?";
            try {
                PreparedStatement pst = cnx2.prepareStatement(req3);
                pst.setInt(1, selectionUtilisateur.getId());
                int lignesupprime = pst.executeUpdate();
                if (lignesupprime > 0) {
                    tabview_aff.getItems().remove(selectionUtilisateur);
                    showAlert("Succès", "Personne supprimée avec succès.");
                } else {
                    showAlert("Erreur", "Échec de la suppression de la personne.");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
        }
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

    public void redirectionajout(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreAjout.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 350);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            FenetreAjout aj = loader.getController();
            aj.setHp(this);
            stage.setOnHidden((WindowEvent windowevent) ->{
                afficheru();
            } );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
