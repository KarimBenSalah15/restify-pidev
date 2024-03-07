package projectfinder.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class personndetailscontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField resnom;

    @FXML
    private TextField resprnom;

    @FXML
    void initialize() {

    }

    public void setResnom(String resnom) {
        this.resnom.setText(resnom);
    }

    public void setResprnom(String resprnom) {
        this.resprnom.setText(resprnom);
    }
}
