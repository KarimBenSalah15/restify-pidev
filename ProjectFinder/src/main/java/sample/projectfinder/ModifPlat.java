package sample.projectfinder;

import sample.projectfinder.Plat;
import sample.projectfinder.PlatCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifPlat {

    @FXML
    private TextField tfcal;

    @FXML
    private TextField tfingr;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprix;

    private PlatAd previousController; // Reference to the previous controller
    private Plat selectedPlat;

    public void setPreviousController(PlatAd previousController) {
        this.previousController = previousController;
    }

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

            // Refresh the table in the previous controller (PlatAd)
            if (previousController != null) {
                previousController.refreshTable();
            }

            // Close the ModifPlat page
            Stage stage = (Stage) tfnom.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Afficher une alerte si les valeurs saisies pour les calories ou le prix ne sont pas valides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nombre valide pour les calories et le prix.");
            alert.showAndWait();
        }
    }

}
