module ProjetPiJava {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires mail;
    requires javax.mail.api;
    opens edu.esprit.Controllers;
    opens edu.esprit.entities;
}