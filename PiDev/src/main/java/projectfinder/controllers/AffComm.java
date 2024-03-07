package projectfinder.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class AffComm {
    @FXML
    private ListView<Commande> commandeListView;

    private CommandeCrud commandeCrud;

    @FXML
    private Text notrait;

    @FXML
    private void initialize() {
        commandeCrud = new CommandeCrud();
        afficherCommandes();
    }

    private void afficherCommandes() {
        List<Commande> commandes = commandeCrud.afficherEntite2();
        ObservableList<Commande> commandeObservableList = FXCollections.observableArrayList(commandes);
        commandeListView.setItems(commandeObservableList);

        commandeListView.setCellFactory(param -> createCommandeListCell());
    }

    private ListCell<Commande> createCommandeListCell() {
        return new ListCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Commande commande = getListView().getItems().get(getIndex());
                    openUpdateDialog(commande);
                });
                // Align the button to the right
                updateButton.setMaxWidth(Double.MAX_VALUE);
                updateButton.setStyle("-fx-alignment: CENTER_RIGHT;");
            }

            @Override
            protected void updateItem(Commande commande, boolean empty) {
                super.updateItem(commande, empty);
                if (empty || commande == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(buildCommandeText(commande));
                    setGraphic(updateButton);
                }
            }
        };
    }

    private String buildCommandeText(Commande commande) {
        List<PlatCommande> platCommandes = commande.getPlatCommandes();
        StringBuilder platNames = new StringBuilder();
        for (PlatCommande platCommande : platCommandes) {
            platNames.append(platCommande.getPlat().getNom()).append(" (").append(platCommande.getQuantite()).append("), ");
        }
        return "ID: " + commande.getId() + "\nPlats: " + platNames.toString() + "\nTotal: " + commande.getTotal();
    }

    private void openUpdateDialog(Commande commande) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/projectfinder/ModifComm.fxml"));
        try {
            Parent root = loader.load();

            ModifComm controller = loader.getController();
            controller.initData(commande); // Pass the selected Commande object to the controller
            notrait.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load  " + e.getMessage());
        }
    }
}
