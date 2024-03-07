package projectfinder.controllers;


import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AppController {

    @FXML
    private BorderPane contentPane;

    //public void closeApp(){
       // HomePage.getWindow().close();
    //}

    public void showHomeView() throws IOException {
        contentPane.setCenter(new HomeView().getView());
    }

    public void showCartView() throws IOException {
        contentPane.setCenter(new CartView().getView());
    }
}
