package edu.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;



public class Tab {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc;

    @FXML
    private AnchorPane anco;

    @FXML
    private AnchorPane ancoleft;

    @FXML
    private AnchorPane ancoright;

    @FXML
    private JFXButton btnplus;

    @FXML
    private JFXButton btnprint;

    @FXML
    private JFXButton btnsave;

    @FXML
    private JFXButton btndelete;

    @FXML
    private JFXButton btnedit;

    @FXML
    private JFXComboBox<Boolean> disponibiliteid;

    @FXML
    private TableColumn<Table, Boolean> disponibiliteid1;

    @FXML
    private TableColumn<Table, Integer> id;

    @FXML
    private Label label_top;

    @FXML
    private JFXComboBox<Integer> nbplaceid;

    @FXML
    private TableColumn<Table, Integer> nbplaceid1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane_top;

    @FXML
    private JFXComboBox<String> placeid;

    @FXML
    private TableColumn<Table, String> placeid1;

    @FXML
    private TableView<Table> tabview;

    public Table SelectedTable;


    @FXML
    private JFXButton btndetails;

    @FXML
    private TextField searchField;

    @FXML
    void save1(ActionEvent event) {

        TableCrud rc = new TableCrud();
        List<Table> reservations = rc.afficherEntiite();
        nbplaceid1.setCellValueFactory(new PropertyValueFactory<>("nbrplace"));
        placeid1.setCellValueFactory(new PropertyValueFactory<>("place"));
        disponibiliteid1.setCellValueFactory(new PropertyValueFactory<>("dispo"));
        id.setCellValueFactory(new PropertyValueFactory<>("idtable"));
        tabview.getItems().addAll(reservations);
        refreshTableView();

        if (validateInput()) {
            Table p = new Table(nbplaceid.getValue(), placeid.getValue(), disponibiliteid.getValue());
            TableCrud pc = new TableCrud();
            pc.ajouterEntite(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Table ajoutée avec succès", ButtonType.OK);
            alert.show();
            refreshTableView();
        } else {
            Alert alerts = new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner vos saisies", ButtonType.OK);
            alerts.show();
        }
    }

    private boolean validateInput() {
        // Ajouter vos contrôles de saisie ici
        Integer nbplace = nbplaceid.getValue();
        String place = placeid.getValue();
        Boolean dispo = disponibiliteid.getValue();
        if (nbplace == null) {
            showAlert("Veuillez saisir un nombre de places");
            return false;
        }
        if (place == null) {
            showAlert("Veuillez sélectionner une place");
            return false;
        }
        if (dispo == null) {
            showAlert("Veuillez sélectionner la disponibilité");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    private Table getSelectedTable() {
        Table selectedTable = tabview.getSelectionModel().getSelectedItem();
        System.out.println("Selected Table: " + selectedTable);
        return selectedTable;
    }

    @FXML
    void save2(ActionEvent event) {
        TableCrud rc = new TableCrud();
        this.SelectedTable = getSelectedTable();
        if (SelectedTable != null) {
            int selectedId = SelectedTable.getIdtable();
            rc.supprimerEntite(selectedId);
            tabview.getItems().remove(SelectedTable);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a row to delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void save3(ActionEvent event) {
        Table selectedTable = getSelectedTable();

        if (selectedTable != null) {
            TableCrud tc = new TableCrud();
            Table t = new Table(nbplaceid.getValue(), placeid.getValue(), disponibiliteid.getValue());
            tc.modifierEntite(t, selectedTable.getIdtable());
            refreshTableView();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a row to update.", ButtonType.OK);
            alert.showAndWait();
        }

        if (selectedTable != null) {
            // Comparer les valeurs actuelles avec les nouvelles valeurs
            int selectedNbPlaces = selectedTable.getNbrplace();
            String selectedPlace = selectedTable.getPlace();
            Boolean selectedDispo = selectedTable.isDispo();

            int newNbPlaces = nbplaceid.getValue();
            String newPlace = placeid.getValue();
            Boolean newDispo = disponibiliteid.getValue();

            if (selectedNbPlaces == newNbPlaces && selectedPlace.equals(newPlace) && selectedDispo.equals(newDispo)) {
                showAlert("Veuillez modifier vos données");
            }
        }
    }

    @FXML
    private void updateComboBoxesFromSelectedRow() {

        Table selectedTable = getSelectedTable();
        if (selectedTable != null) {
            nbplaceid.setValue(selectedTable.getNbrplace());
            placeid.setValue(selectedTable.getPlace());
            disponibiliteid.setValue(selectedTable.isDispo());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une ligne pour mettre à jour", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        TableCrud rc = new TableCrud();
        List<Table> reservations = rc.afficherEntiite();
        nbplaceid1.setCellValueFactory(new PropertyValueFactory<>("nbrplace"));
        placeid1.setCellValueFactory(new PropertyValueFactory<>("place"));
        disponibiliteid1.setCellValueFactory(new PropertyValueFactory<>("dispo"));
        id.setCellValueFactory(new PropertyValueFactory<>("idtable"));
        tabview.getItems().addAll(reservations);

        List<Integer> choices = Arrays.asList(1, 2, 3, 4, 5, 6);
        nbplaceid.setItems(FXCollections.observableArrayList(choices));
        nbplaceid.setOnAction(event -> {
            int s = nbplaceid.getValue();
            if (s == 1) {
                nbplaceid.setValue(1);
            } else if (s == 2) {
                nbplaceid.setValue(2);
            } else if (s == 3) {
                nbplaceid.setValue(3);
            } else if (s == 4) {
                nbplaceid.setValue(4);
            } else if (s == 5) {
                nbplaceid.setValue(5);
            } else if (s == 6) {
                nbplaceid.setValue(6);
            }
        });

        List<String> choicess = Arrays.asList("VIP", "STANDART", "COMMUNALE");
        placeid.setItems(FXCollections.observableArrayList(choicess));
        placeid.setOnAction(event -> {
            String selectedChoice = placeid.getValue();
            // Perform actions based on the selected choice
            if ("VIP".equals(selectedChoice)) {
                placeid.setValue("VIP");
            } else if ("STANDARD".equals(selectedChoice)) {
                placeid.setValue("DTANDARD");
            } else if ("COMMUNALE".equals(selectedChoice)) {
                placeid.setValue("COMMUNALE");
            }
        });

        disponibiliteid.setConverter(new AvailablilityChoice());

        List<Boolean> choicesss = Arrays.asList(true, false);
        disponibiliteid.setItems(FXCollections.observableArrayList(choicesss));
        disponibiliteid.setOnAction(event -> {
            Boolean selectedChoice = disponibiliteid.getValue();
            if (selectedChoice != null) {
                System.out.println("Selected: " + selectedChoice);
            }
        });
        disponibiliteid1.setCellFactory(column -> new TableCell<Table, Boolean>() {
            @Override
            protected void updateItem(Boolean etat, boolean empty) {
                super.updateItem(etat, empty);
                if (empty || etat == null) {
                    setText("");
                } else {
                    setText(etat ? "Disponible" : "Non Disponible");
                }
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            performSearch(newValue);
        });
    }


    @FXML
    private void refreshTableView() {
        TableCrud rc = new TableCrud();
        List<Table> updatedTable = rc.afficherEntiite();
        tabview.getItems().clear(); // Clear existing items
        tabview.getItems().addAll(updatedTable); // Add updated items
        tabview.getSelectionModel().clearSelection();
    }


    @FXML
    void det(ActionEvent event) {

        FXMLLoader loader=new FXMLLoader((getClass().getResource("/adminDetails.fxml")));
        try {
            Parent root= loader.load();
            btndetails.getScene().setRoot(root);

        }catch (IOException e)
        {System.out.println(e.getMessage());}
    }
    @FXML
    void search(ActionEvent event) {
        String searchTerm = searchField.getText();
        performSearch(searchTerm);
    }
    private void performSearch(String searchTerm) {
        TableCrud rc = new TableCrud();
        List<Table> searchResults = rc.searchTables(searchTerm);
        updateTableView(searchResults);
    }
    private void updateTableView(List<Table> updatedTable) {
        tabview.getItems().clear(); // Clear existing items
        tabview.getItems().addAll(updatedTable); // Add updated items
        tabview.getSelectionModel().clearSelection();
    }
}

