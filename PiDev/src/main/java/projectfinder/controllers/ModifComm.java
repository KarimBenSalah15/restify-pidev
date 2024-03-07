package projectfinder.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ModifComm {

    @FXML
    private VBox cartPane;
    private Label totalPriceLabel;

    @FXML
    private Button validerButton;

    private Commande selectedCommande;

    private CommandeCrud commandeCrud; // Add an instance of CommandeCrud

    public ModifComm() {
        commandeCrud = new CommandeCrud(); // Instantiate CommandeCrud
    }

    public void initData(Commande commande) {
        selectedCommande = commande;
        afficherCommandeDetails();
    }

    @FXML
    private void initialize() {
        validerButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment valider la commande ?");
            alert.setContentText("Appuyez sur OK pour confirmer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    modifierCommande();

                    // Load the AffComm.fxml file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/projectfinder/AffComm.fxml"));
                    Parent root = loader.load();

                    // Get the controller of AffComm
                    AffComm controller = loader.getController();

                    // Pass any necessary data to the controller if needed
                    // For example:
                    // controller.initData(someData);

                    // Set the AffComm scene
                    cartPane.getScene().setRoot(root);

                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                    // Handle the exception here, such as displaying an error message
                }
            }
        });
    }

    private void afficherCommandeDetails() {
        try {
            List<PlatCommande> platCommandes = selectedCommande.getPlatCommandes();
            cartPane.getChildren().clear();

            if (platCommandes.isEmpty()) {
                Label emptyLabel = new Label("Panier vide");
                cartPane.getChildren().add(emptyLabel);
            } else {
                Label shoppingCartTitle = new Label("Panier");
                cartPane.getChildren().add(shoppingCartTitle);

                float total = 0;

                for (PlatCommande platCommande : platCommandes) {
                    HBox productView = cartEntryView(platCommande);
                    cartPane.getChildren().add(productView);
                    total += platCommande.getQuantite() * platCommande.getPlat().getPrix();
                }

                Separator separator = new Separator();
                separator.setOrientation(Orientation.HORIZONTAL);
                cartPane.getChildren().add(separator);

                HBox totalView = totalView(total);
                cartPane.getChildren().add(totalView);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle the exception here, such as displaying an error message
        }
    }

    private HBox totalView(float totalPrice) {
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER);
        Label totalLabel = new Label("Total :");
        totalLabel.setStyle("-fx-font-size: 15pt");
        this.totalPriceLabel = new Label(String.valueOf(totalPrice));
        layout.getChildren().addAll(totalLabel, this.totalPriceLabel);
        return layout;
    }

    private HBox cartEntryView(PlatCommande platCommande) throws IOException, SQLException {
        HBox layout = new HBox();
        Plat plat = platCommande.getPlat();

        Label platName = new Label(plat.getNom());
        platName.setPrefWidth(100);
        platName.setStyle("-fx-font-size: 15pt; -fx-padding: 5px");

        Label quantity = new Label(String.valueOf(platCommande.getQuantite()));
        quantity.setStyle("-fx-padding: 5px");

        Button plusButton = new Button("+");
        plusButton.setOnAction(e -> {
            int newQuantity = platCommande.getQuantite() + 1;
            platCommande.setQuantite(newQuantity);
            quantity.setText(String.valueOf(newQuantity));
            updateTotal(plat.getPrix());
            updatePlatQuantity(plat.getId(), newQuantity); // Update quantity in the database
        });

        Button minusButton = new Button("-");
        minusButton.setOnAction(e -> {
            int newQuantity = platCommande.getQuantite() - 1;
            if (newQuantity >= 0) {
                platCommande.setQuantite(newQuantity);
                quantity.setText(String.valueOf(newQuantity));
                updateTotal(-plat.getPrix());
                updatePlatQuantity(plat.getId(), newQuantity); // Update quantity in the database
            }
        });

        Label price = new Label(String.valueOf(plat.getPrix()));
        price.setStyle("-fx-padding: 5px");

        layout.getChildren().addAll(platName, minusButton, quantity, plusButton, price);
        return layout;
    }

    private void updateTotal(float amount) {
        float currentTotal = Float.parseFloat(this.totalPriceLabel.getText());
        float newTotal = currentTotal + amount;
        if (newTotal >= 0) {
            this.totalPriceLabel.setText(String.valueOf(newTotal));
        }
    }

    private void updatePlatQuantity(int platId, int newQuantity) {
        CommandeCrud commandeCrud = new CommandeCrud();
        commandeCrud.modifierCommande3(platId, newQuantity);
    }

    private void modifierCommande() throws IOException, SQLException {
        try {
            List<PlatCommande> platCommandes = selectedCommande.getPlatCommandes();
            float total = 0;

            for (PlatCommande platCommande : platCommandes) {
                int platId = platCommande.getPlat().getId();
                int newQuantity = platCommande.getQuantite();
                float itemTotal = newQuantity * platCommande.getPlat().getPrix();
                total += itemTotal;
                // Use modifierCommande2 to update plat quantity
                commandeCrud.modifierCommande2(platId, newQuantity, selectedCommande.getId());
            }

            // Set the total price of the commande
            selectedCommande.setTotal(total);

            // Update the commande with the new total price
            commandeCrud.modifierCommande3(selectedCommande.getId(), total);

            this.totalPriceLabel.setText(String.valueOf(total));

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("La commande a été modifiée avec succès.");
            successAlert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Une erreur s'est produite lors de la modification de la commande.");
            errorAlert.showAndWait();
        }
    }
}
