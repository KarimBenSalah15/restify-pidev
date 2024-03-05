package sample.Evenement.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import sample.Evenement.Entities.Evenement;
import sample.Evenement.Repository.EventsRepositorySql;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import org.controlsfx.control.Rating;

public class ClientController implements Initializable {

    @FXML
    private Button apply;

    @FXML
    private Button apply1;

    @FXML
    private Button apply11;
    @FXML
    private Rating rateID;
    @FXML
    private Rating rateID1;

    @FXML
    private Rating rateID11;
    @FXML
    private Label datelabel;

    @FXML
    private Label datelabel1;

    @FXML
    private Label datelabel2;

    @FXML
    private Label dureelabel;

    @FXML
    private Label dureelabel1;

    @FXML
    private Label dureelabel2;

    @FXML
    private Label etatlabel;

    @FXML
    private Label etatlabel1;

    @FXML
    private Label etatlabel2;
    @FXML
    private ComboBox<String> evenementComboBox;

    private EventsRepositorySql eventsDb;
    private List<Evenement> eventsList = new ArrayList<>();


    Optional<Evenement> currentEvent ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        eventsDb = new EventsRepositorySql();
        this.initEvenments();
        this.datelabel.setText("");
        this.dureelabel.setText("");
        this.etatlabel.setText("");
        this.datelabel1.setText("");
        this.dureelabel1.setText("");
        this.etatlabel1.setText("");
        this.datelabel2.setText("");
        this.dureelabel2.setText("");
        this.etatlabel2.setText("");
    }


    /**@FXML
    void chargerTypesEvenements(ActionEvent event) {
        String selectedEventType = evenementComboBox.getValue();
        Optional<Evenement> selectEvent = this.eventsList.stream().filter(e -> e.getType().equals(selectedEventType)).findFirst();
        selectEvent.ifPresent(evenement -> {
            System.out.println("event" + evenement);
            this.currentEvent = Optional.of(evenement);
           this.datelabel.setText("Date: " + evenement.getDate().toString());
            this.dureelabel.setText("Duree: " + evenement.getDuree());
            this.etatlabel.setText("Etat: " + evenement.getEtat());
            this.datelabel1.setText("Date: " + evenement.getDate().toString());
            this.dureelabel1.setText("Duree: " + evenement.getDuree());
            this.etatlabel1.setText("Etat: " + evenement.getEtat());
            this.datelabel2.setText("Date: " + evenement.getDate().toString());
            this.dureelabel2.setText("Duree: " + evenement.getDuree());
            this.etatlabel2.setText("Etat: " + evenement.getEtat());

        });

    }*/
    @FXML
    void chargerTypesEvenements(ActionEvent event) {
        String selectedEventType = evenementComboBox.getValue();
        Optional<Evenement> selectedEvent = this.eventsList.stream().filter(e -> e.getType().equals(selectedEventType)).findFirst();
        selectedEvent.ifPresent(evenement -> {
            switch (evenement.getType()) {
                case "Reveillon":
                    datelabel.setText("Date: " + evenement.getDate().toString());
                    dureelabel.setText("Duree: " + evenement.getDuree());
                    etatlabel.setText("Etat: " + evenement.getEtat());
                    rateID.setRating(0);
                    break;
                case "Halloween":
                    datelabel1.setText("Date: " + evenement.getDate().toString());
                    dureelabel1.setText("Duree: " + evenement.getDuree());
                    etatlabel1.setText("Etat: " + evenement.getEtat());
                    rateID1.setRating(0);
                    break;
                case "SaintValentin":
                    datelabel2.setText("Date: " + evenement.getDate().toString());
                    dureelabel2.setText("Duree: " + evenement.getDuree());
                    etatlabel2.setText("Etat: " + evenement.getEtat());
                    rateID11.setRating(0);
                    break;
            }
        });
    }
    @FXML
    void onRatingChanged(ActionEvent event) {
        double rating = rateID.getRating();
        showAlert(rating);
    }

    @FXML
    void onRatingChanged1(ActionEvent event) {
        double rating = rateID1.getRating();
        showAlert(rating);
    }

    @FXML
    void onRatingChanged11(ActionEvent event) {
        double rating = rateID11.getRating();
        showAlert(rating);
    }
    private void showAlert(double rating) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rating Information");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez donné " + rating + " étoiles.");
        alert.showAndWait();
    }


    private void initEvenments() {
        ObservableList<String> typesEvenements = FXCollections.observableArrayList();
        this.eventsList = this.eventsDb.getAll();
        this.eventsList.forEach(ev -> typesEvenements.add(ev.getType()));
        evenementComboBox.setItems(typesEvenements);
    }

}
