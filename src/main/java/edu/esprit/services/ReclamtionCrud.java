package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamtionCrud implements ICrud<Reclamation> {
    Connection cnx2;
    @Override
    public void modifierEntite(Reclamation p ,int id) {
        String requet = "UPDATE reclamation SET date=?, etat=?, type=?,message=?,id=?  WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setDate(1, p.getDate());
            pst.setBoolean(2, p.getEtat());
            pst.setString(3, p.getType());
            pst.setString(4, p.getMessage());
            pst.setInt(5, id);
            pst.setInt(6, id);
            pst.executeUpdate();
            System.out.println("Reclamtion modifiée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
        }

    public ReclamtionCrud() {
        cnx2= MyConnection.getInstance().getCnx();
    }

    @Override
    public List<Reclamation> afficherEntiite() {
        List<Reclamation> reclamations=new ArrayList<>();
        String req3="SELECT * FROM reclamation";

        Statement stm = null;
        try {
            stm = cnx2.createStatement();
            ResultSet rs= stm.executeQuery(req3);
            while (rs.next())
            {
                Reclamation p=new Reclamation();
                p.setId(rs.getInt(1));
                p.setDate(rs.getDate(2));
                p.setEtat(rs.getBoolean(3));
                p.setType(rs.getString(4));
                p.setMessage(rs.getString(5));
                reclamations.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reclamations;
    }

    @Override
    public void supprimerEntite(int id) {
        String requet = "DELETE FROM reclamation WHERE id = ?";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("supprimer reclamtion");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }
    @Override
    public void ajouterEntite(Reclamation p) {
        String requet="INSERT INTO reclamation (date,etat,type,message,idRO) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setDate(1,p.getDate());
            pst.setBoolean(2,p.getEtat());
            pst.setString(3,p.getType());
            pst.setString(4,p.getMessage());
            pst.setInt(5,p.getIdRO());
            pst.executeUpdate();
            System.out.println("Ajoute");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void updateEtat(int ids) {
        String req3 = "UPDATE reclamation\n" +
                "SET etat = TRUE\n" +
                "WHERE id = ?";

        try (PreparedStatement pst = cnx2.prepareStatement(req3)) {
            // Assuming ids is a string that represents an integer value


            pst.setInt(1,ids);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Reclamation modifiée avec succès");
            } else {
                System.out.println("Aucune réclamation modifiée. L'ID n'existe peut-être pas.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erreur de conversion d'ID en entier : " + e.getMessage());
        }
    }


}
