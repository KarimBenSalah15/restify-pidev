module PiDev {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    opens controllers;
    opens entities;
    exports controllers;
    exports entities;
    /*requires recaptcha4j;
    requires javafx.web;
    requires jdk.jsobject;*/
    requires com.google.gson;
    requires java.desktop;
    requires javafx.swing;
    requires core;
    requires twilio;
    requires webcam.capture;
    requires java.datatransfer;
    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires org.apache.pdfbox;
    requires javafx.media;
}