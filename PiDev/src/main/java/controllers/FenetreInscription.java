package controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

/*import javafx.concurrent.Worker;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tools.MyConnection;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FenetreInscription {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_insc;

    @FXML
    private AnchorPane ap_1;

    @FXML
    private AnchorPane ap_2;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_login;

    @FXML
    private TextField tf_mdp;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_prenom;

    @FXML
    private TextField tf_tel;

    @FXML
    private TextField tf_verif;

    @FXML
    private Button btn_verif;

    @FXML
    private TextField hide;

/*
    @FXML
    private WebView webViewRecaptcha;

    private String reCaptchaResponse;*/

    Connection cnx2;
    Encryptor encryptor = new Encryptor();

    public FenetreInscription() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public void Random() {
        Random rd = new Random();
        hide.setText(""+rd.nextInt(10000+1));
    }
    public void sendCode(){
        Properties props=new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port",465);
        props.put("mail.smtp.user","mkcomputeredu03@gmail.com");
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.debug",true);
        props.put("mail.smtp.socketFactory.port",465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback",false);

        try {
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setText("Votre code de vérification est " + hide.getText());
            message.setSubject("Code de vérification de votre compte Restify");
            message.setFrom(new InternetAddress("restify.help@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(tf_email.getText().trim()));
            message.saveChanges();
            try
            {
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com","restify.help@gmail.com","fcihveizkyzscxbg");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                showAlert("Vérification","Un code de vérification vous a été envoyé à l'adresse "+ tf_email.getText());
            }catch(Exception e)
            {
                showAlert("Vérification","Une erreur s'est produite");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void inscription(ActionEvent event) {
/*        if (reCaptchaResponse == null || reCaptchaResponse.isEmpty()) {
            showAlert("Erreur", "Veuillez cocher le reCAPTCHA.");
            return;
        }*/
        String nom = tf_nom.getText().trim();
        String prenom = tf_prenom.getText().trim();
        String emailcheck = tf_email.getText().trim();
        String tel = tf_tel.getText().trim();
        String login = tf_login.getText().trim();
        String mdp = tf_mdp.getText().trim();

        if (login.isEmpty() || mdp.isEmpty() || nom.isEmpty() || prenom.isEmpty() || emailcheck.isEmpty() || tel.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        if (!complexitemdp(mdp)) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.");
            return;
        }
        String emailExiste = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
        String loginExiste = "SELECT COUNT(*) FROM Utilisateur WHERE login = ?";
        String telExiste = "SELECT COUNT(*) FROM Utilisateur WHERE tel = ?";

        try {
            PreparedStatement emailPst = cnx2.prepareStatement(emailExiste);
            emailPst.setString(1, tf_email.getText());
            ResultSet emailRs = emailPst.executeQuery();
            emailRs.next();
            int nbEmails = emailRs.getInt(1);

            PreparedStatement loginPst = cnx2.prepareStatement(loginExiste);
            loginPst.setString(1, tf_login.getText());
            ResultSet loginRs = loginPst.executeQuery();
            loginRs.next();
            int nbLogins = loginRs.getInt(1);

            PreparedStatement telPst = cnx2.prepareStatement(telExiste);
            telPst.setInt(1, Integer.parseInt(tf_tel.getText()));
            ResultSet telRs = telPst.executeQuery();
            telRs.next();
            int nbTels = telRs.getInt(1);

            if (nbEmails > 0) {
                showAlert("Erreur", "Cet email est déjà utilisé.");
            } else if (nbLogins > 0) {
                showAlert("Erreur", "Ce login est déjà utilisé.");

            }
            else if (nbTels > 0) {
                showAlert("Erreur", "Ce téléphone est déjà utilisé.");

            }else {
                String req1 = "INSERT INTO Utilisateur (nom, prenom, email, tel, login, mdp) " +
                        "VALUES (?,?,?,?,?,?)";
                try {
                    PreparedStatement pst = cnx2.prepareStatement(req1);
                    pst.setString(1, tf_nom.getText());
                    pst.setString(2, tf_prenom.getText());
                    String email = tf_email.getText();
                    if (!isValidEmail(email)) {
                        showAlert("Erreur", "Veuillez entrer une adresse email valide.");
                        return;
                    }
                    pst.setString(3, tf_email.getText());
                    pst.setInt(4, Integer.parseInt(tf_tel.getText()));
                    pst.setString(5, tf_login.getText());
                    pst.setString(6, encryptor.encryptString(tf_mdp.getText()));
                    pst.executeUpdate();
                    Random();
                    sendCode();
                    ap_1.setVisible(false);
                    ap_2.setVisible(true);
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void verifcode(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        if (tf_verif.getText().equals(hide.getText())){
            showAlert("Succès", "Vous vous êtes bien inscrit.");
            String req2 = "SELECT id from Utilisateur where login = '"+ tf_login.getText() +"' and mdp = '"+ encryptor.encryptString(tf_mdp.getText()) +"'";
            Statement st2 = cnx2.createStatement();
            ResultSet rs2 = st2.executeQuery(req2);
            if (rs2.next()) {
                int idenvoi = rs2.getInt(1);
                MyConnection.getInstance().setIdenvoi(idenvoi);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreDashboardClient.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 1315, 890);
                    Stage stage = (Stage) tf_nom.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setFullScreen(true);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
            else {
                System.err.println("ERREUR.");
            }
        }
        else {
            showAlert("Vérification", "Le code écrit n'est pas correct");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    @FXML
    void emmeneconnexion(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FenetreConnexion.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) tf_login.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean complexitemdp(String motDePasse) {
        if (motDePasse.length() < 8) {
            return false;
        }
        if (!motDePasse.matches(".*[A-Z].*")) {
            return false;
        }
        if (!motDePasse.matches(".*[a-z].*")) {
            return false;
        }
        if (!motDePasse.matches(".*\\d.*")) {
            return false;
        }
        if (!motDePasse.matches(".*[^a-zA-Z0-9].*")) {
            return false;
        }
        return true;
    }

    @FXML
    void initialize() {
  /*      WebEngine webEngine = webViewRecaptcha.getEngine();
        webEngine.loadContent("<html><body><div class='g-recaptcha' data-sitekey='6Lfy28YaAAAAAAe2jFKOUqSKJQwalQZ6Hzxwy3Zv11' data-callback='handleRecaptcha'></div><script src='https://www.google.com/recaptcha/api.js'></script><script>function handleRecaptcha(response) { window.javaConnector.setRecaptchaResponse(response); }</script></body></html>");

        // Écouteur de changement pour récupérer la réponse reCAPTCHA
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", new JavaConnector());
            }
        });*/
    }
  /*  public class JavaConnector {
        public void setRecaptchaResponse(String response) {
            reCaptchaResponse = response;
            System.out.println("reCAPTCHA Response: " + response);
        }
    }*/

}
