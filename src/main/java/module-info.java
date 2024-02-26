module PIDEV {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    opens edu.esprit.controllers;
    opens edu.esprit.entities;
    requires com.jfoenix;
}