package edu.esprit.services;

import edu.esprit.entities.Plat;
import edu.esprit.entities.Reclamation;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatCrud implements  ICrud<Plat> {
    Connection cnx2;
    public PlatCrud() {
        cnx2= MyConnection.getInstance().getCnx();
    }

    @Override
    public void modifierEntite(Plat p, int id) {
        String requet = "UPDATE plat SET nom=? WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);


            pst.setString(1, p.getNom());

            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("plat modifiée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }

    @Override
    public List<Plat> afficherEntiite() {
        List<Plat> plats=new ArrayList<>();
        String req3="SELECT * FROM plat";

        Statement stm = null;
        try {
            stm = cnx2.createStatement();
            ResultSet rs= stm.executeQuery(req3);
            while (rs.next())
            {
                Plat P=new Plat();
                P.setId(rs.getInt(1));
                P.setNom(rs.getString(2));

                plats.add(P);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return plats;
    }

    @Override
    public void supprimerEntite(int id) {

    }

    @Override
    public void ajouterEntite(Plat p) {

    }
}
