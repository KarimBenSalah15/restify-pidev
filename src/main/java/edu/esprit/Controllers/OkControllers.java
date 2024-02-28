package edu.esprit.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import edu.esprit.entities.Plat;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.services.PlatCrud;
import edu.esprit.services.ReclamtionCrud;
import edu.esprit.services.UserCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class OkControllers {

    @FXML
    private TableColumn<Reclamation, Integer> actionid;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    public int choise;
    @FXML
    public int choise2;

    @FXML
    private Button add;

    @FXML
    private Button del;

    @FXML
    private Pane pn;

    @FXML
    private Button show;

    @FXML
    private Button update;
    @FXML
    private Label libS;
    @FXML
    private GridPane panADD;

    @FXML
    private GridPane penDEL;
    @FXML
    private ComboBox<String> type;
    @FXML
    private ComboBox<String> type1;
    @FXML
    private ComboBox<String> type11;
    @FXML
    private GridPane penSHOW;
    @FXML
    private Button ADDBtn;
    @FXML
    private GridPane penUPDATE;

    @FXML
    private ComboBox<String> type2;
    @FXML
    public void afficher() {
        ReclamtionCrud rc=new ReclamtionCrud();
        List<Reclamation> reclamations=rc.afficherEntiite();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        etatColumn.setCellFactory(column -> new TableCell<Reclamation, Boolean>() {
            @Override
            protected void updateItem(Boolean etat, boolean empty) {
                super.updateItem(etat, empty);
                if (empty || etat == null) {
                    setText("");
                } else {
                    setText(etat ? "réclamation traitée" : "réclamation non traitée");
                }
            }
        });
        tableR.getItems().addAll(reclamations);

    };

    @FXML
    void initialize() {
        //URL location,ResourceBundle resources

        tableR.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        afficher();

        //////////////////////////////////////////////////////////////////
      
        ///////////////////////////////////////////////////////////////
        PlatCrud pc =new PlatCrud();
        List<Plat> plats=pc.afficherEntiite();

        Map<Integer, String> platNames = plats.stream()
                .collect(Collectors.toMap(Plat::getId, Plat::getNom));
        UserCrud  uc=new UserCrud();
        List<User> users=uc.afficherEntiite();


        Map<Integer, String> userIdToNameMap = users.stream()
                .collect(Collectors.toMap(User::getId, User::getNom));
        System.out.println(userIdToNameMap);
        List<String> choices = Arrays.asList("plats", "employe");
        type.setItems(FXCollections.observableArrayList(choices));
        type1.setItems(FXCollections.observableArrayList(choices));
        type1.setOnAction(event -> {
            String selectedChoice = type1.getValue();
            // Perform actions based on the selected choice
            if ("plats".equals(selectedChoice)) {
                type1.setValue( "plats");
                type11.setItems(FXCollections.observableArrayList(platNames.values()));
            } else if ("employe".equals(selectedChoice)) {
                // Handle 2nd choice
                type1.setValue( "employe");
                type11.setItems(FXCollections.observableArrayList(userIdToNameMap.values()));
            }
        });
        type2.setOnAction(event -> {
            String selectedUserName = type2.getValue();

            if ((type.getValue().equals("employe"))) {

                Optional<Map.Entry<Integer, String>> entryOptional = userIdToNameMap.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(selectedUserName))
                        .findFirst();


            // Check if a matching entry is found
            if (entryOptional.isPresent()) {
                int selectedUserId = entryOptional.get().getKey();

                // Now you have the selected userId, and you can use it as needed
                System.out.println("Selected User ID: " + selectedUserId);
                this.choise=selectedUserId;
            } else {
                // Handle the case where no matching entry is found
                System.out.println("No matching user ID found for the selected user name.");
            }
        } else if ((type.getValue()).equals("plats")) {

                Optional<Map.Entry<Integer, String>> entryOptional = platNames.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(selectedUserName))
                        .findFirst();


                // Check if a matching entry is found
                if (entryOptional.isPresent()) {
                    int selectedUserId = entryOptional.get().getKey();

                    // Now you have the selected userId, and you can use it as needed
                    System.out.println("Selected User ID: " + selectedUserId);
                    this.choise=selectedUserId;
                } else {
                    // Handle the case where no matching entry is found
                    System.out.println("No matching user ID found for the selected user name.");
                }
            }
        });
        type.setOnAction(event -> {
            String selectedChoice = type.getValue();
            // Perform actions based on the selected choice
            if ("plats".equals(selectedChoice)) {
               type.setValue( "plats");
                type2.setItems(FXCollections.observableArrayList(platNames.values()));

            } else if ("employe".equals(selectedChoice)) {
                // Handle 2nd choice
                type.setValue( "employe");
                type2.setItems(FXCollections.observableArrayList(userIdToNameMap.values()));

            }
        });
        type11.setOnAction(event -> {
            String selectedUserName = type11.getValue();

            if ((type1.getValue().equals("employe"))) {

                Optional<Map.Entry<Integer, String>> entryOptional = userIdToNameMap.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(selectedUserName))
                        .findFirst();


                // Check if a matching entry is found
                if (entryOptional.isPresent()) {
                    int selectedUserId = entryOptional.get().getKey();

                    // Now you have the selected userId, and you can use it as needed
                    System.out.println("Selected User ID: " + selectedUserId);
                    this.choise2=selectedUserId;
                } else {
                    // Handle the case where no matching entry is found
                    System.out.println("No matching user ID found for the selected user name.");
                }
            } else if ((type1.getValue()).equals("plats")) {

                Optional<Map.Entry<Integer, String>> entryOptional = platNames.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(selectedUserName))
                        .findFirst();


                // Check if a matching entry is found
                if (entryOptional.isPresent()) {
                    int selectedUserId = entryOptional.get().getKey();

                    // Now you have the selected userId, and you can use it as needed
                    System.out.println("Selected User ID: " + selectedUserId);
                    this.choise2=selectedUserId;
                } else {
                    // Handle the case where no matching entry is found
                    System.out.println("No matching user ID found for the selected user name.");
                }
            }
        });
        actionid.setCellFactory(new Callback<TableColumn<Reclamation, Integer>, TableCell<Reclamation, Integer>>() {
            @Override
            public TableCell<Reclamation, Integer> call(TableColumn<Reclamation, Integer> param) {
                return new TableCell<Reclamation, Integer>() {

                    final Button updateButton = new Button("traitee");

                    {


                        updateButton.setOnAction(event -> {
                            Reclamation r = getSelectedReclamation();
                            Reclamation nr= new Reclamation(r.getId(),r.getDate(),true,r.getType(),r.getMessage());
                            int id=r.getId();
                            Reclamation plat = getTableView().getItems().get(getIndex());
                            // Implement update logic here
                            rc.modifierEntite(nr,id);
                            refreshTableView();

                        });
                    }

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Set graphic with both buttons
                            setGraphic(new HBox( updateButton));
                            updateButton.setStyle("   -fx-background-color: #ef6c00;" +
                                    "-fx-text-fill: white;");

                        }
                    }
                };
            }
    });
    }
    @FXML
    private TableView<Reclamation> tableR;
    @FXML
    private TableColumn<Reclamation, Integer> idColumn;

    @FXML
    private TableColumn<Reclamation, Date> dateColumn;

    @FXML
    private TableColumn<Reclamation, Boolean> etatColumn;

    @FXML
    private TableColumn<Reclamation, String> typeColumn;
    ReclamtionCrud rc=new ReclamtionCrud();
    List<Reclamation> reclamations=rc.afficherEntiite();
    @FXML
    private TableColumn<Reclamation, String> messageColumn;
    @FXML
    private void handleClicks(ActionEvent event){
        if ( event.getSource() ==btnadd)
        {
            libS.setText(" Ajouter reclamation");
            penSHOW.toFront();

        } else if (event.getSource() ==show) {
            libS.setText(" Afficher reclamation");
            panADD.toFront();
        } else if (event.getSource()==update) {
            libS.setText(" Modifier reclamation");
            penUPDATE.toFront();
        } else if (event.getSource()==del) {
            libS.setText(" Supprime reclamation");
            penDEL.toFront();
        }
    }
    @FXML
    void showR(ActionEvent event) {
        ReclamtionCrud rc=new ReclamtionCrud();
        List<Reclamation> reclamations=rc.afficherEntiite();
    }
    @FXML
    void saveR(ActionEvent event) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(calendar.getTime().getTime());

        // Check if type is null or empty
        if (type.getValue() == null || type.getValue().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un type de réclamation.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the type is null or empty
        }
        if (type2.getValue() == null || type2.getValue().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un choic.", ButtonType.OK);
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
        ReclamtionCrud rc = new ReclamtionCrud();

        Reclamation r = new Reclamation(currentDate, false, type.getValue(), message.getText(), choise);
        rc.ajouterEntite(r);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Réclamation ajoutée avec succès", ButtonType.OK);
        alert.show();

        // Refresh the TableView after adding
        refreshTableView();
        panADD.toFront(); // Mo
    }
    public Reclamation selectedReclamation ;
    @FXML
    private Button btnselect;

    @FXML
    void selectR(ActionEvent event) {
        this.selectedReclamation = getSelectedReclamation();

        if (this.selectedReclamation != null) {
            penDEL.toFront();
            this.message1.setText(this.selectedReclamation.getMessage());
            this.type1.setValue(this.selectedReclamation.getType());
        } else {
            // Show an alert if no item is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner les lignes à modifier", ButtonType.OK);
            alert.showAndWait();
        }
    }
    @FXML
    private Button backbtn;
    @FXML
    void goback(ActionEvent event) {

        FXMLLoader loader=new FXMLLoader((getClass().getResource("/Intro.fxml")));
        try {
            Parent root= loader.load();
            introController pc=loader.getController();
            libS.getScene().setRoot(root);

        }catch (IOException e)
        {System.out.println(e.getMessage());}
    }
    @FXML
    void deleteR(ActionEvent event) {
        ReclamtionCrud rc = new ReclamtionCrud();
        ObservableList<Reclamation> selectedReclamations = tableR.getSelectionModel().getSelectedItems();

        if (!selectedReclamations.isEmpty()) {
            // Show a confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Suppression de réclamations");
            confirmAlert.setContentText("Voulez-vous vraiment supprimer les réclamations sélectionnées?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            // Check if the user clicked OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Reclamation selectedReclamation : selectedReclamations) {
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
    private void refreshTableView() {
        ReclamtionCrud rc = new ReclamtionCrud();
        List<Reclamation> updatedReclamations = rc.afficherEntiite();

        // Clear and re-populate the TableView with the updated data
        tableR.getItems().clear();
        tableR.getItems().addAll(updatedReclamations);
    }
    @FXML
    private TextArea message;
    @FXML
    private Button delbtn;
    @FXML
    private Button btnadd;

    private Reclamation getSelectedReclamation() {
        return tableR.getSelectionModel().getSelectedItem();
    }
    @FXML
    private TextArea message1;
    @FXML

    void updateR(ActionEvent event) {
        if (message1.getText() == null || message1.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le champ de message ne peut pas être vide.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the message is null or empty
        }
        if (type1.getValue() == null || type1.getValue().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un type de réclamation.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the type is null or empty
        }
        if (type11.getValue() == null || type11.getValue().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner Choix.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the type is null or empty
        }
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Modification de réclamation");
        confirmAlert.setContentText("Voulez-vous vraiment modifier la réclamation "+this.selectedReclamation.getId());
        Optional<ButtonType> result = confirmAlert.showAndWait();
        ReclamtionCrud rc = new ReclamtionCrud();
        Reclamation selectedReclamation = getSelectedReclamation();
        Reclamation r =new Reclamation(this.selectedReclamation.getDate(),this.selectedReclamation.getEtat(),type1.getValue(),message1.getText(),choise2);
        if (message1.getText() == null || message1.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le champ de message ne peut pas être vide.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the message is null or empty
        }
        if (selectedReclamation != null) {

            int selectedId = selectedReclamation.getId();
            rc.modifierEntite(r,selectedId );

            int selectedIndex = tableR.getSelectionModel().getSelectedIndex();
            tableR.getItems().set(selectedIndex, r);

            // Clear the selection to avoid issues with refreshing
            tableR.getSelectionModel().clearSelection();

            panADD.toFront();

        }

    }
    @FXML

    void gobackR(ActionEvent event) {
        panADD.toFront();
    }
    @FXML
    private Button ADDBtn11;


    @FXML
    private Button reponse;
    @FXML
    void  reponseGo(ActionEvent event)
    {
        FXMLLoader loader=new FXMLLoader((getClass().getResource("/reponse.fxml")));
        try {
            Parent root= loader.load();
            ReponseControllers pc=loader.getController();
           type.getScene().setRoot(root);

        }catch (IOException e)
        {System.out.println(e.getMessage());}
    }
}
