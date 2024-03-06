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
            float cellMargin = 10f;

            // Draw border with shadow
            contentStream.setLineWidth(1f);
            contentStream.setLineDashPattern(new float[]{5, 5}, 0);
            contentStream.setStrokingColor(0, 0, 0); // Black color
            contentStream.moveTo(margin, yStart);
            contentStream.lineTo(margin + tableWidth, yStart);
            contentStream.moveTo(margin + tableWidth, yStart);
            contentStream.lineTo(margin + tableWidth, margin);
            contentStream.moveTo(margin + tableWidth, margin);
            contentStream.lineTo(margin, margin);
            contentStream.moveTo(margin, margin);
            contentStream.lineTo(margin, yStart);
            contentStream.closePath();
            contentStream.stroke();

            // Title
            float titleHeight = 30f;
            float titleWidth = tableWidth;
            float titleX = margin;
            float titleY = yStart + 15;

            contentStream.setNonStrokingColor(100, 149, 237); // CornflowerBlue color
            contentStream.addRect(titleX, titleY, titleWidth, titleHeight);
            contentStream.fill();

            contentStream.setNonStrokingColor(255, 255, 255); // White color for text
            contentStream.beginText();
            contentStream.newLineAtOffset(titleX + 10, titleY + titleHeight - 15);
            contentStream.showText("RÉSERVATION");
            contentStream.endText();

            yPosition -= titleHeight + cellMargin;

            // Content
            List<Reservation> selectedReservations = viewid1.getSelectionModel().getSelectedItems();
            if (selectedReservations.isEmpty()) {
                // Aucune ligne sélectionnée, afficher toutes les lignes
                selectedReservations = viewid1.getItems();
            }

            for (Reservation selectedReservation : selectedReservations) {
                yPosition -= rowHeight;

                drawRow(contentStream, margin, yPosition, "DATE", selectedReservation.getDate().toString());
                yPosition -= rowHeight;

                drawRow(contentStream, margin, yPosition, "HEURE", selectedReservation.getHeure());
                yPosition -= rowHeight;

                drawRow(contentStream, margin, yPosition, "NBPERSONNE", String.valueOf(selectedReservation.getNbrpersonne()));
                yPosition -= rowHeight;

                drawRow(contentStream, margin, yPosition, "TABLE_ID", String.valueOf(selectedReservation.getTabId()));
                yPosition -= cellMargin;
            }

            // Draw content border
            contentStream.setLineWidth(1f);
            contentStream.setStrokingColor(0, 0, 0); // Black color
            contentStream.moveTo(margin, yPosition);
            contentStream.lineTo(margin + tableWidth, yPosition);
            contentStream.moveTo(margin + tableWidth, yPosition);
            contentStream.lineTo(margin + tableWidth, yPosition + titleHeight + selectedReservations.size() * 3 * rowHeight);
            contentStream.moveTo(margin + tableWidth, yPosition + titleHeight + selectedReservations.size() * 3 * rowHeight);
            contentStream.lineTo(margin, yPosition + titleHeight + selectedReservations.size() * 3 * rowHeight);
            contentStream.moveTo(margin, yPosition + titleHeight + selectedReservations.size() * 3 * rowHeight);
            contentStream.lineTo(margin, yPosition);
            contentStream.closePath();
            contentStream.stroke();

            contentStream.close();

            // Save the document
            File file = new File("Reservation.pdf");
            document.save(file);
            document.close();

            // Open the PDF file
            Desktop.getDesktop().open(file);

            System.out.println("PDF exporté avec succès et ouvert.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void drawRow(PDPageContentStream contentStream, float x, float y, String... values) throws IOException {
        contentStream.setNonStrokingColor(0, 0, 0); // Black color for text
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);

        for (String value : values) {
            contentStream.showText(value + "  ");
        }

        contentStream.endText();
    }

    public void updateR(ActionEvent actionEvent) {
    }
}
