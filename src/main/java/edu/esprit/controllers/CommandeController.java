package edu.esprit.controllers;

import edu.esprit.entities.Commande;
import edu.esprit.entities.PlatCommande;
import edu.esprit.services.CommandeCrud;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class CommandeController {

    @FXML
    private TableView<Commande> commandeTable;

    private CommandeCrud commandeCrud;

    @FXML
    public void initialize() {
        commandeCrud = new CommandeCrud();
        afficherCommandes();
    }

    private void afficherCommandes() {
        List<Commande> commandes = commandeCrud.afficherEntite();
        ObservableList<Commande> commandeObservableList = FXCollections.observableArrayList(commandes);
        commandeTable.setItems(commandeObservableList);

        TableColumn<Commande, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Commande, String> platsColumn = new TableColumn<>("Plats");
        platsColumn.setCellValueFactory(data -> {
            List<PlatCommande> platCommandes = data.getValue().getPlatCommandes();
            StringBuilder platNames = new StringBuilder();
            for (PlatCommande platCommande : platCommandes) {
                platNames.append(platCommande.getPlat().getNom()).append(" (").append(platCommande.getQuantite()).append("), ");
            }
            return new SimpleStringProperty(platNames.toString());
        });

        TableColumn<Commande, Float> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(data -> new SimpleFloatProperty(data.getValue().getTotal()).asObject());

        // Clear existing columns before adding new ones to avoid duplication
        commandeTable.getColumns().clear();
        commandeTable.getColumns().addAll(idColumn, platsColumn, totalColumn);
    }

}
