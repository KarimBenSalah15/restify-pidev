package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.esprit.entities.Reservation;
import edu.esprit.services.ReservationCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class Intro {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button ss;
    @FXML
    private Button aa;

    @FXML
    private URL location;
    @FXML
    void goR(ActionEvent event) {

        FXMLLoader loader=new FXMLLoader((getClass().getResource("/res.fxml")));
        try {
            Parent root= loader.load();
            Rescontrollers pc=loader.getController();
            ss.getScene().setRoot(root);

        }catch (IOException e)
        {System.out.println(e.getMessage());}
    }

    @FXML
    void goRR(ActionEvent event) {

        FXMLLoader loader=new FXMLLoader((getClass().getResource("/resClient.fxml")));
        try {
            Parent root= loader.load();
            ResClient pc=loader.getController();
            aa.getScene().setRoot(root);

        }catch (IOException e)
        {System.out.println(e.getMessage());}
    }

    @FXML
    void initialize() {

    }

}
