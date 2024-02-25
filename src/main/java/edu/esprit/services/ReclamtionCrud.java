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
        String requet="INSERT INTO reclamation (date,etat,type,message) VALUES(?,?,?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setDate(1,p.getDate());
            pst.setBoolean(2,p.getEtat());
            pst.setString(3,p.getType());
            pst.setString(4,p.getMessage());
            pst.executeUpdate();
            System.out.println("Ajoute");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
