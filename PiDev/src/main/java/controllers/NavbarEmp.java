package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class NavbarEmp {
    @FXML
    private BorderPane bp_affich;

    @FXML
    private Text notrait;

    @FXML
    private Text trait;

    private Parent commandeFXML;

    @FXML
    void initialize() {
        loadCommandeFXML();
        affichagetableau();
    }

    private void loadCommandeFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/commande.fxml"));
        try {
            commandeFXML = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void affichagetableau() {
        if (bp_affich != null && commandeFXML != null) {
            bp_affich.setCenter(commandeFXML);
        } else {
            System.out.println("centerPane or commandeFXML is null");
        }
    }

    private void affichagetableau2() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/commandeTraiter.fxml"));
        try {
            Parent root = loader.load();
            if (bp_affich != null) {
                bp_affich.setCenter(root);
            } else {
                System.out.println("centerPane is null");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void decoemp(MouseEvent event) {
        /*Stage deco = (Stage) btn_modif.getScene().getWindow();
        deco.close();*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreConnexion.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            Stage stage = (Stage) notrait.getScene().getWindow();
            stage.setX(450);
            stage.setY(200);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void notrait(MouseEvent mouseEvent) {
        affichagetableau();
    }

    public void trait(MouseEvent mouseEvent) {
        affichagetableau2();
    }
}
