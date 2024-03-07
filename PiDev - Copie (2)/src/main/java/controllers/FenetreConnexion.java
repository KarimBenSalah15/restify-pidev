package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import com.github.sarxos.webcam.Webcam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.MyConnection;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class FenetreConnexion {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_con;

    @FXML
    private Button btn_insc2;

    @FXML
    private Hyperlink mdpoublie;

    @FXML
    private TextField tf_login_con;

    @FXML
    private TextField tf_mdp_con;
    Connection cnx2;
    Encryptor encryptor = new Encryptor();

    private int tentative = 0;

    public FenetreConnexion() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    @FXML
    void connexion(ActionEvent event) {
        String login = tf_login_con.getText().trim();
        String mdp = tf_mdp_con.getText().trim();

        // Vérification si les champs sont vides
        if (login.isEmpty() || mdp.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        String req = null;
        try {
            req = "SELECT * from Utilisateur where login = '"+ tf_login_con.getText() +"' and mdp = '"+ encryptor.encryptString(tf_mdp_con.getText()) +"'";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            Statement st1 = cnx2.createStatement();
            ResultSet rs = st1.executeQuery(req);
            try {
                if (rs.next()) {
                    String role = rs.getString("role").toUpperCase();
                    if ("CLIENT".equals(role)){
                        String req2 = "SELECT id from Utilisateur where login = '"+ tf_login_con.getText() +"' and mdp = '"+ encryptor.encryptString(tf_mdp_con.getText()) +"'";
                        Statement st2 = cnx2.createStatement();
                        ResultSet rs2 = st2.executeQuery(req2);
                        if (rs2.next()) {
                            int idenvoi = rs2.getInt(1);
                            MyConnection.getInstance().setIdenvoi(idenvoi);
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreDashboardClient.fxml"));
                            try {
                                Parent root = loader.load();
                                Scene scene = new Scene(root, 1300, 750);
                                Stage stage = (Stage) tf_login_con.getScene().getWindow();
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.setX(0);
                                stage.setY(0);
                                stage.show();
                            } catch (IOException e) {
                                System.err.println(e.getMessage());
                            }
                        }
                    } else if ("EMPLOYE".equals(role)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NavbarEmp.fxml"));
                        try {
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 1300, 750);
                            Stage stage = (Stage) tf_login_con.getScene().getWindow();
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.setX(0);
                            stage.setY(0);
                            stage.show();
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    } else if ("ADMIN".equals(role)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreDashboardAdmin.fxml"));
                        try {
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 1400, 750);
                            Stage stage = (Stage) tf_login_con.getScene().getWindow();
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.setX(0);
                            stage.setY(0);
                            stage.show();
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
                else {
                    tentative++;
                    showAlert("Connexion", "Aucun utilisateur avec ces identifiants n'existe.");
                    if (tentative >= 3) {
                        captureWebcamImage();
                        sendEmailWithAttachment();
                    }
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    void emmeneinscription(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreInscription.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) tf_login_con.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void mdpenvoi(ActionEvent event) {
        String login = tf_login_con.getText().trim();
        if (login.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer votre login.");
            return;
        }
        String email = getEmail(login);
        if (email == null || email.isEmpty()) {
            showAlert("Erreur", "Aucun utilisateur avec ce login trouvé.");
            return;
        }
        String nouveauMdp = generermdp();
        if (miseajourmdp(email, nouveauMdp)) {
            envoyerNouveauMdpParEmail(email, nouveauMdp);
            showAlert("Succès", "Un nouveau mot de passe a été envoyé à votre adresse e-mail.");
        } else {
            showAlert("Erreur", "Erreur lors de la réinitialisation du mot de passe.");
        }
    }

    private String getEmail(String login) {
        String req = "SELECT email FROM Utilisateur WHERE login = ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, login);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getprenom(String email) {
        String req = "SELECT prenom FROM Utilisateur WHERE email = ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("prenom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String generermdp() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}|;:',<.>/?";
        StringBuilder newPassword = new StringBuilder();
        int length = 10;
        for (int i = 0; i < length; i++) {
            newPassword.append(characters.charAt(random.nextInt(characters.length())));
        }
        return newPassword.toString();
    }

    private boolean miseajourmdp(String email, String nouveauMdp) {
        String reqUpdate = "UPDATE Utilisateur SET mdp = ? WHERE email = ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(reqUpdate);
            pst.setString(1, encryptor.encryptString(nouveauMdp));
            pst.setString(2, email);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void envoyerNouveauMdpParEmail(String email, String nouveauMdp) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        try {
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("restify.help@gmail.com", "fcihveizkyzscxbg");
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("restify.help@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Réinitialisation de votre mot de passe");
            message.setText("Bonjour "+getprenom(email)+", votre nouveau mot de passe est : " + nouveauMdp);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmailWithAttachment() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("restify.help@gmail.com", "fcihveizkyzscxbg");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("restify.help@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("karim.bensalah@esprit.tn")
            );
            message.setSubject("Capture d'image, tentative échouée.");
            String file = "CaptureConnexion.jpg";
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart.setText("Bonjour,\n\nVeuillez trouver la capture d'image en pièce jointe d'une personne qui s'est trompé au moins 3 fois, c'est peut être un usurpateur d'identité.");
            MimeBodyPart attachPart = new MimeBodyPart();
            try {
                attachPart.attachFile(new File(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);
            System.out.println("Envoi du message...");
            Transport.send(message);
            System.out.println("Message envoyé avec succès.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void captureWebcamImage() {
        try {
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            ImageIO.write(webcam.getImage(), "JPG", new File("CaptureConnexion.jpg"));
            webcam.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
    }

}
