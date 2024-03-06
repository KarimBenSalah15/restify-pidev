package edu.esprit.email;

import java.time.LocalDate;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.esprit.entities.Reservation;

public class SendEmail {

    public static void send(String toEmail, Reservation selectedReservation) {
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("restify.help@gmail.com", "fcihveizkyzscxbg"); // Change this to your sender email password
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("restify.help@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Réservation RESTIFY");

            // Get the reservation time from the Reservation object
            LocalDate localDate = selectedReservation.getDate().toLocalDate();
            String reservationTime = localDate + " à " + selectedReservation.getHeure();

            // Include the reservation time in the email message
            message.setText("Votre réservation est confirmée pour le " + reservationTime);

            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
