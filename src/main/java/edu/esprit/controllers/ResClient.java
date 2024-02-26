package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

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
        LocalDate localDate = dateid.getValue();

        // Convert LocalDate to java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        Reservation p = new Reservation(sqlDate,heureid.getText(),nbpersonneid.getValue());
        ReservationCrud pc = new ReservationCrud();
        pc.ajouterEntite(p);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Reservation ajoutée avec succès", ButtonType.OK);
        alert.show();

        ReservationCrud rc=new ReservationCrud();
        List<Reservation> reservations=rc.afficherEntiite();
        dateid1.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureid1.setCellValueFactory(new PropertyValueFactory<>("heure"));
        nbpersonneid1.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        viewid1.getItems().addAll(reservations);

    }
    private Reservation getSelectedReservation() {
        return viewid1.getSelectionModel().getSelectedItem();
    }

    @FXML
    void deleteR(ActionEvent event) {


        ReservationCrud rc = new ReservationCrud();
        this.selectedReservation = getSelectedReservation();

        if (selectedReservation != null) {
            int selectedId = selectedReservation.getId();
            rc.supprimerEntite(selectedId);

            // Refresh the TableView after deletion
            viewid1.getItems().remove(selectedReservation);
        } else {
            // Show an alert or handle the case when no item is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a row to delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML

    void updateR(ActionEvent event) {
        this.selectedReservation = getSelectedReservation();
        FXMLLoader loader=new FXMLLoader((getClass().getResource("/resDetails.fxml")));
        try {
            Parent root= loader.load();
            ResDetails pc=loader.getController();
            pc.setDateid2(this.selectedReservation.getDate());
            pc.setHeureid2(this.selectedReservation.getHeure());
            pc.setNbpersonneid2(this.selectedReservation.getNbrpersonne());
            pc.setid(this.selectedReservation.getId());
            pc.setR(this.selectedReservation);
            dateid.getScene().setRoot(root);
        }catch (IOException e)
        {System.out.println(e.getMessage());}

    }


    @FXML
    void initialize() {
        ReservationCrud rc=new ReservationCrud();
        List<Reservation> reservations=rc.afficherEntiite();
        dateid1.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureid1.setCellValueFactory(new PropertyValueFactory<>("heure"));
        nbpersonneid1.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        viewid1.getItems().addAll(reservations);

        List<Integer> choices = Arrays.asList(1,2,3,4,5,6);
        nbpersonneid.setItems(FXCollections.observableArrayList(choices));
        nbpersonneid.setOnAction(event -> {
            int s=nbpersonneid.getValue();
            if (s==1)
            {nbpersonneid.setValue(1);}
            else if(s==2)
            {nbpersonneid.setValue(2);}
            else if(s==3)
            {nbpersonneid.setValue(3);}
            else if(s==4)
            {nbpersonneid.setValue(4);}
            else if(s==5)
            {nbpersonneid.setValue(5);}
            else if(s==6)
            {nbpersonneid.setValue(6);}
        });



    }
    @FXML
    void returnR(ActionEvent event) {

        FXMLLoader loader=new FXMLLoader((getClass().getResource("/intro.fxml")));
        try {
            Parent root= loader.load();
            Intro pc=loader.getController();
            returnid.getScene().setRoot(root);

        }catch (IOException e)
        {System.out.println(e.getMessage());}
    }

}

