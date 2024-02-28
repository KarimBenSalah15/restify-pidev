package sample.Evenement;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class participantClientController implements Initializable {

    @FXML
    private ListView<String> listid;

    public void afficher() {
        ParticipantController pc=new ParticipantController();
        ObservableList<Participant> rep = (ObservableList<Participant>) pc.afficher();

        // Display data in TableView

        // Display data in ListView
        List<String> participantDetaille = rep.stream()
                .map(participant -> {



                    return "ID: [" + participant.getId() + "]  Nom:[" + participant.getNom() + "]  Prenom:[" + participant.getPrenom() + "]  Age:[" + participant.getAge()+ "]"   ;
                })
                .collect(Collectors.toList());


        listid.getItems().addAll(participantDetaille);
    }
    @FXML
    void initialize() {
        afficher();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}