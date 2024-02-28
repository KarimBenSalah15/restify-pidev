package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.PlatCrud;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomeController {

    @FXML
    private GridPane productGridPane;

    private PlatCrud platCrud = new PlatCrud();

    @FXML
    public void initialize() {
        productGridPane.getChildren().clear();

        List<Plat> plats = platCrud.afficherEntite();

        int rowIndex = 0; // Start at the first row

        for (Plat plat : plats) {
            HBox cardContainer = new HBox(); // Container for the card
            cardContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px; ");

            cardContainer.getStyleClass().add("card-container");
            productGridPane.add(cardContainer, 0, rowIndex);

            // Container for text and button
            VBox textAndButtonContainer = new VBox();
            textAndButtonContainer.setPadding(new Insets(10));
            cardContainer.getChildren().add(textAndButtonContainer);

            // Text details
            Label nameLabel = new Label("Nom: " + plat.getNom());
            nameLabel.setPadding(new Insets(5));
            textAndButtonContainer.getChildren().add(nameLabel);

            Label prixLabel = new Label("Prix: " + plat.getPrix());
            prixLabel.setPadding(new Insets(5));
            textAndButtonContainer.getChildren().add(prixLabel);

            Label ingredientsLabel = new Label("Ingrédients: " + plat.getIngredients());
            ingredientsLabel.setPadding(new Insets(5));
            textAndButtonContainer.getChildren().add(ingredientsLabel);

            Label caloriesLabel = new Label("Calories: " + plat.getCalories());
            caloriesLabel.setPadding(new Insets(5));
            textAndButtonContainer.getChildren().add(caloriesLabel);

            // Button
            Button addButton = new Button("Ajouter Panier");
            addButton.setOnAction(event -> {
                ShoppingCart.getInstance().addPlat(new CardEntry(plat, 1).getPlat()); // Create CardEntry with the Plat instance
            });
            addButton.setPadding(new Insets(5));
            textAndButtonContainer.getChildren().add(addButton);

            // Convert Blob to Image
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(plat.getImage().getBytes(1, (int) plat.getImage().length()));
                BufferedImage bImage = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bImage, null);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100); // Adjust width as needed
                imageView.setFitHeight(100); // Adjust height as needed
                cardContainer.getChildren().add(imageView); // Add the ImageView to the HBox
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }

            // Increment the row index for the next Plat
            rowIndex += 1;
        }
    }
}
