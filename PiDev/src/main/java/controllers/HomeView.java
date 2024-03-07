package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HomeView {

    private Parent view;


    public HomeView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/projectfinder/home.fxml"));
        Parent root = loader.load();

        this.view = root;
    }

    public Parent getView(){
        return this.view;
    }


}
