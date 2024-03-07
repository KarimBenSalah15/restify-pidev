package sample.projectfinder;

import sample.projectfinder.Plat;
import sample.projectfinder.PlatCrud;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private Button historiqueReclamationButton;

    @FXML
    public void initialize() {
        productGridPane.getChildren().clear();

        List<Plat> plats = platCrud.afficherEntite();

        // Add "historique reclammation" button
        historiqueReclamationButton = new Button("Historique Reclamation");
        historiqueReclamationButton.setOnAction(this::openHistoriqueReclamationScene);
        productGridPane.add(historiqueReclamationButton, 0, 0);

        int rowIndex = 1; // Start at the second row after the button

        for (int i = 0; i < plats.size(); i += 5) {
            HBox rowContainer = new HBox(); // Container for the row
            rowContainer.setSpacing(20); // Adjust spacing between cards
            rowContainer.setPadding(new Insets(10));
            productGridPane.add(rowContainer, 0, rowIndex);

            for (int j = i; j < Math.min(i + 3, plats.size()); j++) {
                Plat plat = plats.get(j);

                // Container for the card
                VBox cardContainer = new VBox();
                cardContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");
                cardContainer.getStyleClass().add("card-container");
                cardContainer.setSpacing(10); // Adjust spacing between content
                cardContainer.setMaxWidth(300); // Set maximum width for the card
                rowContainer.getChildren().add(cardContainer);

                // Text details
                Label nameLabel = new Label(plat.getNom());
                nameLabel.setPadding(new Insets(5));
                nameLabel.setStyle("-fx-font-weight: bold;");
                nameLabel.setMaxWidth(Double.MAX_VALUE);
                nameLabel.setAlignment(Pos.CENTER);
                cardContainer.getChildren().add(nameLabel);

                Label prixLabel = new Label("Prix: " + plat.getPrix());
                prixLabel.setPadding(new Insets(5));
                prixLabel.setMaxWidth(Double.MAX_VALUE);
                prixLabel.setAlignment(Pos.CENTER);
                cardContainer.getChildren().add(prixLabel);

                Label ingredientsLabel = new Label("Ingrédients: " + plat.getIngredients());
                ingredientsLabel.setPadding(new Insets(5));
                ingredientsLabel.setMaxWidth(Double.MAX_VALUE);
                ingredientsLabel.setAlignment(Pos.CENTER);
                cardContainer.getChildren().add(ingredientsLabel);

                Label caloriesLabel = new Label("Calories: " + plat.getCalories());
                caloriesLabel.setPadding(new Insets(5));
                caloriesLabel.setMaxWidth(Double.MAX_VALUE);
                caloriesLabel.setAlignment(Pos.CENTER);
                cardContainer.getChildren().add(caloriesLabel);

                // Button
                Button addButton = new Button("Ajouter Panier");
                addButton.setOnAction(event -> {
                    ShoppingCart.getInstance().addPlat(new CardEntry(plat, 1).getPlat()); // Create CardEntry with the Plat instance
                });
                addButton.setPadding(new Insets(5));
                addButton.setMaxWidth(Double.MAX_VALUE);
                addButton.setAlignment(Pos.CENTER);
                cardContainer.getChildren().add(addButton);

                // Convert Blob to Image
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(plat.getImage().getBytes(1, (int) plat.getImage().length()));
                    BufferedImage bImage = ImageIO.read(bis);
                    Image image = SwingFXUtils.toFXImage(bImage, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150); // Adjust width as needed
                    imageView.setFitHeight(150); // Adjust height as needed
                    imageView.setPreserveRatio(true); // Preserve image aspect ratio
                    cardContainer.getChildren().add(imageView); // Add the ImageView to the VBox
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }

            // Increment the row index for the next row
            rowIndex += 1;
        }
    }

    // Method to open the AffComm scene
    private void openHistoriqueReclamationScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/projectfinder/AffComm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load AffComm.fxml: " + e.getMessage());
        }
    }

    // Handle MouseEvent
    @FXML
    public void histo(MouseEvent mouseEvent) {
        openHistoriqueReclamationScene(null);
    }
}
