package sample.Evenement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class participantClientController  {

    @FXML
    private ListView<String> listid;
    ObservableList<Participant> participantsObservableList = FXCollections.observableArrayList();
    public void afficher() {
        MyConnection connectNow = new MyConnection();
        Connection connectDB = connectNow.getCnx();

        try {
            String participantViewQuery = "SELECT * from  participant ";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(participantViewQuery);
            while (queryOutput.next()) {
                Integer queryid = queryOutput.getInt("ID");
                String querynom = queryOutput.getString("Nom");

                String queryprenom = queryOutput.getString("Prenom");
                Integer queryage = queryOutput.getInt("Age");
                participantsObservableList.add(new Participant(queryid, querynom, queryprenom, queryage));
            }

        } catch (SQLException e) {
            Logger.getLogger(ParticipantController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

        System.out.println(participantsObservableList);
        List<String> reclamationDescriptions = participantsObservableList.stream()
                .map(participant -> {


                    return "ID: [" + participant.getId() + "]  Nom:[" + participant.getNom() + "]  Prenom:[" + participant.getPrenom() + "]  Age:[" + participant.getAge() + "]" ;
                })
                .collect(Collectors.toList());


        listid.getItems().addAll(reclamationDescriptions);

    }



    public void initialize() {
        System.out.println("oooooooooooooooo");
        afficher();
    }
}