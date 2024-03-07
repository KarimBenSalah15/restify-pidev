package sample.projectfinder;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CommandeController {

    @FXML
    private TableView<Commande> commandeTable;

    private CommandeCrud commandeCrud;

    @FXML
    private void initialize() {
        commandeCrud = new CommandeCrud();
        afficherCommandes();
    }

    private void afficherCommandes() {
        List<Commande> commandes = commandeCrud.afficherEntite2();
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

        // Traitement column
        TableColumn<Commande, Void> traiColumn = new TableColumn<>("Traitement");
        traiColumn.setCellFactory(param -> new TableCell<Commande, Void>() {
            private final Button traiterButton = new Button("Traiter");

            {
                traiterButton.setOnAction((ActionEvent event) -> {
                    Commande commande = getTableView().getItems().get(getIndex());
                    commandeCrud.traiterCommande(commande.getId());
                    // Refresh the table after updating the command
                    afficherCommandes();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(traiterButton);
                }
            }
        });
        traiColumn.setPrefWidth(120);
        traiColumn.setStyle("-fx-alignment: CENTER;");

        // Clear existing columns before adding new ones to avoid duplication
        commandeTable.getColumns().clear();
        commandeTable.getColumns().addAll(idColumn, platsColumn, totalColumn, traiColumn);
    }



}

