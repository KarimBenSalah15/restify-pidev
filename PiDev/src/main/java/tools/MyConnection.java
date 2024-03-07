package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public String url="jdbc:mysql://localhost:3306/restify3";
    public String login="root";
    public String pwd="";
    Connection cnx;
    public int idenvoi;
    public static MyConnection instance;

    public int getIdenvoi() {
        return idenvoi;
    }

    public void setIdenvoi(int idenvoi) {
        this.idenvoi = idenvoi;
    }

    private MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public Connection getCnx() {
        return cnx;
    }
    public static MyConnection getInstance(){
        if(instance == null)
            instance = new MyConnection();
        return instance;
    }
}