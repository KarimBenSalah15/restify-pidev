package edu.esprit.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import edu.esprit.entities.Reservation;
import edu.esprit.services.ReservationCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AdminDetails {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc;

    @FXML
    private AnchorPane anco;

    @FXML
    private AnchorPane ancoright;

    @FXML
    private JFXButton btnplus;

    @FXML
    private JFXButton btnprint;

    @FXML
    private TableColumn<Reservation, Date> dateid1;

    @FXML
    private TableColumn<Reservation, String> heureid1;

    @FXML
    private TableColumn<Reservation, Integer> id;

    @FXML
    private Label label_top;

    @FXML
    private TableColumn<Reservation, Integer> nbpersonneid1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane_top;

    @FXML
    private TableColumn<Reservation, Integer> tabId;

    @FXML
    private TableView<Reservation> viewid1;



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

    }
    @FXML
    void returnD(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/tab.fxml")));
        try {
            Parent root = loader.load();
            btnplus.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
