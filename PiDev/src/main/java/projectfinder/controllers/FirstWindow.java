// FirstWindow.java
package projectfinder.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class FirstWindow {

    @FXML
    private TextField tfcal;

    @FXML
    private TextField tfingre;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprix;

    @FXML
    private ImageView imageView;

    private PlatAd previousController; // Reference to the previous controller

    public void setPreviousController(PlatAd previousController) {
        this.previousController = previousController;
    }

    @FXML
    void savePlat(ActionEvent event) throws SQLException {
        if (imageView.getImage() == null) {
            showAlert(Alert.AlertType.ERROR, "Veuillez choisir une image");
            return;
        }

        Blob imageData = getImageDataFromImageView(imageView);

        if (tfnom.getText().isEmpty() || tfprix.getText().isEmpty() || tfingre.getText().isEmpty() || tfcal.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs");
            return;
        }

        PlatCrud platCrud = new PlatCrud();
        if (platCrud.platExists(tfnom.getText())) {
            showAlert(Alert.AlertType.ERROR, "Ce nom de plat existe déjà");
            return;
        }

        float prix;
        int calories;
        try {
            prix = Float.parseFloat(tfprix.getText());
            calories = Integer.parseInt(tfcal.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Veuillez saisir un prix valide (nombre décimal) et des calories valides (nombre entier)");
            return;
        }

        Plat p = new Plat(tfnom.getText(), prix, tfingre.getText(), calories, imageData);
        PlatCrud ps = new PlatCrud();
        ps.ajouterEntite(p);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Plat ajouté");

        // Refresh the table in the previous controller
        if (previousController != null) {
            previousController.refreshTable();
        }

        // Close the current window
        Stage stage = (Stage) tfnom.getScene().getWindow();
        stage.close();
    }

    private Blob getImageDataFromImageView(ImageView imageView) throws SQLException {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageData = outputStream.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            return new SerialBlob(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void loadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                javafx.scene.image.Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error loading image");
            }
        }
    }

    @FXML
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

    @FXML
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
