package edu.esprit.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PersonDetailsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfnomaff;

    @FXML
    private TextField tfprenomaff;

    @FXML
    void initialize() {
    }

    public void setTfnomaff(String tfnomaff) {
        this.tfnomaff.setText(tfnomaff);
    }

    public void setTfprenomaff(String tfprenomaff) {
        this.tfprenomaff.setText(tfprenomaff);
    }
}
