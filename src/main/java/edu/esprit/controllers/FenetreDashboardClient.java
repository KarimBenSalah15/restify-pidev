package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import edu.esprit.tools.MyConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


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
    private Label btn_reservC;

    Connection cnx2;

    public FenetreDashboardClient() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    @FXML
    void modif_info(MouseEvent event) {
        bp_modif.setVisible(true);

    }

    @FXML
    void initialize() {
        bp_modif.setVisible(false);
    }
    private void affress(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res.fxml"));
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
    @FXML
    void reserverC(MouseEvent event) {
        bp_modif.setVisible(true);
        affress();
    }



    @FXML
    void decoclient(MouseEvent event) {
        Stage deco = (Stage) btn_modif.getScene().getWindow();
        deco.close();
    }
}