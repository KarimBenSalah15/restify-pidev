package sample.Evenement.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import sample.Evenement.Entities.Participant;
import sample.Evenement.MyConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantController implements Initializable {
    @FXML
    private TextField emailTextField;
    @FXML
    private TableColumn<Participant, String> emailTextField1;
    @FXML
    private TableColumn<Participant, Integer> ageP;
    @FXML
    private TextField telTextField1;

    @FXML
    private TableColumn<Participant, Integer> telTextField11;


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
    private TableColumn<com.twilio.rest.api.v2010.account.conference.Participant, String> prenomP;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TableView<Participant> tableP;
    @FXML
    private TextField searchID;

    private MyConnection cnx2 = null;
    private PreparedStatement pst = null;
    private ObservableList<Participant> participantsObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();


        tableP.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnSave.setDisable(true);
            } else {
                btnSave.setDisable(false);
            }
        });
        searchID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                recherche(new ActionEvent());
            }
        });

        tableP.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnSave.setDisable(true);
            } else {
                btnSave.setDisable(false);
            }
        });
    }

    public void ajouterParticipant(Participant participant) {
        participantsObservableList.add(participant);
        tableP.setItems(participantsObservableList);
        clear();
    }

    private void clear() {
        nomTextField.clear();
        prenomTextField.clear();
        telTextField1.clear();
        emailTextField.clear();
    }

    public void afficher() {
        participantsObservableList.clear();
        MyConnection connectNow = new MyConnection();
        Connection connectDB = connectNow.getCnx();

        try {
            String participantViewQuery = "SELECT * FROM participant";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(participantViewQuery);
            while (queryOutput.next()) {
                Integer queryid = queryOutput.getInt("ID");
                String querynom = queryOutput.getString("Nom");
                String queryprenom = queryOutput.getString("Prenom");
                String queryemail = queryOutput.getString("email");
                Integer querytel = queryOutput.getInt("tel");
                participantsObservableList.add(new Participant(queryid, querynom, queryprenom, queryemail,querytel));
            }

            idP.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomP.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomP.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            emailTextField1.setCellValueFactory(new PropertyValueFactory<>("email")); // Utilisez emailTextField1 pour la colonne de l'email
            telTextField11.setCellValueFactory(new PropertyValueFactory<>("tel")); // Utilisez telTextField11 pour la colonne du numéro de téléphone
            tableP.setItems(participantsObservableList);
        } catch (SQLException e) {
            Logger.getLogger(ParticipantController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    @FXML
    void recherche(ActionEvent event) {
        String searchEmail = searchID.getText().trim();
        if (!searchEmail.isEmpty()) {
            participantsObservableList.clear();
            MyConnection connectNow = new MyConnection();
            Connection connectDB = connectNow.getCnx();

            try {
                String participantSearchQuery = "SELECT * FROM participant WHERE email LIKE ?";
                PreparedStatement statement = connectDB.prepareStatement(participantSearchQuery);
                statement.setString(1, "%" + searchEmail + "%");
                ResultSet queryOutput = statement.executeQuery();
                while (queryOutput.next()) {
                    Integer queryid = queryOutput.getInt("ID");
                    String querynom = queryOutput.getString("Nom");
                    String queryprenom = queryOutput.getString("Prenom");
                    String queryemail = queryOutput.getString("email");
                    Integer querytel = queryOutput.getInt("tel");
                    participantsObservableList.add(new Participant(queryid, querynom, queryprenom, queryemail, querytel));
                }

                idP.setCellValueFactory(new PropertyValueFactory<>("id"));
                nomP.setCellValueFactory(new PropertyValueFactory<>("nom"));
                prenomP.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                emailTextField1.setCellValueFactory(new PropertyValueFactory<>("email")); // Utilisez emailTextField1 pour la colonne de l'email
                telTextField11.setCellValueFactory(new PropertyValueFactory<>("tel")); // Utilisez telTextField11 pour la colonne du numéro de téléphone
                tableP.setItems(participantsObservableList);
            } catch (SQLException e) {
                Logger.getLogger(ParticipantController.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez saisir l'email à rechercher.");
        }

    }
    @FXML
    void ajouterParticipant(ActionEvent event) {
        String nom = nomTextField.getText().trim();
        String prenom = prenomTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String tel = telTextField1.getText().trim();

        if (!nom.isEmpty() && !prenom.isEmpty() && !tel.isEmpty()) {
            String query = "SELECT * FROM participant WHERE nom = ?";
            cnx2 = MyConnection.getInstance();
            try {
                pst = cnx2.getCnx().prepareStatement(query);
                pst.setString(1, nom);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    showAlert(Alert.AlertType.INFORMATION, "Information", "Le participant existe déjà dans la base de données.");
                } else {
                    String insertQuery = "INSERT INTO participant(nom, prenom, email,tel) VALUES (?, ?, ?,?)";
                    PreparedStatement insertPst = cnx2.getCnx().prepareStatement(insertQuery);
                    insertPst.setString(1, nom);
                    insertPst.setString(2, prenom);
                    insertPst.setString(3, email);
                    insertPst.setInt(4, Integer.parseInt(tel));
                    insertPst.executeUpdate();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Participant ajouté avec succès.");
                    afficher();
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir un entier pour l'âge.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout du participant : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez remplir tous les champs.");
        }
    }

    @FXML
    void getData() {
        Participant participant = tableP.getSelectionModel().getSelectedItem();
        if (participant != null) {
            nomTextField.setText(participant.getNom());
            prenomTextField.setText(participant.getPrenom());
            emailTextField.setText(participant.getEmail());
            telTextField1.setText(String.valueOf(participant.getTel()));
        }
    }

    @FXML
    void supprimerParticipant(ActionEvent event) {
        Participant participant = tableP.getSelectionModel().getSelectedItem();
        if (participant != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce participant?");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String delete = "DELETE FROM participant WHERE id = ?";
                    cnx2 = MyConnection.getInstance();
                    try {
                        pst = cnx2.getCnx().prepareStatement(delete);
                        pst.setInt(1, participant.getId());
                        pst.executeUpdate();
                        afficher();
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la suppression du participant : " + e.getMessage());
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un participant à supprimer.");
        }
    }

    @FXML
    void update(ActionEvent event) {
        Participant participant = tableP.getSelectionModel().getSelectedItem();
        if (participant != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour ce client ?");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String update = "UPDATE participant SET nom = ?, prenom = ?,email =?, tel = ? WHERE id = ?";
                    cnx2 = MyConnection.getInstance();
                    try {
                        pst = cnx2.getCnx().prepareStatement(update);
                        pst.setString(1, nomTextField.getText());
                        pst.setString(2, prenomTextField.getText());
                        pst.setString(3, emailTextField.getText());
                        pst.setInt(4, Integer.parseInt(telTextField1.getText()));
                        pst.setInt(5, participant.getId());
                        pst.executeUpdate();
                        afficher();
                    } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir un entier pour l'âge.");
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la mise à jour du participant : " + e.getMessage());
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un participant à mettre à jour.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String information, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(information);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}











/**package sample.Evenement.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Evenement.Entities.Evenement;
import sample.Evenement.Entities.Participant;
import sample.Evenement.MyConnection;
import sample.Evenement.Repository.EventsRepositorySql;
import sample.Evenement.Repository.ParticipantRepositorySql;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantController implements Initializable {
    @FXML
    private ComboBox<String> participantsComboBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private TableColumn<Participant, String> emailTextField1;
    @FXML
    private TableColumn<Participant, Integer> ageP;
    @FXML
    private TextField telTextField1;

    @FXML
    private TableColumn<Participant, Integer> telTextField11;


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

    private MyConnection cnx2 = null;
    private PreparedStatement pst = null;
    private ObservableList<Participant> participantsObservableList = FXCollections.observableArrayList();
    private ParticipantRepositorySql participantsDb;
    private EventsRepositorySql eventsDb;
    List<Evenement> eventsList = new ArrayList<>();
    List<Participant> ParticipantList = new ArrayList<>();

    Optional<Evenement> currentEvent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();


        tableP.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnSave.setDisable(true);
            } else {
                btnSave.setDisable(false);
            }
        });
        participantsDb = new ParticipantRepositorySql();
        eventsDb = new EventsRepositorySql();
        this.initEvents();
    }


    public void ajouterParticipant(Participant participant) {
        participantsObservableList.add(participant);
        tableP.setItems(participantsObservableList);
        clear();
    }

    private void clear() {
        nomTextField.clear();
        prenomTextField.clear();
        telTextField1.clear();
        emailTextField.clear();
    }

    public void afficher() {
        participantsObservableList.clear();
        MyConnection connectNow = new MyConnection();
        Connection connectDB = connectNow.getCnx();

        try {
            String participantViewQuery = "SELECT * FROM participant";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(participantViewQuery);
            while (queryOutput.next()) {
                Integer queryid = queryOutput.getInt("ID");
                String querynom = queryOutput.getString("Nom");
                String queryprenom = queryOutput.getString("Prenom");
                String queryemail = queryOutput.getString("email");
                Integer querytel = queryOutput.getInt("tel");
                participantsObservableList.add(new Participant(queryid, querynom, queryprenom, queryemail,querytel));
            }

            idP.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomP.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomP.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            emailTextField1.setCellValueFactory(new PropertyValueFactory<>("email")); // Utilisez emailTextField1 pour la colonne de l'email
            telTextField11.setCellValueFactory(new PropertyValueFactory<>("tel")); // Utilisez telTextField11 pour la colonne du numéro de téléphone
            tableP.setItems(participantsObservableList);
        } catch (SQLException e) {
            Logger.getLogger(ParticipantController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }


    @FXML
    void ajouterParticipant(ActionEvent event) {
        String nom = nomTextField.getText().trim();
        String prenom = prenomTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String tel = telTextField1.getText().trim();

        if (!nom.isEmpty() && !prenom.isEmpty() && !tel.isEmpty()) {
            String query = "SELECT * FROM participant WHERE nom = ?";
            cnx2 = MyConnection.getInstance();
            try {
                pst = cnx2.getCnx().prepareStatement(query);
                pst.setString(1, nom);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    showAlert(Alert.AlertType.INFORMATION, "Information", "Le participant existe déjà dans la base de données.");
                } else {
                    String insertQuery = "INSERT INTO participant(nom, prenom, email,tel) VALUES (?, ?, ?,?)";
                    PreparedStatement insertPst = cnx2.getCnx().prepareStatement(insertQuery);
                    insertPst.setString(1, nom);
                    insertPst.setString(2, prenom);
                    insertPst.setString(3, email);
                    insertPst.setInt(4, Integer.parseInt(tel));
                    insertPst.executeUpdate();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Participant ajouté avec succès.");
                    afficher();
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir un entier pour l'âge.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout du participant : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez remplir tous les champs.");
        }
    }

    @FXML
    void getData() {
        Participant participant = tableP.getSelectionModel().getSelectedItem();
        if (participant != null) {
            nomTextField.setText(participant.getNom());
            prenomTextField.setText(participant.getPrenom());
            emailTextField.setText(participant.getEmail());
            telTextField1.setText(String.valueOf(participant.getTel()));
        }
    }

    @FXML
    void supprimerParticipant(ActionEvent event) {
        Participant participant = tableP.getSelectionModel().getSelectedItem();
        if (participant != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce participant?");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String delete = "DELETE FROM participant WHERE id = ?";
                    cnx2 = MyConnection.getInstance();
                    try {
                        pst = cnx2.getCnx().prepareStatement(delete);
                        pst.setInt(1, participant.getId());
                        pst.executeUpdate();
                        afficher();
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la suppression du participant : " + e.getMessage());
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un participant à supprimer.");
        }
    }

    @FXML
    void update(ActionEvent event) {
        Participant participant = tableP.getSelectionModel().getSelectedItem();
        if (participant != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour ce client ?");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String update = "UPDATE participant SET nom = ?, prenom = ?,email =?, tel = ? WHERE id = ?";
                    cnx2 = MyConnection.getInstance();
                    try {
                        pst = cnx2.getCnx().prepareStatement(update);
                        pst.setString(1, nomTextField.getText());
                        pst.setString(2, prenomTextField.getText());
                        pst.setString(3, emailTextField.getText());
                        pst.setInt(4, Integer.parseInt(telTextField1.getText()));
                        pst.setInt(5, participant.getId());
                        pst.executeUpdate();
                        afficher();
                    } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir un entier pour l'âge.");
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la mise à jour du participant : " + e.getMessage());
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un participant à mettre à jour.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String information, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(information);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }



    public void chargerTypesEvenements() {
        String selectedEventType = participantsComboBox.getValue();
        Optional<Evenement> selectEvent = this.eventsList.stream().filter(e -> e.getType().equals(selectedEventType)).findFirst();
        selectEvent.ifPresent(evenement -> {
            System.out.println("event" + evenement);
            this.currentEvent = Optional.of(evenement);
            this.updateParticipantList(evenement);
        });
    }
    private void updateParticipantList (Evenement evenement) {

        ObservableList<String> typesEvenements = FXCollections.observableArrayList();
        this.ParticipantList = this.participantsDb.getParticpantsByEvent(evenement);
        this.eventsList.forEach(ev -> typesEvenements.add(ev.getType()));
        participantsComboBox.setItems(typesEvenements);
    }

    private void initEvents() {
        ObservableList<String> typesEvenements = FXCollections.observableArrayList();
        this.eventsList = this.eventsDb.getAll();
        this.eventsList.forEach(ev -> typesEvenements.add(ev.getType()));
        participantsComboBox.setItems(typesEvenements);
    }



    }*/
