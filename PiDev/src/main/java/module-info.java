module PiDev {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens controllers;
    opens entities;
    exports controllers;
}