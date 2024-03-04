module sample.projectfinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires  java.sql;
    requires twilio;

    opens sample.Evenement to javafx.fxml;
    exports sample.Evenement;
    exports sample.Evenement.Controllers;
    opens sample.Evenement.Controllers to javafx.fxml;
    exports sample.Evenement.Entities;
    opens sample.Evenement.Entities to javafx.fxml;
}