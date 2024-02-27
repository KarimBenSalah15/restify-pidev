package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class FenetreDashboardClient {

    @FXML
    private ResourceBundle resources;

    @FXML
    private BorderPane bp_modif;

    @FXML
    private URL location;

    @FXML
    private ImageView btn_modif;

    @FXML
    void modif_info(MouseEvent event) {
        bp_modif.setVisible(true);
        affichagemodif();
    }

    @FXML
    void initialize() {
        bp_modif.setVisible(false);
    }
    private void affichagemodif(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreProfilClient.fxml"));
        try {
            Parent root = loader.load();
            if (bp_modif != null) {
                bp_modif.setCenter(root);
            } else {
                System.out.println("centerPane is null");
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}