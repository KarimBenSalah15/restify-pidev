package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import entities.Reclamation;
import entities.Reponse;
import entities.Utilisateur;
import services.ReclamtionCrud;
import services.ReponseCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ReponseControllers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
private int idAD=1;

    public int getIdAD() {
        return idAD;
    }

    public void setIdAD(int idAD) {
        this.idAD = idAD;
    }

    @FXML
    private Button ADDBtn;

    @FXML
    private Button ADDBtn1;

    @FXML
    private Button ADDBtn11;

    @FXML
    private Button ADDBtn2;


    @FXML
    private Button add;

    @FXML
    private Button btnadd;

    @FXML
    private Button btnselect;

    @FXML
    private TableColumn<Reponse, Date> dateColumn;

    @FXML
    private Button del;

    @FXML
    private Button delbtn;


    @FXML
    private TableColumn<Reponse, Integer> idColumn;

    @FXML
    private ImageView imageid;

    @FXML
    private Label libS;
    @FXML
    private ComboBox<Integer> type;

    @FXML
    private Label libS12;

    @FXML
    private TextArea message;

    @FXML
    private TextArea message1;

    @FXML
    private TableColumn<Reponse, String> messageColumn;

    @FXML
    private Label messageL;

    @FXML
    private Label messageL1;

    @FXML
    private GridPane panADD;

    @FXML
    private AnchorPane panenew;

    @FXML
    private GridPane penDEL;

    @FXML
    private GridPane penSHOW;

    @FXML
    private GridPane penUPDATE;

    @FXML
    private Pane pn;

    @FXML
    private Button show;

    @FXML
    private TableView<Reponse> tableR;

    @FXML
    private ComboBox<Integer> idR;


    @FXML
    private TableColumn<Reponse, Integer> idRColumn;

    @FXML
    private Button update;
    @FXML
    private DatePicker dateid;

    @FXML
    private ComboBox<Integer> type1;

    @FXML
    private void handleClicks(ActionEvent event) {
        if (event.getSource() == btnadd) {
            libS.setText(" Ajouter reclamation");
            penSHOW.toFront();
        }
    }

    @FXML
    void gobackR(ActionEvent event) {

        panADD.toFront();
    }

    @FXML
    void updateR(ActionEvent event) {
        if (message1.getText() == null || message1.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le champ de message ne peut pas être vide.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the message is null or empty
        }


        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Modification de réclamation");
        confirmAlert.setContentText("Voulez-vous vraiment modifier la reponse" + this.selectR.getId());
        Optional<ButtonType> result = confirmAlert.showAndWait();
        ReponseCrud rc = new ReponseCrud();
        Reponse selectedReclamation = getSelectedReclamation();
        Reponse r = new Reponse(this.selectR.getId(), message1.getText(), this.selectR.getDate(), this.selectR.getIdA(), this.selectR.getIdR());
        if (message1.getText() == null || message1.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le champ de message ne peut pas être vide.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the message is null or empty
        }
        if (selectedReclamation != null) {

            int selectedId = selectedReclamation.getId();
            rc.modifierEntite(r, selectedId);

            int selectedIndex = tableR.getSelectionModel().getSelectedIndex();
            tableR.getItems().set(selectedIndex, r);

            // Clear the selection to avoid issues with refreshing
            tableR.getSelectionModel().clearSelection();
            FXMLLoader loader=new FXMLLoader((getClass().getResource("/FenetreDashboardAdmin.fxml")));
            try {
                Parent root= loader.load();
                ;

                message.getScene().setRoot(root);


            }catch (IOException e)
            {System.out.println(e.getMessage());}


        }
    }

    @FXML
    void saveR(ActionEvent event) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(calendar.getTime().getTime());

        // Check if type is null or empty
        if (idRR.getValue() == null || String.valueOf(idRR.getValue()).trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner id.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the type is null or empty
        }


        // Check if message is null or empty
        if (message.getText() == null || message.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le champ de message ne peut pas être vide.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the message is null or empty
        }
        // Save the reclamation to the database
        ReponseCrud rc = new ReponseCrud();

        Reponse r = new Reponse(message.getText(), currentDate, idAD, idRR.getValue());

        rc.ajouterEntite(r);
        ReclamtionCrud rr = new ReclamtionCrud();
        Reclamation c = new Reclamation();
        rr.updateEtat(idRR.getValue());


        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Réclamation ajoutée avec succès", ButtonType.OK);
        alert.show();

        // Refresh the TableView after adding

        panADD.toFront(); // Mo
        refreshTableView();
    }

    @FXML
    void deleteR(ActionEvent event) {

        ReponseCrud rc = new ReponseCrud();
        ObservableList<Reponse> selectedReclamations = tableR.getSelectionModel().getSelectedItems();

        if (!selectedReclamations.isEmpty()) {
            // Show a confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Suppression de rponse");
            confirmAlert.setContentText("Voulez-vous vraiment supprimer les reponse sélectionnées?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            // Check if the user clicked OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Reponse selectedReclamation : selectedReclamations) {
                    int selectedId = selectedReclamation.getId();
                    rc.supprimerEntite(selectedId);
                }
                // Clear the selection after deletion
                tableR.getSelectionModel().clearSelection();
                // Refresh the TableView after deletion
                refreshTableView();
            }
        } else {
            // Show an alert if no items are selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner les lignes à supprimer.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void afficher() {
        ReponseCrud rc = new ReponseCrud();
        List<Reponse> rep = rc.afficherEntiite();
        System.out.println(rep.size());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        idRColumn.setCellValueFactory(new PropertyValueFactory<>("idR"));


        tableR.getItems().addAll(rep);

    }

    ;

    private void refreshTableView() {
        ReponseCrud rc = new ReponseCrud();
        List<Reponse> updatedReclamations = rc.afficherEntiite();

        // Clear and re-populate the TableView with the updated data
        tableR.getItems().clear();
        tableR.getItems().addAll(updatedReclamations);
    }

    @FXML
    void initialize() {
        afficher();
        ReclamtionCrud rc = new ReclamtionCrud();
        List<Reclamation> rec = rc.afficherEntiite();
        List<Reclamation> filteredRec = rec.stream()
                .filter(r -> !r.getEtat())
                .collect(Collectors.toList());


        List<Integer> reclmations = filteredRec.stream()
                .map(Reclamation::getId)
                .collect(Collectors.toList());
        type1.setItems(FXCollections.observableArrayList(reclmations));
        idRR.setItems(FXCollections.observableArrayList(reclmations));
        type1.setOnAction(event -> {

            // Perform actions based on the selected choice
            type1.setValue(type1.getValue());


        });

        idRR.setOnAction(event -> {
            int selectedChoice = idRR.getValue();
            // Perform actions based on the selected choice

            idRR.setValue(idRR.getValue());


        });
    }


    @FXML
    private ComboBox<Integer> idRR;

    private Reponse getSelectedReclamation() {
        return tableR.getSelectionModel().getSelectedItem();
    }

    public Reponse selectR;

    @FXML
    void selectR(ActionEvent event) {
        this.selectR = getSelectedReclamation();

        if (this.selectR != null) {
            penDEL.toFront();
            this.message1.setText(this.selectR.getMessage());
            //   this.type1.setValue(this.selectR.getType());
        } else {
            // Show an alert if no item is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner les lignes à modifier", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void goback(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader((getClass().getResource("/page1.fxml")));
        try {
            Parent root = loader.load();

            libS.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    //////////////////////////////
}