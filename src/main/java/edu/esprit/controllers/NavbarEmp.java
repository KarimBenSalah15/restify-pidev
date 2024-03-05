package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

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

    public void notrait(MouseEvent mouseEvent) {
        affichagetableau();
    }

    public void trait(MouseEvent mouseEvent) {
        affichagetableau2();
    }
}
