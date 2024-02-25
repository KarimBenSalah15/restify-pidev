package edu.esprit.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PersonneDetailsControllers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
private int id;

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
    public void setId(int id) {
        this.id=id;
    }
}
