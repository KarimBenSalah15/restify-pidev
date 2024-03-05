package edu.esprit.controllers;

import edu.esprit.entities.Commande;
import edu.esprit.entities.PlatCommande;
import edu.esprit.services.CommandeCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

public class CommandeTraiter {
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
        List<Commande> commandes = commandeCrud.afficherEntite3();
        ObservableList<Commande> commandeObservableList = FXCollections.observableArrayList(commandes);
        commandeListView.setItems(commandeObservableList);

        commandeListView.setCellFactory(param -> new ListCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Commande commande = getItem();
                    if (commande != null) {
                        commandeCrud.supprimerEntite(commande.getId());
                        afficherCommandes(); // Refresh the list after deleting the command
                    }
                });
            }

            @Override
            protected void updateItem(Commande commande, boolean empty) {
                super.updateItem(commande, empty);
                if (empty || commande == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    List<PlatCommande> platCommandes = commande.getPlatCommandes();
                    StringBuilder platNames = new StringBuilder();
                    for (PlatCommande platCommande : platCommandes) {
                        platNames.append(platCommande.getPlat().getNom()).append(" (").append(platCommande.getQuantite()).append("), ");
                    }
                    setText("ID: " + commande.getId() + "\nPlats: " + platNames.toString() + "\nTotal: " + commande.getTotal());
                    setGraphic(deleteButton);
                }
            }
        });
    }
}
