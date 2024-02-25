package edu.esprit.services;

import edu.esprit.entities.Plat;
import edu.esprit.entities.User;
import edu.esprit.tools.MyConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class userCrus implements ICrud<User> {
    Connection cnx2;
    public userCrus() {
        cnx2= MyConnection.getInstance().getCnx();
    }

    @Override
    public void modifierEntite(User p, int id) {

    }

    @Override
    public List<User> afficherEntiite() {
        List<User> users=new ArrayList<>();
        String req3="SELECT * FROM utilisateur";

        Statement stm = null;
        try {
            stm = cnx2.createStatement();
            ResultSet rs= stm.executeQuery(req3);
            while (rs.next())
            {
                User P=new User();
                P.setId(rs.getInt(1));
                P.setNom(rs.getString(2));

                users.add(P);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    @Override
    public void supprimerEntite(int id) {

    }

    @Override
    public void ajouterEntite(User p) {

    }
}
