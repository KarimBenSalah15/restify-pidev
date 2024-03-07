package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.MyConnection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccueilController {
    @FXML
    private HBox ImageContainer;

    @FXML
    private Label FruitPriceLable;
    private HBox currentRow;
    @FXML
    private Label FruitNameLable;
    @FXML
    private Button ajoutP;

    @FXML
    private Button Next;
    @FXML
    private ImageView fruitimg;
    @FXML
    private ImageView navCart;

    @FXML
    private Button Previous;
    private int columnIndex = 0;
    private int rowIndex = 0;
    private int currentPageIndex = 0;
    private static final int ITEMS_PER_PAGE = 9;

    // Connexion à la base de données
    private Connection connection;
    private Parent view;

    @FXML
    void initialize() {
        currentRow = new HBox(); // Initialisez la variable de classe currentRow
        ImageContainer.getChildren().add(currentRow);
        // Connexion à la base de données
        connection = MyConnection.getInstance().getCnx();
        displayPage(currentPageIndex);
    }

    private int countProducts() {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) AS total FROM produit";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions
        }
        return count;
    }

    private void displayPage(int pageIndex) {
        ImageContainer.getChildren().clear(); // Effacez le contenu précédent
        try {
            String query = "SELECT image, nom, prix FROM produit LIMIT ?, ?";
            int offset = pageIndex * ITEMS_PER_PAGE;

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, offset);
                statement.setInt(2, ITEMS_PER_PAGE);

                try (ResultSet resultSet = statement.executeQuery()) {
                    HBox hbox = new HBox(); // Créer une HBox pour aligner les cartes horizontalement
                    hbox.setAlignment(Pos.CENTER); // Centrer les éléments horizontalement
                    VBox vbox = new VBox(); // Créer une VBox pour aligner les lignes de cartes verticalement
                    while (resultSet.next()) {
                        String imageUrl = resultSet.getString("image");
                        String nomProduit = resultSet.getString("nom");
                        double prixProduit = resultSet.getDouble("prix");

                        // Créer une carte pour le produit
                        BorderPane card = createProductCard(imageUrl, nomProduit, prixProduit);

                        // Ajouter la carte à la HBox
                        hbox.getChildren().add(card);

                        // Si nous avons ajouté 3 cartes à la HBox, ajouter la HBox à la VBox et créer une nouvelle HBox
                        if (hbox.getChildren().size() == 3) {
                            vbox.getChildren().add(hbox);
                            hbox = new HBox();
                            hbox.setAlignment(Pos.CENTER);
                        }
                    }

                    // Si la dernière ligne n'est pas complète, ajouter la dernière HBox à la VBox
                    if (!hbox.getChildren().isEmpty()) {
                        vbox.getChildren().add(hbox);
                    }

                    // Centrer la VBox dans ImageContainer
                    vbox.setAlignment(Pos.CENTER);

                    // Ajouter la VBox à ImageContainer
                    ImageContainer.getChildren().add(vbox);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions
        }
    }


    @FXML
    void nextPage(ActionEvent event) {
        if (currentPageIndex < (countProducts() - 1) / ITEMS_PER_PAGE) {
            currentPageIndex++;
            displayPage(currentPageIndex);
        }
    }

        @FXML
    void previousPage(ActionEvent event) {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            displayPage(currentPageIndex);
        }
    }

    private BorderPane createProductCard(String imageUrl, String nomProduit, double prixProduit) {
        // Création de l'objet Image à partir de l'URL
        Image image = new Image(imageUrl);

        // Création d'un ImageView pour afficher l'image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150); // Définissez la largeur souhaitée
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true); // Préservez le ratio de l'image

        // Création d'une Label pour le nom du produit
        Label nameLabel = new Label(nomProduit);
        nameLabel.setMaxWidth(Double.MAX_VALUE); // Permet au label de s'étendre horizontalement

        // Création d'une mise en page pour les détails du produit (nom et prix)
        VBox detailsLayout = new VBox(5);
        detailsLayout.getChildren().addAll(nameLabel);
        detailsLayout.setAlignment(Pos.BOTTOM_CENTER); // Alignement à gauche
        detailsLayout.setSpacing(5); // Espacement entre les éléments

        // Création d'une mise en page pour la carte du produit
        HBox cardLayout = new HBox(10); // Utilisez une HBox pour aligner horizontalement l'image et les détails
        cardLayout.getChildren().addAll(detailsLayout, imageView); // Ajoutez d'abord les détails puis l'image
        cardLayout.setAlignment(Pos.CENTER); // Centrez verticalement
        cardLayout.setPadding(new Insets(5)); // Ajoutez un padding pour l'espace autour de la carte

        // Création d'une mise en page pour la carte du produit
        BorderPane cardPane = new BorderPane();
        cardPane.setCenter(cardLayout);
        cardPane.setMinSize(300, 200); // Taille minimale de la carte
        cardPane.setMaxSize(300, 200); // Taille maximale de la carte
        cardPane.setStyle("-fx-border-color: #EF6C00; -fx-border-width: 8px;"); // Ajoutez une bordure orange autour de la carte

        // Ajoutez un événement de clic pour afficher l'image sélectionnée
        cardPane.setOnMouseClicked(event -> {
            fruitimg.setImage(image); // Afficher l'image sélectionnée à côté
            FruitNameLable.setText(nomProduit); // Afficher le nom du produit sélectionné
            FruitPriceLable.setText("Prix: " + prixProduit + " TND"); // Afficher le prix dans FruitPriceLable
        });

        return cardPane;
    }


    public void showCartView(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cartproduit.fxml"));
            Parent root = loader.load();
            CartProduit cartProduitController = loader.getController();
            cartProduitController.setPreviousController(this); // Set the reference to the previous controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load cartproduit.fxml: " + e.getMessage());
        }
    }
    @FXML
    void ajoutPanier(ActionEvent event) {
        // Get the details of the selected product
        String nomProduit = FruitNameLable.getText();
        double prixProduit = Double.parseDouble(FruitPriceLable.getText().replaceAll("[^0-9.]", "")); // Extract the price from the label

        // Create a Produit instance with the selected product details
        ProductSearchModel produit = new ProductSearchModel(nomProduit, (int) prixProduit); // Assuming Produit class has a constructor accepting name and price

        // Add the selected product to the shopping cart
        ShoppingCart2.getInstance().addProduit(produit);
    }




}





