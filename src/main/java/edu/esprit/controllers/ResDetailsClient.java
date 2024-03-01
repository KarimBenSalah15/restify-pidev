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

public class ResDetailsClient {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn3;

    @FXML
    private ComboBox<Integer> tab;

    @FXML
    private DatePicker dateid2;

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
        nbpersonneid2.setOnAction(event -> {
            int s = nbpersonneid2.getValue();
            if (s == 1) {
                nbpersonneid2.setValue(1);
            } else if (s == 2) {
                nbpersonneid2.setValue(2);
            } else if (s == 3) {
                nbpersonneid2.setValue(3);
            } else if (s == 4) {
                nbpersonneid2.setValue(4);
            } else if (s == 5) {
                nbpersonneid2.setValue(5);
            } else if (s == 6) {
                nbpersonneid2.setValue(6);
            }
        });
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
        Reservation updatedReservation = new Reservation(sqlDate, heureid2.getText(), nbpersonneid2.getValue(), tab.getValue());
        reservationCrud.modifierEntite(updatedReservation, id);

        // Reload the view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resClient.fxml"));
        try {
            Parent root = loader.load();
            Rescontrollers mainController = loader.getController();
            mainController.refreshTableView(); // Refresh the main view
            dateid2.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}