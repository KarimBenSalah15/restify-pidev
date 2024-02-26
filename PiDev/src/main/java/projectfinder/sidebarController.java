package projectfinder;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class sidebarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane centrePane;

    @FXML
    private Label labelStock;

    @FXML
    private Label labelUtilisateurs;



    @FXML
    void methodeUtilisateurs(MouseEvent event) {

    }
    private boolean isClicked = false;
    @FXML
    private Text tt;

    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page));
            if (centrePane != null) {
                centrePane.setCenter(root);
            } else {
                System.out.println("centerPane is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {

    }

    private void loadStocksSearch() {
        try {
            System.out.println("Chargement de l'interface Stocks...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/projectfinder/productsearch.fxml"));
            Parent root = loader.load();
            centrePane.setCenter(root);
            System.out.println("Interface Stocks chargée avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation initiale, si nécessaire
    }

    @FXML
    private void handleClick(MouseEvent event) {
        Object source = event.getSource();
        if (source instanceof Label) {
            Label label = (Label) source;
            switch (label.getId()) {
                case "labelAccueil":
                    // Chargez la vue d'accueil, si vous en avez une
                    break;
                case "labelProduits":

                    break;
                // Ajoutez d'autres cas pour les autres labels
            }
        } else if (source instanceof ImageView) {
            ImageView imageView = (ImageView) source;
            // Gérez les clics sur les icônes ici, similaire aux labels
        }
    }

    @FXML
    void methodeStock(MouseEvent event) {
        System.out.println("Clic sur le label Stocks");
        loadPage("/sample/projectfinder/stockssearch.fxml");
    }
    @FXML
    void methodeProduit(MouseEvent event) {

        loadPage("/sample/projectfinder/productsearch.fxml");
    }

}