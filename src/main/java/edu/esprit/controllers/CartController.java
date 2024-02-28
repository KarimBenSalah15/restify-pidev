package edu.esprit.controllers;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Plat;
import edu.esprit.services.CommandeCrud;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CartController {

    @FXML
    private VBox cartPane;
    private Label totalPriceLabel;

    @FXML
    private Button validerButton;

    @FXML
    private void initialize() {
        try {
            List<CardEntry> entries = ShoppingCart.getInstance().getEntries();
            cartPane.getChildren().clear();

            if (entries.isEmpty()) {
                Label emptyLabel = new Label("Cart empty");
                cartPane.getChildren().add(emptyLabel);
            } else {
                Label shoppingCartTitle = new Label("Panier");
                cartPane.getChildren().add(shoppingCartTitle);

                for (CardEntry cardEntry : entries) {
                    HBox productView = cartEntryView(cardEntry);
                    cartPane.getChildren().add(productView);
                }

                Separator separator = new Separator();
                separator.setOrientation(Orientation.HORIZONTAL);
                cartPane.getChildren().add(separator);

                HBox totalView = totalView(ShoppingCart.getInstance().calculTotal());
                cartPane.getChildren().add(totalView);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle the exception here, such as displaying an error message
        }

        validerButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment valider la commande ?");
            alert.setContentText("Appuyez sur OK pour confirmer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                validerCommande();
            }
        });
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

    private HBox cartEntryView(CardEntry cardEntry) throws IOException, SQLException {
        HBox layout = new HBox();
        Plat plat = cardEntry.getPlat();

        // Assuming you want to access the image from the CardEntry's Plat object
        try (ByteArrayInputStream bis = new ByteArrayInputStream(plat.getImage().getBytes(1, (int) plat.getImage().length()))) {
            BufferedImage bImage = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(bImage, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50); // Adjust width as needed
            imageView.setFitHeight(50); // Adjust height as needed
            layout.getChildren().add(imageView); // Add the ImageView to the HBox
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle the exception here, such as displaying a placeholder image
        }

        Label platName = new Label(plat.getNom());
        platName.setPrefWidth(100);
        platName.setStyle("-fx-font-size: 15pt; -fx-padding: 5px");
        Label quantity = new Label(String.valueOf(cardEntry.getQuantity()));
        quantity.setStyle("-fx-padding: 5px");
        Button plusButton = new Button("+");
        plusButton.setStyle("-fx-padding: 5px");
        plusButton.setUserData(cardEntry.getPlat().getNom()); // Set the user data directly on the button
        plusButton.setOnAction(e -> {
            String name = (String) ((Node) e.getSource()).getUserData(); // Retrieve user data from the button
            ShoppingCart.getInstance().addPlat(new CardEntry(plat, 1).getPlat());
            quantity.setText(String.valueOf(ShoppingCart.getInstance().getQuantity(name)));
            this.totalPriceLabel.setText(String.valueOf(ShoppingCart.getInstance().calculTotal()));
        });

        Button minusButton = new Button("-");
        minusButton.setStyle("-fx-padding: 5px");
        minusButton.setUserData(cardEntry.getPlat().getNom()); // Set the user data directly on the button
        minusButton.setOnAction(e -> {
            String name = (String) ((Node) e.getSource()).getUserData(); // Retrieve user data from the button
            ShoppingCart.getInstance().removePlat(name);
            quantity.setText(String.valueOf(ShoppingCart.getInstance().getQuantity(name)));
            this.totalPriceLabel.setText(String.valueOf(ShoppingCart.getInstance().calculTotal()));
        });

        Label price = new Label(String.valueOf(plat.getPrix()));
        price.setStyle("-fx-padding: 5px");
        layout.getChildren().addAll(platName, plusButton, quantity, minusButton, price);
        return layout;
    }

    private void validerCommande() {
        try {
            List<CardEntry> entries = ShoppingCart.getInstance().getEntries();
            float totalPrice = ShoppingCart.getInstance().calculTotal();
            int totalQuantity = entries.stream().mapToInt(CardEntry::getQuantity).sum();

            Commande commande = new Commande(totalQuantity, totalPrice, entries.stream().map(CardEntry::getPlat).toList());
            CommandeCrud commandeCrud = new CommandeCrud();
            commandeCrud.ajouterEntite(commande);

            // Clear the shopping cart after validation

            // Show a success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("La commande a été validée avec succès.");
            successAlert.showAndWait();
        } catch (Exception e) {
            // Handle exceptions, such as database errors
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Une erreur s'est produite lors de la validation de la commande.");
            errorAlert.showAndWait();
        }
    }
}
