package sample.Evenement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantController implements Initializable {

    @FXML
    private TableColumn<Participant, Integer> ageP;

    @FXML
    private TextField ageTextField1;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Participant, Integer> idP;

    @FXML
    private TableColumn<Participant, String> nomP;

    @FXML
    private TextField nomTextField;

    @FXML
    private TableColumn<Participant, String> prenomP;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TableView<Participant> tableP;
    MyConnection cnx2 = null;
    PreparedStatement pst =null;
    ObservableList<Participant> participantsObservableList = FXCollections.observableArrayList();

    public List<Participant> afficher   () {
        participantsObservableList.clear();
        MyConnection connectNow = new MyConnection();
        Connection connectDB = connectNow.getCnx();

        try {
            String participantViewQuery = "SELECT * from  participant ";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(participantViewQuery);
            while (queryOutput.next()) {
                Integer queryid = queryOutput.getInt("ID");
                String querynom = queryOutput.getString("Nom");

                String queryprenom = queryOutput.getString("Prenom");
                Integer queryage = queryOutput.getInt("Age");
                participantsObservableList.add(new Participant(queryid, querynom, queryprenom, queryage));
            }

            idP.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomP.setCellValueFactory(new PropertyValueFactory<>("nom"));

            prenomP.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            ageP.setCellValueFactory(new PropertyValueFactory<>("age"));
            tableP.setItems(participantsObservableList);
        } catch (SQLException e) {
            Logger.getLogger(ParticipantController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return participantsObservableList;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableP.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // A row is selected, disable btnSave
                btnSave.setDisable(true);
            } else {
                // No row is selected, enable btnSave
                btnSave.setDisable(false);
            }
        });
        afficher();
    }


    @FXML
    void ajouterParticipant(ActionEvent event) {
        String nom = nomTextField.getText().trim();

        String prenom = prenomTextField.getText().trim();
        String age = ageTextField1.getText().trim();

        // Vérifier que les champs ne sont pas vides
        if (!nom.isEmpty()  && !prenom.isEmpty() && !age.isEmpty()) {
            String query = "SELECT * FROM `participant` WHERE nom = ?  ";
            cnx2 = MyConnection.getInstance();
            try {
                pst = cnx2.getCnx().prepareStatement(query);
                pst.setString(1, nom);

               // pst.setString(2, prenom);
                //pst.setInt(3, Integer.parseInt(age));

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    showAlert(Alert.AlertType.INFORMATION, "Information",  "Le participant existe déjà dans la base de données.");
                } else {
                    // Insérer le nouveau produit dans la base de données
                    String insertQuery = "INSERT INTO participant(nom, prenom, age) VALUES (?, ?, ?)";
                    PreparedStatement insertPst = cnx2.getCnx().prepareStatement(insertQuery);
                    insertPst.setString(1, nom);

                    insertPst.setString(2,prenom);
                    insertPst.setInt(3, Integer.parseInt(age));
                    insertPst.executeUpdate();
                    showAlert(Alert.AlertType.INFORMATION, "Succès",  "Participant ajouté avec succès.");
                    afficher();
                }
            }
            catch(NumberFormatException e){
                showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez saisir un entier");
            }
            catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de l'ajout du participant : " + e.getMessage());
            }
        } else {
            // Afficher une alerte si un champ est vide
            showAlert(Alert.AlertType.WARNING, "Avertissement",  "Veuillez remplir tous les champs.");
        }



    }

    private void showAlert(Alert.AlertType alertType, String information, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(information);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    void getData() {
        Participant participant = tableP.getSelectionModel().getSelectedItem();

        if (participant != null) {
            nomTextField.setText(participant.getNom());

            prenomTextField.setText(participant.getPrenom());
            ageTextField1.setText(String.valueOf(participant.getAge()));

        }

    }

    @FXML
    void supprimerParticipant(ActionEvent event) {
        Participant participant = tableP.getSelectionModel().getSelectedItem();
        if (participant != null) {
            // Un produit est sélectionné, afficher une alerte de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce participant?");

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si l'utilisateur a cliqué sur OK, supprimer le produit
                    String delete ="delete from participant where id =?";
                    cnx2=MyConnection.getInstance();
                    try {
                        pst=cnx2.cnx.prepareStatement(delete);
                        pst.setInt(1, participant.getId());
                        pst.executeUpdate();
                        afficher();
                        //clear();
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de la suppression du participant : " + e.getMessage());
                    }
                }
            });
        } else {
            // Aucun produit sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur",  "Veuillez sélectionner un participant à supprimer.");
        }


    }



    @FXML
    void update(ActionEvent event) {
        Participant participant = tableP.getSelectionModel().getSelectedItem();
        if (participant != null) {

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour ce PARTICIPANT ?");

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si l'utilisateur a cliqué sur OK, mettre à jour le produit
                    String update ="update participant set nom=?, prenom =?, age=? where id=?";
                    cnx2=MyConnection.getInstance();
                    try {
                        pst=cnx2.cnx.prepareStatement(update);
                        pst.setString(1, nomTextField.getText());

                        pst.setString(2, prenomTextField.getText());
                        pst.setInt(3, Integer.parseInt(ageTextField1.getText()));
                        pst.setInt(4, participant.getId());
                        pst.executeUpdate();
                        afficher();
                        //clear();
                    }catch(NumberFormatException e){
                        showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez saisir un entier");
                    }


                    catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de la mise à jour du produit : " + e.getMessage());
                    }
                }
            });
        } else {
            // Aucun produit sélectionné, afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur",  "Veuillez sélectionner un produit à mettre à jour.");
        }


    }

    }


