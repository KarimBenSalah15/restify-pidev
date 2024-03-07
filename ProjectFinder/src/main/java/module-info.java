module sample.projectfinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires  java.sql;
    requires twilio;
    requires poi;
    requires poi.ooxml;


    requires java.desktop;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires javafx.swing;
    requires java.sql.rowset; // Ajoutez cette ligne pour inclure la bibliothèque AWT



    opens sample.projectfinder to javafx.fxml;
    exports sample.projectfinder;




}