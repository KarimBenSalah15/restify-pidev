package sample.Evenement.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Evenement.Entities.Evenement;
import sample.Evenement.MyConnection;
import sample.Evenement.Repository.EventsRepositorySql;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvenementController implements Initializable {
    @FXML
    private TableColumn<Evenement, Integer> nbrparticipation;
    @FXML
    private TableColumn<Evenement, Integer> IDA;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Evenement, Date> dateA;  // Correction du type de colonne pour la date

    @FXML
    private DatePicker dateFid;

    @FXML
    private TextField dureFid;

    @FXML
    private TableColumn<Evenement, String> dureeA;

    @FXML
    private TableColumn<Evenement, String> etatA;

    @FXML
    private ComboBox<String> etatFid;  // Correction du type de ComboBox pour String

    @FXML
    private TableColumn<Evenement, String> nomA;

    @FXML
    private TextField nomTextField;

    @FXML
    private TableView<Evenement> table;

    @FXML
    private TableColumn<Evenement, String> typeA;

    @FXML
    private ComboBox<String> typeFid;

    private PreparedStatement pst = null;
    private MyConnection cnx = null;
    private EventsRepositorySql eventsDb;
    List<Evenement> eventsList = new ArrayList<>();

    @FXML
    void ajouterEvenement() {
        String nom = nomTextField.getText();
        LocalDate localDate = dateFid.getValue();
        String duree = dureFid.getText();
        String etat = etatFid.getValue();
        String type = typeFid.getValue();

        // Vérifier que les champs ne sont pas vides
        if (!nom.isEmpty() && localDate != null && !duree.isEmpty() && !etat.isEmpty() && !type.isEmpty()) {
            String query = "SELECT * FROM `evenement` WHERE nom = ?";
            cnx = MyConnection.getInstance();
            try {
                pst = cnx.getCnx().prepareStatement(query);
                pst.setString(1, nom);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    showAlert(Alert.AlertType.INFORMATION, "Information", "Le produit existe déjà dans la base de données.");
                } else {
                    // Insérer le nouvel événement dans la base de données
                    String insertQuery = "INSERT INTO evenement(nom, date, duree, etat, type,nbrparticipation) VALUES (?, ?, ?, ?, ?,?)";
                    PreparedStatement insertPst = cnx.getCnx().prepareStatement(insertQuery);
                    insertPst.setString(1, nom);
                    insertPst.setDate(2, Date.valueOf(localDate));
                    insertPst.setString(3, duree);
                    insertPst.setString(4, etat);
                    insertPst.setString(5, type);
                    insertPst.setInt(6,0);
                    insertPst.executeUpdate();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement ajouté avec succès.");
                    afficher();


                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout de l'événement : " + e.getMessage());
            }
        } else {
            // Afficher une alerte si un champ est vide
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez remplir tous les champs.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void getData() {
        Evenement evenement = table.getSelectionModel().getSelectedItem();

        if (evenement != null) {
            nomTextField.setText(evenement.getNom());
            dateFid.setValue(evenement.getDate().toLocalDate());
            dureFid.setText(evenement.getDuree());
            etatFid.setValue(evenement.getEtat());
            typeFid.setValue(evenement.getType());
        }
    }

    ObservableList<Evenement> EvenementObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialisation des éléments du tableau
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Une ligne est sélectionnée, désactiver btnSave
                btnSave.setDisable(true);
            } else {
                // Aucune ligne n'est sélectionnée, activer btnSave
                btnSave.setDisable(false);
            }
        });

        // Afficher les événements existants
        afficher();


        // Choix pour le ComboBox etatFid
        List<String> choices = Arrays.asList("Full", "Empty");
        etatFid.setItems(FXCollections.observableArrayList(choices));
        etatFid.setOnAction(event -> {
            String selectedChoice = etatFid.getValue();
            // Effectuer des actions en fonction du choix sélectionné
            if ("Full".equals(selectedChoice)) {
                etatFid.setValue("Full");
            } else if ("Empty".equals(selectedChoice)) {
                etatFid.setValue("Empty");
            }
        });

        // Choix pour le ComboBox typeFid
        List<String> choix = Arrays.asList("SaintValentin", "Reveillon", "Halloween");
        typeFid.setItems(FXCollections.observableArrayList(choix));
        typeFid.setOnAction(event -> {
            String selectedChoice = typeFid.getValue();
            // Effectuer des actions en fonction du choix sélectionné
            /*if ("Halloween".equals(selectedChoice)) {
                etatFid.setItems(FXCollections.observableArrayList(Arrays.asList("Full", "Empty", "Special")));
                etatFid.setValue("Special");
            } else {
                etatFid.setItems(FXCollections.observableArrayList(Arrays.asList("Full", "Empty")));
                etatFid.setValue("Full");
            }*/
        });

    }

        /*table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // A row is selected, disable btnSave
                btnSave.setDisable(true);
            } else {
                // No row is selected, enable btnSave
                btnSave.setDisable(false);
            }
        });

        afficher();
        List<String> choices = Arrays.asList("Full", "Empty");
        etatFid.setItems(FXCollections.observableArrayList(choices));
        etatFid.setOnAction(event -> {
            String selectedChoice = etatFid.getValue();
            // Perform actions based on the selected choice
            if ("Full".equals(selectedChoice)) {
                etatFid.setValue( "Full");
            } else if ("Empty".equals(selectedChoice)) {
                // Handle 2nd choice
                etatFid.setValue( "Empty");
            }
        });
        List<String> choix = Arrays.asList("SaintValentin", "Reveillon","hallowein");
        typeFid.setItems(FXCollections.observableArrayList(choix));
        typeFid.setOnAction(event -> {
            String selectedChoice = typeFid.getValue();
            // Perform actions based on the selected choice
            if ("SaintValentin".equals(selectedChoice)) {
                etatFid.setValue( "SaintValentin");
            } else if ("Reveillon".equals(selectedChoice)) {
                // Handle 2nd choice
                etatFid.setValue( "Reveillon");
            }
            else if ("hallowein".equals(selectedChoice)) {
                // Handle 2nd choice
                etatFid.setValue( "hallowein");
            }
        });
    }*/

    private void afficher() {
        EvenementObservableList.clear();
        MyConnection connectNow = MyConnection.getInstance();
        Connection connectDB = connectNow.getCnx();

        try {
            String productViewQuery = "SELECT * FROM evenement";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(productViewQuery);
            while (queryOutput.next()) {
                Integer queryid = queryOutput.getInt("ID");
                String querynom = queryOutput.getString("Nom");
                Date querydate = queryOutput.getDate("date");
                String querduree = queryOutput.getString("duree");
                String queryetat = queryOutput.getString("etat");
                String querytype = queryOutput.getString("type");
                int querynbrparticipation = queryOutput.getInt("nbrparticipation");

                Evenement evenement = new Evenement(queryid, querynom, querydate, querduree, queryetat, querytype, querynbrparticipation);
                EvenementObservableList.add(evenement);
            }

            IDA.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomA.setCellValueFactory(new PropertyValueFactory<>("nom"));
            dateA.setCellValueFactory(new PropertyValueFactory<>("date"));
            dureeA.setCellValueFactory(new PropertyValueFactory<>("duree"));
            etatA.setCellValueFactory(new PropertyValueFactory<>("etat"));
            typeA.setCellValueFactory(new PropertyValueFactory<>("type"));
            nbrparticipation.setCellValueFactory(new PropertyValueFactory<>("nbrparticipation")); // Ajout de cette ligne
            table.setItems(EvenementObservableList);
        } catch (SQLException e) {
            Logger.getLogger(EvenementController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerEvent(ActionEvent event) {
        Evenement evenement = table.getSelectionModel().getSelectedItem();
        if (evenement != null) {
            // Un produit est sélectionné, afficher une alerte de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer cet evenement ?");

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si l'utilisateur a cliqué sur OK, supprimer le produit
                    String delete ="delete from evenement where id =?";
                    cnx=MyConnection.getInstance();
                    try {
                        pst=cnx.cnx.prepareStatement(delete);
                        pst.setInt(1, evenement.getId());
                        pst.executeUpdate();
                        afficher();
                        //eventsDb.getAll();

                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de la suppression de cet evenement : " + e.getMessage());
                    }
                }
            });
        } else {
            // Aucun produit sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur",  "Veuillez sélectionner un evenement à supprimer.");
        }


    }
    @FXML
    void UpdateEvent(ActionEvent event) {
        Evenement evenement = table.getSelectionModel().getSelectedItem();
        if (table != null) {

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour cette evenement ?");

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si l'utilisateur a cliqué sur OK, mettre à jour le produit
                    String update ="update evenement set Nom=?, date =?, duree=?,etat=?,type=? where id=?";
                    cnx=MyConnection.getInstance();
                    try {
                        pst=cnx.cnx.prepareStatement(update);

                        pst.setInt(6, evenement.getId());
                        pst.setString(1, nomTextField.getText());
                        pst.setDate(2, Date.valueOf(dateFid.getValue()));
                        pst.setString(3, dureFid.getText());
                        pst.setString(4, etatFid.getValue());
                        pst.setString(5, typeFid.getValue());



                        pst.executeUpdate();
                       afficher();

                    }//catch(){
                       // showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez saisir un entier");
                    //}


                    catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de la mise à jour de votre event : " + e.getMessage());
                    }
                }
            });
        } else {
            // Aucun produit sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur",  "Veuillez sélectionner un produit à mettre à jour.");
        }


    }



}
