module ProjetPiJava {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;

    requires javax.mail.api;
    requires google.cloud.translate;
    requires google.cloud.core;
    requires google.cloud.core.http;
    opens edu.esprit.Controllers;
    opens edu.esprit.entities;
}