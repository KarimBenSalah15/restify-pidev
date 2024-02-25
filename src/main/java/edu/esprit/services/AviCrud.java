package edu.esprit.services;

import edu.esprit.entities.Avi;
import edu.esprit.entities.Reclamation;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AviCrud implements ICrud<Avi>{
    Connection cnx2;
    @Override
    public void modifierEntite(Avi p, int id) {
        String requet = "UPDATE avis SET nbretoile=?, date=?, message=?  WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setInt(1, p.getNbretoile());
            pst.setDate(2, p.getDate());
            pst.setString(3, p.getMessage());
            pst.setInt(4, id);

            pst.executeUpdate();
            System.out.println("avi modifiée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }

    public AviCrud() {
        cnx2= MyConnection.getInstance().getCnx();
    }

    @Override
    public List<Avi> afficherEntiite() {
        List<Avi> avis=new ArrayList<>();
        String req3="SELECT * FROM avi";

        Statement stm = null;
        try {
            stm = cnx2.createStatement();
            ResultSet rs= stm.executeQuery(req3);
            while (rs.next())
            {
                Avi p=new Avi();
                p.setId(rs.getInt(1));
                p.setNbretoile(rs.getInt(2));
                p.setDate(rs.getDate(3));
                p.setMessage(rs.getString(4));

                avis.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return avis;
    }

    @Override
    public void supprimerEntite(int id) {
        String requet = "DELETE FROM avis WHERE id = ?";
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
    public void ajouterEntite(Avi p) {
        String requet="INSERT INTO avis (nbretoile,date,message) VALUES(?,?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1,p.getNbretoile());
            pst.setDate(2,p.getDate());
            pst.setString(3,p.getMessage());

            pst.executeUpdate();
            System.out.println("Ajoute");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
