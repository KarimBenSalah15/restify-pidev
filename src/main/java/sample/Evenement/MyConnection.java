package sample.Evenement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyConnection {
    public String url = "jdbc:mysql://localhost:3306/workshop";
    public String login = "root";
    public String pwd = "";
    public Connection cnx = null;
    public static MyConnection instance;

    public MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }

    // Méthode pour récupérer les données des événements et des participants avec une jointure


}
