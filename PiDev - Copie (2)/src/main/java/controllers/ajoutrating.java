package controllers;

import entities.Reclamation;
import org.controlsfx.control.Rating;
import tools.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ajoutrating {
    Connection cnx2;

    public ajoutrating() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    public void ajoutetoile(Rating r){
            String requet="INSERT INTO rating (id_event, etoiles, id_user) VALUES(?,?,?)";
            try {
                PreparedStatement pst =cnx2.prepareStatement(requet);
                pst.setInt(1, 1);
                pst.setDouble(2,r.getRating());
                pst.setDouble(3,MyConnection.getInstance().getIdenvoi());
                pst.executeUpdate();
                System.out.println("Ajoute");
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
    }
    public void ajoutetoile2(Rating r){
        String requet="INSERT INTO rating (id_event, etoiles, id_user) VALUES(?,?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1, 2);
            pst.setDouble(2,r.getRating());
            pst.setDouble(3,MyConnection.getInstance().getIdenvoi());
            pst.executeUpdate();
            System.out.println("Ajoute");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void ajoutetoile3(Rating r){
        String requet="INSERT INTO rating (id_event, etoiles, id_user) VALUES(?,?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1, 3);
            pst.setDouble(2,r.getRating());
            pst.setDouble(3,MyConnection.getInstance().getIdenvoi());
            pst.executeUpdate();
            System.out.println("Ajoute");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
