package projectfinder.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class BarcodeWindowController {

    @FXML
    private ImageView imageView;

    public void setBarcodeImage(File imageFile) {
        try {
            String imageUrl = imageFile.toURI().toURL().toString();
            Image image = new Image(imageUrl);
            imageView.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
