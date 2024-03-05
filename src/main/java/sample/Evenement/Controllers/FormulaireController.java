package sample.Evenement.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


import sample.Evenement.Entities.Evenement;
import sample.Evenement.Entities.Participant;
import sample.Evenement.Repository.EventsRepositorySql;
import sample.Evenement.Repository.ParticipantRepositorySql;

import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;


public class FormulaireController implements Initializable {

    @FXML
    private TextField ageTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private ComboBox<String> participantsComboBox;
    @FXML
    private AnchorPane bp;
    @FXML
    private TextField telTextField11;
    @FXML
    private TextField emailTextField1;
    private ParticipantRepositorySql participantsDb;
    private EventsRepositorySql eventsDb;
    List<Evenement> eventsList = new ArrayList<>();
    Optional<Evenement> currentEvent;

    @FXML
    private Label dateLabel;
    @FXML
    private Label dureeLabel;
    @FXML
    private Label etatLabel;

    @FXML
    void register(ActionEvent event) {
        String nom = nomTextField.getText().trim();
        String prenom = prenomTextField.getText().trim();
        String email = emailTextField1.getText().trim();
        Integer tel = Integer.valueOf(telTextField11.getText().trim());


        if (nom.isEmpty() || prenom.isEmpty() ||  this.currentEvent.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si l'e-mail est valide
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'adresse e-mail n'est pas valide.");
            return;
        }

        try {
            this.currentEvent.ifPresent(evenement -> {
                Participant newParticipant = new Participant(nom, prenom, email, tel, evenement);
                this.participantsDb.create(newParticipant);
                int newNbrParticipation = evenement.getNbrparticipation() + 1;
                evenement.setNbrparticipation(newNbrParticipation);
                this.eventsDb.update(evenement, evenement.getId());
                showAlert(Alert.AlertType.INFORMATION, "Success", "Participant ajouté avec succès.");
                //try {
                  //  Mailing.sendMail(email, "Vous êtes admis à l'événement " + evenement.getType());
              //  } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'envoi de l'e-mail de confirmation.");
               // }
            });

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur","Veuillez saisir un entier pour l'âge");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
        }
    }




    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    private void showAlert(Alert.AlertType alertType, String information, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(information);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        participantsDb = new ParticipantRepositorySql();
        eventsDb = new EventsRepositorySql();
        this.initEvents();
        this.dateLabel.setText("");
        this.dureeLabel.setText("");
        this.etatLabel.setText("");
    }

    public void chargerTypesEvenements() {
        String selectedEventType = participantsComboBox.getValue();
        Optional<Evenement> selectEvent = this.eventsList.stream().filter(e -> e.getType().equals(selectedEventType)).findFirst();
        selectEvent.ifPresent(evenement -> {
            System.out.println("event" + evenement);
            this.currentEvent = Optional.of(evenement);
            this.dateLabel.setText("Date: " + evenement.getDate().toString());
            this.dureeLabel.setText("Duree: " + evenement.getDuree());
            this.etatLabel.setText("Etat: " + evenement.getEtat());
        });
    }

    private void initEvents() {
        ObservableList<String> typesEvenements = FXCollections.observableArrayList();
        this.eventsList = this.eventsDb.getAll();
        this.eventsList.forEach(ev -> typesEvenements.add(ev.getType()));
        participantsComboBox.setItems(typesEvenements);
    }
}
