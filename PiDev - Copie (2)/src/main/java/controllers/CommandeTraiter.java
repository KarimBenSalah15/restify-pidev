package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

public class CommandeTraiter {
    @FXML
    private TableView<Commande> commandeTable;

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
        commandeTable.setItems(commandeObservableList);

        TableColumn<Commande, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(120);
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Commande, String> platsColumn = new TableColumn<>("Plats");
        platsColumn.setCellValueFactory(data -> {
            List<PlatCommande> platCommandes = data.getValue().getPlatCommandes();
            StringBuilder platNames = new StringBuilder();
            for (PlatCommande platCommande : platCommandes) {
                platNames.append(platCommande.getPlat().getNom()).append(" (").append(platCommande.getQuantite()).append("), ");
            }
            return new SimpleStringProperty(platNames.toString());
        });
        platsColumn.setPrefWidth(240);
        platsColumn.setStyle("-fx-alignment: CENTER;");

        // Total column
        TableColumn<Commande, Float> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalColumn.setPrefWidth(120);
        totalColumn.setStyle("-fx-alignment: CENTER;");

        // Delete column
        TableColumn<Commande, Void> deleteColumn = new TableColumn<>("Supprimer");
        deleteColumn.setCellFactory(param -> new TableCell<Commande, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction((ActionEvent event) -> {
                    Commande commande = getTableView().getItems().get(getIndex());
                    commandeCrud.supprimerEntite(commande.getId());
                    // Refresh the table after deleting the command
                    afficherCommandes();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        deleteColumn.setPrefWidth(120);
        deleteColumn.setStyle("-fx-alignment: CENTER;");

        // Clear existing columns before adding new ones to avoid duplication
        commandeTable.getColumns().clear();
        commandeTable.getColumns().addAll(idColumn, platsColumn, totalColumn, deleteColumn);
    }
}
