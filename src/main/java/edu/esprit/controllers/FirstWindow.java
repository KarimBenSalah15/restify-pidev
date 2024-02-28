package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.PlatCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class FirstWindow {

    @FXML
    private Button btn;
    @FXML
    private Button btn1;

    @FXML
    private TextField tfcal;

    @FXML
    private TextField tfingre;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprix;

    @FXML
    private ImageView imageView; // Injected from FXML

    @FXML
    void savePlat(ActionEvent event) throws SQLException {
        //sauvegarde de Plat dans la bd
        Blob imageData = getImageDataFromImageView(imageView);
        if (tfnom.getText().isEmpty() || tfprix.getText().isEmpty() || tfingre.getText().isEmpty() || tfcal.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs", ButtonType.OK);
            alert.showAndWait();
            return; // Sort de la méthode si un champ est vide
        }

        // Vérifie si les valeurs saisies sont valides (prix et calories)
        float prix;
        int calories;
        try {
            prix = Float.parseFloat(tfprix.getText());
            calories = Integer.parseInt(tfcal.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez saisir un prix valide (nombre décimal) et des calories valides (nombre entier)", ButtonType.OK);
            alert.showAndWait();
            return; // Sort de la méthode si les valeurs saisies ne sont pas valides
        }
        // Save Plat with image data to the database
        Plat p = new Plat(tfnom.getText(), prix, tfingre.getText(), calories, imageData);
        PlatCrud ps = new PlatCrud();
        ps.ajouterEntite(p);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success,Plat ajouté", new ButtonType("ok"));
        alert.show();
    }
    private Blob getImageDataFromImageView(ImageView imageView) throws SQLException {
        try {
            // Convert the JavaFX Image to a BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            // Create a ByteArrayOutputStream to write the image data to
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // Write the BufferedImage to the ByteArrayOutputStream as PNG
            ImageIO.write(bufferedImage, "png", outputStream);
            // Convert the ByteArrayOutputStream to a byte array
            byte[] imageData = outputStream.toByteArray();
            // Create a ByteArrayInputStream from the byte array
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            // Return a Blob object created from the ByteArrayInputStream
            return new javax.sql.rowset.serial.SerialBlob(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void loadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // Load the selected image into the ImageView
                FileInputStream inputStream = new FileInputStream(selectedFile);
                Image image = new Image(inputStream);
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading image", new ButtonType("OK"));
                alert.show();
            }
        }
    }
    @FXML
    private void retur(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlatAd.fxml"));
        try {

            Parent root = loader.load();


            tfnom.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load  " + e.getMessage());
        }
    }

}