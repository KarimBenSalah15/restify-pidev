package edu.esprit.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import edu.esprit.entities.Reservation;
import edu.esprit.services.ReservationCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;

import java.awt.Desktop;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

public class AdminDetails {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc;

    @FXML
    private AnchorPane anco;

    @FXML
    private AnchorPane ancoright;

    @FXML
    private JFXButton btnplus;

    @FXML
    private JFXButton btnprint;

    @FXML
    private TableColumn<Reservation, Date> dateid1;

    @FXML
    private TableColumn<Reservation, String> heureid1;

    @FXML
    private TableColumn<Reservation, Integer> id;

    @FXML
    private Label label_top;

    @FXML
    private TableColumn<Reservation, Integer> nbpersonneid1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane_top;

    @FXML
    private TableColumn<Reservation, Integer> tabId;

    @FXML
    private TableView<Reservation> viewid1;



    @FXML
    void initialize() {
        ReservationCrud rc = new ReservationCrud();
        List<Reservation> reservations = rc.afficherEntiite();
        dateid1.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureid1.setCellValueFactory(new PropertyValueFactory<>("heure"));
        nbpersonneid1.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabId.setCellValueFactory(new PropertyValueFactory<>("tabId")); // Set cellValueFactory for tabId
        viewid1.getItems().addAll(reservations);

    }
    @FXML
    void returnD(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/tab.fxml")));
        try {
            Parent root = loader.load();
            btnplus.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void exportToPDF(ActionEvent event) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20f;
            float cellMargin = 30f;
            // Set border color
            contentStream.setStrokingColor(255, 0, 0); // Red color
            // Draw border
            float margins = 40;
            contentStream.addRect(margins, margins, tableWidth, yStart - margins);
            contentStream.stroke();
            // Title
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("RÉSERVATION");
            contentStream.endText();
            yPosition -= 10;
            // Content
            yPosition -= rowHeight; // Move down for the content
            // Draw table content
            for (Reservation reservation : viewid1.getSelectionModel().getSelectedItems()) {
                yPosition -= rowHeight;

                drawRow(contentStream, margin, yPosition, "DATE", reservation.getDate().toString());
                yPosition -= cellMargin;

                drawRow(contentStream, margin, yPosition, "HEURE", reservation.getHeure());
                yPosition -= cellMargin;

                drawRow(contentStream, margin, yPosition, "NBPERSONNE", String.valueOf(reservation.getNbrpersonne()));
                yPosition -= cellMargin;

                drawRow(contentStream, margin, yPosition, "TABLE_ID", String.valueOf(reservation.getTabId()));
                yPosition -= cellMargin * 2; // Additional space between rows
            }
            contentStream.close();
            // Save the document
            File file = new File("Reservations.pdf");
            document.save(file);
            document.close();
            // Open the PDF file
            Desktop.getDesktop().open(file);

            System.out.println("PDF exporté avec succès et ouvert.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void drawRow(PDPageContentStream contentStream, float x, float y, String label, String value) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(label + ": " + value);
        contentStream.endText();
    }

}
