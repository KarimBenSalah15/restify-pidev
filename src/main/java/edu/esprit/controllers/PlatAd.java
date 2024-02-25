package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.PlatCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class PlatAd {

    @FXML
    private Button add;
    @FXML
    private TableView<Plat> tab;
    @FXML
    private TableColumn<Plat, Integer> tfId;

    @FXML
    private TableColumn<Plat, Integer> tfact;

    public TableColumn<Plat, Integer> getTfId() {
        return tfId;
    }

    public void setTfId(TableColumn<Plat, Integer> tfId) {
        this.tfId = tfId;
    }
    @FXML
    private TableColumn<Plat, Blob> tfimg;

    @FXML
    private TableColumn<Plat, Integer> tfcal;

    @FXML
    private TableColumn<Plat, String> tfingre;

    @FXML
    private TableColumn<Plat, String> tfnom;

    @FXML
    private TableColumn<Plat, Float> tfprix;

    public TableColumn<Plat, Integer> getTfact() {
        return tfact;
    }

    public void setTfact(TableColumn<Plat, Integer> tfact) {
        this.tfact = tfact;
    }

    public TableColumn<Plat, Integer> getTfcal() {
        return tfcal;
    }

    public void setTfcal(TableColumn<Plat, Integer> tfcal) {
        this.tfcal = tfcal;
    }

    public TableColumn<Plat, String> getTfingre() {
        return tfingre;
    }

    public void setTfingre(TableColumn<Plat, String> tfingre) {
        this.tfingre = tfingre;
    }

    public TableColumn<Plat, String> getTfnom() {
        return tfnom;
    }

    public void setTfnom(TableColumn<Plat, String> tfnom) {
        this.tfnom = tfnom;
    }

    public TableColumn<Plat, Float> getTfprix() {
        return tfprix;
    }

    public void setTfprix(TableColumn<Plat, Float> tfprix) {
        this.tfprix = tfprix;
    }

    @FXML
    void initialize() {

        tfimg.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Blob blob, boolean empty) {
                super.updateItem(blob, empty);

                if (empty || blob == null) {
                    setGraphic(null);
                } else {
                    try {
                        // Convert Blob to InputStream
                        InputStream inputStream = blob.getBinaryStream();

                        // Convert InputStream to Image
                        Image image = new Image(inputStream);

                        // Set the image to the ImageView
                        imageView.setImage(image);
                        imageView.setFitWidth(50); // Adjust width as needed
                        imageView.setFitHeight(50); // Adjust height as needed

                        // Set the ImageView as the graphic of the cell
                        setGraphic(imageView);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //URL location,ResourceBundle resources
        PlatCrud rc = new PlatCrud();
        List<Plat> plats=rc.afficherEntite();
        tfId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tfnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tfprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tfingre.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        tfcal.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tfimg.setCellValueFactory(new PropertyValueFactory<>("image"));
        // Add custom cell factory for tfact column
        tfact.setCellFactory(new Callback<>() {

            public TableCell<Plat, Integer> call(TableColumn<Plat, Integer> param) {
                return new TableCell<>() {
                    final Button deleteButton = new Button("Supprimer");
                    final Button updateButton = new Button("Modifier");

                    {
                        deleteButton.setOnAction(event -> {
                            Plat plat = getTableView().getItems().get(getIndex());

                            int platId = plat.getId(); // Retrieve the unique identifier of the Plat object
                            // Implement deletion logic here
                            tab.getItems().remove(plat); // Remove the Plat object from the TableView
                            rc.supprimerEntite(platId);
                        });

                        updateButton.setOnAction(event -> {
                            Plat plat = getTableView().getItems().get(getIndex());
                            // Implement update logic here
                            openModifPlatPage(plat);
                        });
                    }

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Set graphic with both buttons
                            setGraphic(new HBox(deleteButton, updateButton));
                        }
                    }
                };
            }
            @FXML


            private void openModifPlatPage(Plat plat) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifPlat.fxml"));
                    Parent root = loader.load();

                    ModifPlat controller = loader.getController();
                    controller.initData(plat);
                    add.getScene().setRoot(root);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to load ModifPlat.fxml: " + e.getMessage());
                }
            }

        });

        tab.getItems().addAll(plats);


}

    public void add(ActionEvent actionEvent) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstWindow.fxml"));
            try {

                Parent root = loader.load();



                add.getScene().setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load  " + e.getMessage());
            }
        }
        }


