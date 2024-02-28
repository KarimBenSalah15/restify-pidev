package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.PlatCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ModifPlat {

    @FXML
    private Button btn1;

    @FXML
    private Button btn;

    @FXML
    private TextField tfcal;

    @FXML
    private TextField tfingr;

    @FXML
    private TextField tfnom;
    private Plat selectedPlat;

    public TextField getTfcal() {
        return tfcal;
    }

    public void setTfcal(TextField tfcal) {
        this.tfcal = tfcal;
    }

    public TextField getTfingr() {
        return tfingr;
    }

    public void setTfingr(TextField tfingr) {
        this.tfingr = tfingr;
    }

    public TextField getTfnom() {
        return tfnom;
    }

    public void setTfnom(TextField tfnom) {
        this.tfnom = tfnom;
    }

    public TextField getTfprix() {
        return tfprix;
    }

    public void setTfprix(TextField tfprix) {
        this.tfprix = tfprix;
    }

    @FXML
    private TextField tfprix;


    public void initData(Plat plat) {
        // Initialize the input fields with the details of the selected Plat object
        selectedPlat = plat;
        tfnom.setText(plat.getNom());
        tfprix.setText(String.valueOf(plat.getPrix()));
        tfingr.setText(plat.getIngredients());
        tfcal.setText(String.valueOf(plat.getCalories()));
    }

    @FXML
    public void modifierButtonClicked() {
        // Vérifier si les champs de saisie sont vides
        if (tfnom.getText().isEmpty() || tfprix.getText().isEmpty() || tfingr.getText().isEmpty() || tfcal.getText().isEmpty()) {
            // Afficher une alerte pour informer l'utilisateur que tous les champs doivent être remplis
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Sortir de la méthode si les champs sont vides
        }

        try {
            // Vérifier si les valeurs saisies pour les calories et le prix sont valides
            int calories = Integer.parseInt(tfcal.getText());
            float prix = Float.parseFloat(tfprix.getText());

            // Get the modified details from the input fields
            String nom = tfnom.getText();
            String ingredients = tfingr.getText();

            // Update the selected Plat object with the modified details
            selectedPlat.setNom(nom);
            selectedPlat.setPrix(prix);
            selectedPlat.setIngredients(ingredients);
            selectedPlat.setCalories(calories);

            // Call the method to update the Plat object in the database
            PlatCrud platCrud = new PlatCrud();
            platCrud.modifierEntite(selectedPlat, selectedPlat.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success,Plat modifié", new ButtonType("ok"));
            alert.show();

            // Close the ModifPlat page (optional)
            // You may want to close the ModifPlat page after modifying the Plat object
            // You can access the stage and close it if needed
        } catch (NumberFormatException e) {
            // Afficher une alerte si les valeurs saisies pour les calories ou le prix ne sont pas valides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nombre valide pour les calories et le prix.");
            alert.showAndWait();
        }
    }

    @FXML
    private void retur(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlatAd.fxml"));
        try {

            Parent root = loader.load();


            tfnom.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load  " + e.getMessage());
        }
    }


}
