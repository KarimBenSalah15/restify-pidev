package edu.esprit.services;

import edu.esprit.entities.Avi;
import edu.esprit.entities.Signalement;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SignalementCrud implements ICrud<Signalement>{
    Connection cnx2;

    public SignalementCrud(Connection cnx2) {
        cnx2= MyConnection.getInstance().getCnx();
    }

    @Override
    public void modifierEntite(Signalement p, int id) {
        String requet = "UPDATE signalement SET message=?, date=? WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setString(1, p.getMessage());
            pst.setDate(2, p.getDate());

            pst.setInt(3, id);

            pst.executeUpdate();
            System.out.println("signalement modifiée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }

    @Override
    public List<Signalement> afficherEntiite() {
        List<Signalement> Signalements=new ArrayList<>();
        String req3="SELECT * FROM signalement";

        Statement stm = null;
        try {
            stm = cnx2.createStatement();
            ResultSet rs= stm.executeQuery(req3);
            while (rs.next())
            {
                Signalement p=new Signalement();
                p.setId(rs.getInt(1));
                p.setMessage(rs.getString(2));
                p.setDate(rs.getDate(3));


                Signalements.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Signalements;
    }

    @Override
    public void supprimerEntite(int id) {
        String requet = "DELETE FROM signalement WHERE id = ?";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("supprimer avi");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }

    @Override
    public void ajouterEntite(Signalement p) {
        String requet="INSERT INTO signalement (message,date) VALUES(?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setString(1,p.getMessage());
            pst.setDate(2,p.getDate());


            pst.executeUpdate();
            System.out.println("Ajoute");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
