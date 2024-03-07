package controllers;
import java.net.URL;

import entities.Reclamation;
import entities.Reponse;
import entities.Utilisateur;
import services.ReclamtionCrud;
import services.ReponseCrud;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class ReclamationPageControlllers {
    @FXML
    private TreeView<Reclamation> treeView;
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
    private Button del;

    @FXML
    private Button delbtn;


    @FXML
    private ImageView imageid;

    @FXML
    private Label libS;

    @FXML
    private Label libS12;

    @FXML
    private ListView<String> listid;

    @FXML
    private TextArea message;

    @FXML
    private TextArea message1;

    @FXML
    private Label messageL;

    @FXML
    private Label messageL1;

    @FXML
    private GridPane panADD;
    @FXML
    private URL location;

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
    private ComboBox<Integer> type1;
    @FXML
    private ComboBox<String> typeR;

    @FXML
    private Button update;
    @FXML
    private int client;
    @FXML
    private int plat;
    @FXML
    private int emp;

    @FXML
    void deleteR(ActionEvent event) {
        // Check if an item is selected
        TreeItem<Reclamation> selectedItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Reclamation selectedReclamation = selectedItem.getValue();

            // Confirmation prompt for deletion
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Suppression de réclamation");
            confirmAlert.setContentText("Voulez-vous vraiment supprimer la réclamation " + selectedReclamation.getId());
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ReclamtionCrud rc = new ReclamtionCrud();
                rc.supprimerEntite(selectedReclamation.getId()); // Call the delete method from your CRUD service

                // Update the TreeView
                TreeItem<Reclamation> parentItem = selectedItem.getParent();
                if (parentItem != null) {
                    parentItem.getChildren().remove(selectedItem);
                } else {
                    treeView.getRoot().getChildren().remove(selectedItem);
                }

                // Clear the selection to avoid issues with refreshing
                treeView.getSelectionModel().clearSelection();
            }
        } else {
            // Show an alert if no item is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une réclamation pour la supprimer.", ButtonType.OK);
            alert.showAndWait();
        }
    }


    @FXML
    private ComboBox<String> idRR;

    @FXML
    private ComboBox<String> idRR1;

    @FXML
    public int choise;
    @FXML
    void saveR(ActionEvent event) {


        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(calendar.getTime().getTime());

        // Check if type is null or empty
        if (idRR.getValue() == null || idRR.getValue().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un type de réclamation.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the type is null or empty
        }
        if (idRR1.getValue() == null || idRR1.getValue().trim().isEmpty()) {
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
if (idRR.getValue()=="plats")
{
        Reclamation r = new Reclamation(currentDate, false, idRR.getValue(), message.getText(),0,idC, choise);
        rc.ajouterEntite(r);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Réclamation ajoutée avec succès", ButtonType.OK);
        alert.show();}
else {
    Reclamation r = new Reclamation(currentDate, false, idRR.getValue(), message.getText(),choise,idC,0);
    rc.ajouterEntite(r);

    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Réclamation ajoutée avec succès", ButtonType.OK);
    alert.show();
}

        // Refresh the TableView after adding

        refreshTableView();
        panADD.toFront(); // Mo


    }



    @FXML
    private void handleClicks(ActionEvent event){
        if (nombreTentativesAjout >= MAX_TENTATIVES_AJOUT) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vous avez atteint le nombre maximum de tentatives d'ajout (3)", ButtonType.OK);
            alert.showAndWait();

            bloquerBoutonAjout();
            return;
        }
        if ( event.getSource() ==btnadd)
        {

            libS.setText(" Ajouter reclamation");
            penSHOW.toFront();}
        nombreTentativesAjout++;
    }
    @FXML
    void gobackR(ActionEvent event) {

        panADD.toFront();
    }
    public void afficher() {

        ReclamtionCrud rc=new ReclamtionCrud();
        List<Reclamation> rep = rc.afficherEntiite();

        List<Reclamation> filteredRep = rep.stream()
                .filter(reclamation -> reclamation.getUserId() == idC)
                .collect(Collectors.toList());
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbb"+rep);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaa"+filteredRep);
        TreeItem<Reclamation> rootItem = new TreeItem<>(new Reclamation()); // You can use a dummy object for the root if needed
        rootItem.setExpanded(true);

        for (Reclamation reclamation : filteredRep) {
            TreeItem<Reclamation> reclamationItem = new TreeItem<>(reclamation);
            rootItem.getChildren().add(reclamationItem);
        }

        treeView.setRoot(rootItem);
    }
    private void refreshTableView() {
        treeView.getRoot().getChildren().clear();
        afficher();
    }
    @FXML
    private ListView<String> reponseid;
    Connection cnx2;
    public List<Utilisateur> afficheremploye() {
        List<Utilisateur> list = new ArrayList<>();
        String req2 = "SELECT * from Utilisateur where upper(role)='EMPLOYE'";
        try {
            PreparedStatement st1 = cnx2.prepareStatement(req2);
            ResultSet rs = st1.executeQuery(req2);
            while (rs.next()) {
                Utilisateur p = new Utilisateur();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));
                p.setTel(rs.getInt("tel"));
                p.setLogin(rs.getString("login"));
                p.setRole(rs.getString("role"));
                p.setPoste(rs.getString("poste"));
                p.setSalaire(rs.getInt("salaire"));
                p.setDateembauche(rs.getDate("dateembauche"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @FXML
    void initialize() {
        afficher();
        List<Utilisateur> users= afficheremploye();
        ReclamtionCrud tt=new ReclamtionCrud();
        List<Reclamation> rr=tt.afficherEntiite();
        System.out.println(rr);

      /*  PlatCrud pc =new PlatCrud();
        List<Plat> plats=pc.afficherEntiite();*/



        List<Integer> idROList = rr.stream()
                .map(Reclamation::getIdRO)
                .collect(Collectors.toList());

        List<Utilisateur> userPlats = users.stream()
                .filter(user -> !idROList.contains(user.getId()))
                .collect(Collectors.toList());

      /*  List<Plat> filteredPlats = plats.stream()
                .filter(plat -> !idROList.contains(plat.getId()))
                .collect(Collectors.toList());
        System.out.println(filteredPlats);*/
    List<Utilisateur> filteredPlats = new ArrayList<>();
          /*     Map<Integer, String> userIdToNameMap = userPlats.stream()
                .collect(Collectors.toMap(Utilisateur::getId, Utilisateur::getNom));
        Map<Integer, String> platNames = filteredPlats.stream()
                .collect(Collectors.toMap(Plat::getId, Plat::getNom));*/
    Map<Integer,String> userIdToNameMap = new TreeMap<>();
    Map<Integer,String> platNames = new TreeMap<>();
        System.out.println("ooooooooooooooo");
        ReponseCrud rc=new ReponseCrud();
        List<Reponse> rep=rc.afficherEntiite();
        List<String> reclamationDescriptions = rep.stream()
                .map(reclamation -> {

                    return "Date: [" + reclamation.getDate() + "]  Message:[" + reclamation.getMessage() + "]  id_Reclamation:[" + reclamation.getIdR() + "]  id:[" + reclamation.getId() + "]";
                })
                .collect(Collectors.toList());
        reponseid.getItems().addAll(reclamationDescriptions);
        List<String> choices = Arrays.asList("plats", "employe");
        idRR.setItems(FXCollections.observableArrayList(choices));
        idRR.setOnAction(event -> {
            String selectedChoice = idRR.getValue();
            // Perform actions based on the selected choice
            if ("plats".equals(selectedChoice)) {
                idRR.setValue( "plats");
                idRR1.setItems(FXCollections.observableArrayList(platNames.values()));

            } else if ("employe".equals(selectedChoice)) {
                // Handle 2nd choice
                idRR.setValue( "employe");
                idRR1.setItems(FXCollections.observableArrayList(userIdToNameMap.values()));

            }
        });
        idRR1.setOnAction(event -> {
            String selectedUserName = idRR1.getValue();

            if ((idRR.getValue().equals("employe"))) {

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
            } else if ((idRR.getValue()).equals("plats")) {


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
    }
    public  int idREC;
    public  String mess;
    public  String typ;
    public Boolean eta;
    public Date dat;
    @FXML
    void selectR(ActionEvent event) {
        TreeItem<Reclamation> selectedItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Reclamation selectedReclamation = selectedItem.getValue();

            // Now you have the selected Reclamation object, and you can use its properties as needed
            this.idREC = selectedReclamation.getId();
            System.out.println(this.idREC);
            this.mess = selectedReclamation.getMessage();
            this.typ = selectedReclamation.getType();
            this.dat = selectedReclamation.getDate();
            this.eta = selectedReclamation.getEtat();
            this.client = selectedReclamation.getUserId();
            this.plat = selectedReclamation.getPlatId();
            this.emp=selectedReclamation.getIdRO();
            if(!selectedReclamation.getEtat()) {
                penDEL.toFront();

                message1.setText(mess);
            }
            else if (selectedReclamation.getEtat()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "réclmation est deja traitée.", ButtonType.OK);
                alert.showAndWait();
            }
            // Update UI elements or perform further actions based on the selected Reclamation
            // ...
        } else {
            // Show an alert if no item is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une réclamation.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void updateR(ActionEvent event) {

        // Check if message is empty and show warning if so
        if (message1.getText() == null || message1.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le champ de message ne peut pas être vide.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Confirmation prompt for modification
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Modification de réclamation");
        confirmAlert.setContentText("Voulez-vous vraiment modifier la réclamation " + this.idREC);
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ReclamtionCrud rc = new ReclamtionCrud();

            // Create a new Reclamation object with updated message
            Reclamation r = new Reclamation(dat, eta, typ, message1.getText(),emp,client,plat);

            // Get the selected item from the list view
            TreeItem<Reclamation> selectedItem = treeView.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                // Extract the selected item's ID (assuming it's the first part)


                // Perform the update using the CRUD service
                rc.modifierEntite(r, idREC);

                // Consider using an observable list for automatic updates
                // listid.getItems().set(selectedId, updatedItem); // If using an observable list

                // Clear the selection to avoid issues with refreshing
                treeView.getRoot().getChildren().clear();
                treeView.getSelectionModel().clearSelection();
                afficher();
                panADD.toFront();
            } else {
                // Show an alert if no item is selected
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une réclamation pour la modifier.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

@FXML
    void reponseR(ActionEvent event)
{

    penUPDATE.toFront();
}
    @FXML
    private Button backbtn;

    //////////////////////////////
    private void bloquerBoutonAjout() {
        btnadd.setDisable(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            btnadd.setDisable(false);
        }));
        timeline.play();
    }
    private int nombreTentativesAjout = 0;
    private final int MAX_TENTATIVES_AJOUT = 3;
    private int idC=4;




















}
