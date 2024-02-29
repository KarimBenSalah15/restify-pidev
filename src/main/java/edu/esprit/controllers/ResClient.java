package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import edu.esprit.entities.Reservation;
import edu.esprit.services.ReservationCrud;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ResClient {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> tab;

    @FXML
    private Button btn;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button returnid;

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

    @FXML
    void saveRes(ActionEvent event) {
        if (validateInput()) {
            LocalDate localDate = dateid.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
            Reservation p = new Reservation(sqlDate, heureid.getText(), nbpersonneid.getValue(), tab.getValue());
            ReservationCrud pc = new ReservationCrud();
            pc.ajouterEntite(p);
            showAlert("Reservation ajoutée avec succès", Alert.AlertType.INFORMATION);
            refreshTableView();
        } else {
            showAlert("Veuillez sélectionner vos saisies", Alert.AlertType.WARNING);
        }
    }

    private boolean validateInput() {
        if (dateid.getValue() == null) {
            showAlert("Veuillez saisir une date", Alert.AlertType.ERROR);
            return false;
        }
        if (heureid.getText().isEmpty()) {
            showAlert("Veuillez saisir une heure", Alert.AlertType.ERROR);
            return false;
        }
        if (nbpersonneid.getValue() == null) {
            showAlert("Veuillez saisir un nombre de personnes", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void refreshTableView() {
        ReservationCrud rc = new ReservationCrud();
        List<Reservation> updateReservation = rc.afficherEntiite();

        // Clear and re-populate the TableView with the updated data
        viewid1.getItems().clear();
        viewid1.getItems().addAll(updateReservation);
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
    private Reservation getSelectedReservation() {
        return viewid1.getSelectionModel().getSelectedItem();
    }

    @FXML
    void updateR(ActionEvent event) {
        this.selectedReservation = getSelectedReservation();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/resDetails.fxml")));
        try {
            Parent root = loader.load();
            ResDetails pc = loader.getController();
            pc.setDateid2(this.selectedReservation.getDate());
            pc.setHeureid2(this.selectedReservation.getHeure());
            pc.setNbpersonneid2(this.selectedReservation.getNbrpersonne());
            pc.setid(this.selectedReservation.getId());
            pc.setR(this.selectedReservation);
            dateid.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void initialize() {
        ReservationCrud rc = new ReservationCrud();
        List<Reservation> reservations = rc.afficherEntiite();
        dateid1.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureid1.setCellValueFactory(new PropertyValueFactory<>("heure"));
        nbpersonneid1.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        viewid1.getItems().addAll(reservations);

        List<Integer> choices = Arrays.asList(1, 2, 3, 4, 5, 6);
        nbpersonneid.setItems(FXCollections.observableArrayList(choices));
        nbpersonneid.setOnAction(event -> nbpersonneid.setValue(nbpersonneid.getValue()));
    }

    @FXML
    void returnR(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/intro.fxml")));
        try {
            Parent root = loader.load();
            Intro pc = loader.getController();
            returnid.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
