package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.twilio.rest.api.v2010.account.Message;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;

import entities.Reclamation;
import entities.Reponse;
import services.ReclamtionCrud;
import services.ReponseCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import static com.twilio.example.Example.ACCOUNT_SID;
import static com.twilio.example.Example.AUTH_TOKEN;

public class AddRepControllers {
    private final String ACCOUNT_SID = "ACa1cc1061eea100b535bc83fec76b535f";
    private final String AUTH_TOKEN ="83e99742ddf3feed7aa2105e8ffcfc0a";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ADDBtn;



    @FXML
    private TextArea message;
    @FXML
    private AnchorPane pn;
 private  int id;

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    void gobackR(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader((getClass().getResource("/FenetreAffichage.fxml")));
        try {
            Parent root= loader.load();
            ;

            message.getScene().setRoot(root);


        }catch (IOException e)
        {System.out.println(e.getMessage());}

    }

    @FXML
    void saveR(ActionEvent event) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(calendar.getTime().getTime());

        // Check if type is null or empty


        // Check if message is null or empty
        if (message.getText() == null || message.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le champ de message ne peut pas être vide.", ButtonType.OK);
            alert.showAndWait();
            return; // Stop the method if the message is null or empty
        }
        // Save the reclamation to the database
        ReponseCrud rc = new ReponseCrud();

        Reponse r = new Reponse(message.getText(),currentDate, 1, id);

        rc.ajouterEntite(r);

        ReclamtionCrud rr=new ReclamtionCrud();
        Reclamation c=new  Reclamation();
        rr.updateEtat(id);



        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Réclamation ajoutée avec succès", ButtonType.OK);
        alert.show();
        FXMLLoader loader=new FXMLLoader((getClass().getResource("/FenetreDashboardAdmin.fxml")));
        try {
            Parent root= loader.load();
            ;

            message.getScene().setRoot(root);


        }catch (IOException e)
        {System.out.println(e.getMessage());}

        // Refresh the TableView after adding



    }

    @FXML
    void initialize() {

    }


}
