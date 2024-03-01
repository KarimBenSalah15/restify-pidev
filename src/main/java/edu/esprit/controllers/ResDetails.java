package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Table;
import edu.esprit.services.ReservationCrud;
import edu.esprit.services.TableCrud;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResDetails {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn3;

    @FXML
    private DatePicker dateid2;
    @FXML
    private ComboBox<Integer> tab; // Added ComboBox for selecting the table ID

    @FXML
    private TextField heureid2;

    @FXML
    private ComboBox<Integer> nbpersonneid2;

    @FXML
    private Reservation r;

    @FXML
    private int id;

    @FXML
    void initialize() {

        List<Integer> choices = Arrays.asList(1, 2, 3, 4, 5, 6);
        nbpersonneid2.setItems(FXCollections.observableArrayList(choices));


        TableCrud tableCrud = new TableCrud();

        List<Table> tabls=tableCrud.afficherEntiite();
        List<Integer> tableId = tabls.stream()
                .filter(Table::isDispo)
                .map(Table::getTabId)
                .toList();
// Set items to the ComboBox
        tab.setItems(FXCollections.observableArrayList(tableId));
    }

    public void setDateid2(Date dateid2) {
        this.dateid2.setValue(dateid2.toLocalDate());
    }

    public void setHeureid2(String heureid2) {
        this.heureid2.setText(heureid2);
    }

    public void setNbpersonneid2(Integer nbpersonneid2) {
        this.nbpersonneid2.setValue(nbpersonneid2);
    }

    public void setid(int id) {
        this.id = id;
    }

    public void setR(Reservation r) {
        this.r = r;
    }

    @FXML
    void updateR(ActionEvent event) {
        ReservationCrud reservationCrud = new ReservationCrud();
        LocalDate localDate = dateid2.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        Reservation updatedReservation = new Reservation(sqlDate, heureid2.getText(), nbpersonneid2.getValue() , tab.getValue());
        reservationCrud.modifierEntite(updatedReservation, id);

        // Close the current stage
        Stage stage = (Stage) btn3.getScene().getWindow();
        stage.close();

        // Load the main reservation view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res.fxml"));
        try {
            Parent root = loader.load();
            Rescontrollers mainController = loader.getController();
            mainController.refreshTableView(); // Refresh the main view
            stage = new Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
