package sample.Evenement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReveillonController implements Initializable {

    @FXML
    private Label etatLabel;

    @FXML
    private Label dureeLabel;

    @FXML
    private Label dateLabel;

    ObservableList<Evenement> EvenementObservableList = FXCollections.observableArrayList();
    private MyConnection cnx = null;
    Date date = Date.valueOf("2024-02-06");
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


                // Afficher les valeurs récupérées dans la console
                System.out.println("Etat: " + queryetat);
                System.out.println("Duree: " + querduree);
                System.out.println("Date: " + querydate);

                EvenementObservableList.add(new Evenement(queryid,querynom,querydate, querduree, queryetat,querytype));
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

