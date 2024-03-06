package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tools.MyConnection;

public class FenetreDashboardClient {

    @FXML
    private ResourceBundle resources;

    @FXML
    private BorderPane bp_modif;

    @FXML
    private URL location;

    @FXML
    private ImageView btn_modif;

    Connection cnx2;

    public FenetreDashboardClient() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    @FXML
    void modif_info(MouseEvent event) {
        bp_modif.setVisible(true);
        affichagemodif();
    }

    @FXML
    void initialize() {
        bp_modif.setVisible(false);
    }

    public void affichagemodif() {
        String req2 = "SELECT nom, prenom, email, tel, login from Utilisateur where id=?";
        try {
            PreparedStatement st1 = cnx2.prepareStatement(req2);
            st1.setInt(1, MyConnection.getInstance().getIdenvoi());
            ResultSet rs = st1.executeQuery();
            while (rs.next()) {
                Utilisateur p = new Utilisateur();
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));
                p.setTel(rs.getInt("tel"));
                p.setLogin(rs.getString("login"));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreProfilClient.fxml"));
                try {
                    Parent root = loader.load();
                    if (bp_modif != null) {
                        FenetreProfilClient pc = loader.getController();
                        pc.getTf_nomprof().setText(p.getNom());
                        pc.getTf_prenomprof().setText(p.getPrenom());
                        pc.getTf_emailprof().setText(p.getEmail());
                        pc.getTf_telprof().setText(String.valueOf(p.getTel()));
                        pc.getTf_loginprof().setText(p.getLogin());
                        bp_modif.setCenter(root);
                    } else {
                        System.out.println("centerPane is null");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }



    @FXML
    void decoclient(MouseEvent event) {
        /*Stage deco = (Stage) btn_modif.getScene().getWindow();
        deco.close();*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreConnexion.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            Stage stage = (Stage) btn_modif.getScene().getWindow();
            stage.setX(450);
            stage.setY(200);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}