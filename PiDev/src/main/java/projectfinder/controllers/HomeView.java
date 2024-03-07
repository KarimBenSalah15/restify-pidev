package projectfinder.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

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
