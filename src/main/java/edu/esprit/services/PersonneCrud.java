package edu.esprit.services;

import edu.esprit.entities.Personne;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneCrud implements  ICrud<Personne> {
    Connection cnx2;

    public PersonneCrud() {
        cnx2=MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouterEntite(Personne p) {
        String req1 = "INSERT INTO personne (nom,prenom) VALUES" + "('" + p.getNom() + "','" + p.getPrenom() + "')";
        try {
            Statement st = cnx2.createStatement();

            //  Statement st = new MyConnection().getCnx().createStatement();
            st.executeUpdate(req1);
            System.out.println("persone ajoute");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierEntite(Personne p, int id) {

    }

    @Override
    public List<Personne> afficherEntiite() {
        List<Personne> personnes=new ArrayList<>();
        String req3="SELECT * FROM personne";

            Statement stm = null;
            try {
                stm = cnx2.createStatement();
                ResultSet rs= stm.executeQuery(req3);
                while (rs.next())
                {
                    Personne p=new Personne();
                    p.setId(rs.getInt(1));
                    p.setNom(rs.getString("nom"));
                    p.setPrenom(rs.getString(3));
                    personnes.add(p);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        return personnes;
    }

    @Override
    public void supprimerEntite(int id) {

    }

    public void ajouterEntite2(Personne p) {
        String requet="INSERT INTO personne (nom,prenom) VALUES(?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setString(1,p.getNom());
            pst.setString(2,p.getPrenom());
            pst.executeUpdate();
            System.out.println("Ajoute");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
