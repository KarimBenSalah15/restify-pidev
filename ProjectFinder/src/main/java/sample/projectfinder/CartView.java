package sample.projectfinder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CartView {
    private Parent view;

    public CartView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/projectfinder/Cart.fxml"));
        Parent root = loader.load();

        this.view = root;
    }
    public Parent getView(){
        return this.view;
    }
}