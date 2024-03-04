package sample.Evenement.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.Evenement.Entities.Evenement;
import sample.Evenement.MyConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SaintValentinControlleur implements Initializable {
    @FXML
    private Button back;
    @FXML
    private Label etatLabel;

    @FXML
    private Label dureeLabel;

    @FXML
    private Label dateLabel;
    @FXML
    void  backGo(){
        FXMLLoader loader=new FXMLLoader((getClass().getResource("/sample/Evenement/EvenementClient.fxml")));
        try {
            Parent root= loader.load();
            EvenementClientController pc=loader.getController();
            dateLabel.getScene().setRoot(root);

        }catch (IOException e)
        {System.out.println(e.getMessage());}
    }
    ObservableList<Evenement> EvenementObservableList = FXCollections.observableArrayList();
    private MyConnection cnx = null;
    Date date = Date.valueOf("2025-02-14");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Afficher les événements existants
        afficherEvenement(date);
    }

    public void afficherEvenement(Date date) {
        MyConnection connectNow = MyConnection.getInstance();
        Connection connectDB = connectNow.getCnx();

        try {
            String query = "SELECT * FROM `evenement` WHERE `date` = ?";
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setDate(1, new java.sql.Date(date.getTime())); // Convertir la date Java en java.sql.Date
            ResultSet queryOutput = statement.executeQuery();

            while (queryOutput.next()) {
                Integer queryid = queryOutput.getInt("ID");
                String querynom = queryOutput.getString("Nom");
                Date querydate = queryOutput.getDate("date");
                String querduree = queryOutput.getString("duree");
                String queryetat = queryOutput.getString("etat");
                String querytype = queryOutput.getString("type");
                Integer querynbrparticipation = queryOutput.getInt("nbrparticipation");


                // Afficher les valeurs récupérées dans la console
                System.out.println("Etat: " + queryetat);
                System.out.println("Duree: " + querduree);
                System.out.println("Date: " + querydate);

                EvenementObservableList.add(new Evenement(queryid,querynom,querydate, querduree, queryetat,querytype,querynbrparticipation));
            }

            // Afficher la liste des événements dans la console
            for (Evenement evenement : EvenementObservableList) {
                System.out.println(evenement);
                if (!EvenementObservableList.isEmpty()) {
                    // Accéder au premier élément
                    etatLabel.setText("État: " + evenement.getEtat());
                    dureeLabel.setText("Durée: " + evenement.getDuree());
                    dateLabel.setText("Date: " + evenement.getDate());
                } else {
                    System.out.println("La liste d'événements est vide.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



























   /* public void afficherEvenements() {
        MyConnection connectNow = MyConnection.getInstance();
        Connection connectDB = connectNow.getCnx();

        try {
            String query = "SELECT * FROM `evenement` WHERE `date` = ? ";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);
            while (queryOutput.next()) {
                String queryetat = queryOutput.getString("etat");
                String querduree = queryOutput.getString("duree");
                Date querydate  = queryOutput.getDate("date");

                // Afficher les valeurs récupérées dans la console
                System.out.println("Etat: " + queryetat);
                System.out.println("Duree: " + querduree);
                System.out.println("Date: " + querydate);

                EvenementObservableList.add(new Evenement(queryetat, querduree, querydate));
            }
           System.out.println(EvenementObservableList);

            // Afficher les données dans les labels
        /*    if (!EvenementObservableList.isEmpty()) {
                Evenement evenement = EvenementObservableList.get(1); // Accéder au premier élément
                etatLabel.setText("État: " + evenement.getEtat());
                dureeLabel.setText("Durée: " + evenement.getDuree());
                dateLabel.setText("Date: " + evenement.getDate());
            } else {
                System.out.println("La liste d'événements est vide.");
            }
        } catch (SQLException e) {
          Logger.getLogger(ReveillonController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }*/
}

