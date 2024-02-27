module sample.projectfinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires  java.sql;
    requires twilio;

    opens sample.Evenement to javafx.fxml;
    exports sample.Evenement;
}