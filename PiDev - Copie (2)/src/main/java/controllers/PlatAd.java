package controllers;

import controllers.Plat;
import controllers.PlatCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

public class PlatAd {

    @FXML
    private Button add;

    @FXML
    private TableView<Plat> tab;

    @FXML
    private TableColumn<Plat, Integer> tfId;

    @FXML
    private TableColumn<Plat, Integer> tfact;

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
                        Image image = new Image(blob.getBinaryStream());
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        refreshTable();
    }

    public void refreshTable() {
        PlatCrud rc = new PlatCrud();
        List<Plat> plats = rc.afficherEntite();

        tab.getItems().clear(); // Clear previous items
        tab.getItems().addAll(plats);

        tfId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tfnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tfprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tfingre.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        tfcal.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tfimg.setCellValueFactory(new PropertyValueFactory<>("image"));

        tfact.setCellFactory(new Callback<>() {
            public TableCell<Plat, Integer> call(TableColumn<Plat, Integer> param) {
                return new TableCell<>() {
                    final Button deleteButton = new Button("Supprimer");
                    final Button updateButton = new Button("Modifier");

                    {
                        deleteButton.setOnAction(event -> {
                            Plat plat = getTableView().getItems().get(getIndex());
                            int platId = plat.getId();

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation de suppression");
                            alert.setHeaderText(null);
                            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce plat ?");

                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {
                                    tab.getItems().remove(plat);
                                    rc.supprimerEntite(platId);
                                }
                            });
                        });
                        updateButton.setOnAction(event -> {
                            Plat plat = getTableView().getItems().get(getIndex());
                            openModifPlatPage(plat);
                        });
                    }

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(deleteButton, updateButton));
                        }
                    }
                };
            }
        });
    }

    @FXML
    public void add(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstWindow.fxml"));
            Parent root = loader.load();
            FirstWindow controller = loader.getController();
            controller.setPreviousController(this); // Pass a reference to this controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FirstWindow.fxml: " + e.getMessage());
        }
    }

    private void openModifPlatPage(Plat plat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifPlat.fxml"));
            Parent root = loader.load();
            ModifPlat controller = loader.getController();
            controller.initData(plat);
            controller.setPreviousController(this); // Pass a reference to this controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load ModifPlat.fxml: " + e.getMessage());
        }
    }
}
