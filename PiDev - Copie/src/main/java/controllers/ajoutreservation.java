package controllers;

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

import email.SendEmail;
import entities.Reservation;
import entities.Table;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.ReservationCrud;
import services.TableCrud;
import tools.MyConnection;

public class ajoutreservation {

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
    private Button btn3;

    @FXML
    private DatePicker dateid2;

    @FXML
    private TextField email;

    @FXML
    private TextField heureid2;

    @FXML
    private Label label_top;

    @FXML
    private ComboBox<Integer> nbpersonneid2;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane_top;

    @FXML
    private ComboBox<Integer> tab;

    private Rescontrollers rc;
    public void setRC(Rescontrollers rc) {
        this.rc = rc;
    }
    public  int tt;

    private Rescontrollers rescontrollers; // Injection du contrôleur Rescontrollers

    public void setRescontrollers(Rescontrollers rescontrollers) {
        this.rescontrollers = rescontrollers;
    }

    @FXML
    void initialize() {
        dateid2.setValue(LocalDate.now());
        dateid2.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        List<Integer> choices = Arrays.asList(1, 2, 3, 4, 5, 6);
        nbpersonneid2.setItems(FXCollections.observableArrayList(choices));
        nbpersonneid2.setOnAction(event -> {
            int s = nbpersonneid2.getValue();
            if (s >= 1 && s <= 6) {
                nbpersonneid2.setValue(s);
                tt = s;
            }
            TableCrud tableCrud = new TableCrud();

            List<Table> tabls = tableCrud.afficherEntiite();
            List<Integer> tableId = tabls.stream()
                    .filter(table -> table.isDispo() && table.getNbrplace() >= tt) // Adjust the condition based on your requirements
                    .map(Table::getTabId)
                    .toList();
            tab.setItems(FXCollections.observableArrayList(tableId));
        });
    }


    @FXML
    void saveRes(ActionEvent event) {
        if (validateInput()) {
            LocalDate localDate = dateid2.getValue();
            Date sqlDate = Date.valueOf(localDate);
            // Get the selected table ID
            Integer selectedTableId = tab.getValue();
            Reservation p = new Reservation(sqlDate, heureid2.getText(), nbpersonneid2.getValue(), tab.getValue());
            ReservationCrud pc = new ReservationCrud();
            pc.ajouterEntite(p);
            pc.updateDispo(tab.getValue());
            rc.refreshTableView();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reservation ajoutée avec succès", ButtonType.OK);
            alert.show();
            String toEmail = email.getText().trim();
            // Check if the email address is valid
            if (!isValidEmail1(toEmail)) {
                showAlert("Email invalide", Alert.AlertType.ERROR);
                return;
            }
            // Call the send method from SendEmail class
            SendEmail.send(toEmail, p);
            // Show success message
            showAlert("Email envoyé", Alert.AlertType.INFORMATION);
            Stage ajout = (Stage)  btn3.getScene().getWindow();
            ajout.close();

            // Actualiser le tableau des réservations
            if (rescontrollers != null) {
                rescontrollers.refreshTableView();
            } else {
                System.out.println("Le contrôleur Rescontrollers n'est pas défini.");
            }
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
    private boolean validateInput() {
        if (dateid2.getValue() == null) {
            showAlert("Veuillez saisir une date", Alert.AlertType.WARNING);
            return false;
        }
        if (heureid2.getText().isEmpty()) {
            showAlert("Veuillez saisir une heure", Alert.AlertType.WARNING);
            return false;
        }
        if (nbpersonneid2.getValue() == null) {
            showAlert("Veuillez saisir un nombre de personnes", Alert.AlertType.WARNING);
            return false;
        }
        // Check if a table ID is selected
        if (tab.getValue() == null) {
            showAlert("Veuillez sélectionner une table", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.show();
    }

}
