module ProjetPiJava {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;

    requires javax.mail.api;
    requires org.controlsfx.controls;
    requires twilio;


    opens edu.esprit.Controllers;
    opens edu.esprit.entities;
}