package edu.esprit.services;

import edu.esprit.entities.Personne;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneCrud implements ICrud<Personne>{
    Connection cnx2;
    public PersonneCrud(){
        cnx2 = MyConnection.getInstance().getCnx();
    }
    public void ajouterEntite(Personne p){ //obligé d'utiliser cette méthode pour les requêtes de type modification de table)
        String req1="INSERT INTO Personne(nom,prenom) VALUES" +
                " ('"+p.getNom()+"','"+p.getPrenom()+"')";
        try {
            Statement st = cnx2.createStatement();
            st.executeUpdate(req1);
            System.out.println("Personne ajoutée.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public List<Personne> afficherEntite(){
        List<Personne> list = new ArrayList<>();
        String req3 = "SELECT * from Personne";
        try {
            Statement st1 = cnx2.createStatement();
            ResultSet rs = st1.executeQuery(req3);
            while (rs.next()){
                Personne p = new Personne();
                p.setid(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString(3));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }
    public void ajouterEntite2(Personne p){
        String req2 = "INSERT INTO Personne (nom, prenom) VALUES (?,?)";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req2);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getPrenom());
            pst.executeUpdate();
            System.out.println("Personne ajoutée.");;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
