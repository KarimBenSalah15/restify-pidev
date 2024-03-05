package edu.esprit.services;

import edu.esprit.entities.Plat;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatCrud  implements ICrud<Plat> {

    Connection cnx2;
    public PlatCrud(){

        cnx2= MyConnection.getInstance().getCnx();
    }

    public void ajouterEntite(Plat p){
        String requet = "INSERT INTO plat(nom,prix,ingredients,calories, image) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setString(1, p.getNom());
            pst.setFloat(2, p.getPrix());
            pst.setString(3,p.getIngredients());
            pst.setInt(4,p.getCalories());
            pst.setBlob(5, p.getImage()); // Set the image data

            pst.executeUpdate();
            System.out.println("plat ajoué");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public List<Plat> afficherEntite() {
        List<Plat> plats = new ArrayList<>();
        String req3 = "SELECT * FROM plat";
        try {
            Statement stn = cnx2.createStatement();
            ResultSet rs = stn.executeQuery(req3);
            while (rs.next()){
                Plat p = new Plat();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrix(rs.getFloat(3));
                p.setIngredients(rs.getString(4));
                p.setCalories(rs.getInt(5));
                p.setImage(rs.getBlob(6));
                plats.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return plats;
    }
    public boolean platExists(String nom) {
        String query = "SELECT * FROM plat WHERE nom = ?";
        try (PreparedStatement statement = cnx2.prepareStatement(query)) {
            statement.setString(1, nom);
            try (ResultSet resultSet = statement.executeQuery()) {
                // Si le résultat contient au moins une ligne, cela signifie que le plat existe déjà
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En cas d'erreur, retourner false par défaut
        }
    }
    @Override
    public void supprimerEntite(int id) {
        String requet = "DELETE FROM plat WHERE id = ?";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("supprimer Plat");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }
    @Override
    public void modifierEntite(Plat p ,int id) {
        String requet = "UPDATE plat SET nom=?, prix=?, ingredients=? , calories=? WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setString(1, p.getNom());
            pst.setFloat(2, p.getPrix());
            pst.setString(3, p.getIngredients());
            pst.setInt(4, p.getCalories());
            pst.setInt(5, id);
            pst.executeUpdate();
            System.out.println("Plat modifiée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }
}
