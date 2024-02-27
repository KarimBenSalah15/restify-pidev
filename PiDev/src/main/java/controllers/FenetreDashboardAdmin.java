package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FenetreDashboardAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private BorderPane bp_affich;

    @FXML
    private URL location;

    @FXML
    private HBox btn_commande;

    @FXML
    private HBox btn_event;

    @FXML
    private Label btn_plat;

    @FXML
    private HBox btn_recla;

    @FXML
    private HBox btn_resa;

    @FXML
    private HBox btn_stock;

    @FXML
    private HBox btn_user;

    @FXML
    void btn_affichuser(MouseEvent event) {
        bp_affich.setVisible(true);
        affichagetableau();
    }

    @FXML
    void initialize() {
        bp_affich.setVisible(false);
    }
    private void affichagetableau(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreAffichage.fxml"));
        try {
            Parent root = loader.load();
            if (bp_affich != null) {
                bp_affich.setCenter(root);
            } else {
                System.out.println("centerPane is null");
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void decocadmin(MouseEvent event) {
        Stage deco = (Stage) btn_user.getScene().getWindow();
        deco.close();
    }
}
