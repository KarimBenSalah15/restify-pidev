package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.esprit.email.SendEmail;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.Table;
import edu.esprit.services.ReservationCrud;
import edu.esprit.services.TableCrud;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Rescontrollers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> tabId1;

    @FXML
    private TableColumn<Reservation, Integer> tabId; // Add TableColumn for tabId

    @FXML
    private Button btn;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private DatePicker dateid;

    @FXML
    private TextField heureid;

    @FXML
    private ComboBox<Integer> nbpersonneid;

    @FXML
    private TableColumn<Reservation, Date> dateid1;

    @FXML
    private TableColumn<Reservation, String> heureid1;

    @FXML
    private TableColumn<Reservation, Integer> nbpersonneid1;

    @FXML
    private TableColumn<Reservation, Integer> id;

    @FXML
    private TableView<Reservation> viewid1;

    public Reservation selectedReservation;
    public  int tt;

    @FXML
    private Label currentDateLabel;

    @FXML
    private TextField tfmail;

    @FXML
    void saveRes(ActionEvent event) {
        if (validateInput()) {
            LocalDate localDate = dateid.getValue();
            Date sqlDate = Date.valueOf(localDate);

            // Get the selected table ID
            Integer selectedTableId = tabId1.getValue();

            Reservation p = new Reservation(sqlDate, heureid.getText(), nbpersonneid.getValue(), selectedTableId);
            ReservationCrud pc = new ReservationCrud();
            pc.ajouterEntite(p);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reservation ajoutée avec succès", ButtonType.OK);
            alert.show();

            ReservationCrud rc = new ReservationCrud();
            List<Reservation> reservations = rc.afficherEntiite();
            dateid1.setCellValueFactory(new PropertyValueFactory<>("date"));
            heureid1.setCellValueFactory(new PropertyValueFactory<>("heure"));
            nbpersonneid1.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tabId1.setItems(FXCollections.observableArrayList(tableIds));

            viewid1.getItems().addAll(reservations);
            refreshTableView();

            String toEmail = tfmail.getText().trim();
            // Check if the email address is valid
            if (!isValidEmail1(toEmail)) {
                showAlert("Email invalide", Alert.AlertType.ERROR);
                return;
            }
            // Call the send method from SendEmail class
            SendEmail.send(toEmail, p);
            // Show success message
            showAlert("Email envoyé", Alert.AlertType.INFORMATION);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner vos saisies", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private boolean isValidEmail1(String email) {
        // Utiliser une expression régulière pour vérifier le format de l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    TableCrud tableCrud = new TableCrud();
    List<Integer> tableIds = tableCrud.getAllTableIds();

    private boolean validateInput() {
        if (dateid.getValue() == null) {
            showAlert("Veuillez saisir une date", Alert.AlertType.WARNING);
            return false;
        }
        if (heureid.getText().isEmpty()) {
            showAlert("Veuillez saisir une heure", Alert.AlertType.WARNING);
            return false;
        }
        if (nbpersonneid.getValue() == null) {
            showAlert("Veuillez saisir un nombre de personnes", Alert.AlertType.WARNING);
            return false;
        }
        // Check if a table ID is selected
        if (tabId1.getValue() == null) {
            showAlert("Veuillez sélectionner une table", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    @FXML
    void deleteR(ActionEvent event) {
        ReservationCrud rc = new ReservationCrud();
        this.selectedReservation = getSelectedReservation();
        if (selectedReservation != null) {
            int selectedId = selectedReservation.getId();
            rc.supprimerEntite(selectedId);
            viewid1.getItems().remove(selectedReservation);
        } else {
            showAlert("Please select a row to delete.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.show();
    }

    @FXML
    void initialize() {
        ReservationCrud rc = new ReservationCrud();
        List<Reservation> reservations = rc.afficherEntiite();
        dateid1.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureid1.setCellValueFactory(new PropertyValueFactory<>("heure"));
        nbpersonneid1.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabId.setCellValueFactory(new PropertyValueFactory<>("tabId")); // Set cellValueFactory for tabId
        viewid1.getItems().addAll(reservations);

        // Initialize the tab ComboBox
        TableCrud tableCrud = new TableCrud();
        List<Integer> tableIds = tableCrud.getAllTableIds();
        List<Table> tabls = tableCrud.afficherEntiite();

        // Initialise le DatePicker et définissez la date minimale sur la date actuelle
        dateid.setValue(LocalDate.now());
        dateid.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        List<Integer> choices = Arrays.asList(1, 2, 3, 4, 5, 6);
        nbpersonneid.setItems(FXCollections.observableArrayList(choices));
        nbpersonneid.setOnAction(event -> {
            int s = nbpersonneid.getValue();
            if (s >= 1 && s <= 6) {
                nbpersonneid.setValue(s);
                tt = s;
            }

            List<Integer> tableId = tabls.stream()
                    .filter(table -> table.isDispo() && table.getNbrplace() >= tt) // Adjust the condition based on your requirements
                    .map(Table::getTabId)
                    .toList();
            tabId1.setItems(FXCollections.observableArrayList(tableId));
        });

        // Set items to the ComboBox

        // Initialise le DatePicker et définissez la date minimale sur la date actuelle
        dateid.setValue(LocalDate.now());
        dateid.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
        // Afficher la date actuelle dans le label
        currentDateLabel.setText("Date actuelle : " + LocalDate.now().toString());
    }

    @FXML
    void refreshTableView() {
        ReservationCrud rc = new ReservationCrud();
        List<Reservation> updateReservation = rc.afficherEntiite();

        // Clear and re-populate the TableView with the updated data
        viewid1.getItems().clear();
        viewid1.getItems().addAll(updateReservation);
    }

    private Reservation getSelectedReservation() {
        return viewid1.getSelectionModel().getSelectedItem();
    }

    @FXML
    void updateR(ActionEvent event) {
        this.selectedReservation = getSelectedReservation();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/res2.fxml"));
            Parent root = loader.load();

            // Access the controller of the new scene
            Res2 pc = loader.getController();
            pc.setDateid2(this.selectedReservation.getDate());
            pc.setHeureid2(this.selectedReservation.getHeure());
            pc.setNbpersonneid2(this.selectedReservation.getNbrpersonne());
            pc.setid(this.selectedReservation.getId());
            pc.setR(this.selectedReservation);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
