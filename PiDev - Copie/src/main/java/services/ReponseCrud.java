package services;

import entities.Reponse;
import tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseCrud implements ICrud<Reponse>{
    Connection cnx2;

    public ReponseCrud() {
        cnx2= MyConnection.getInstance().getCnx();
    }

    @Override
    public void modifierEntite(Reponse p, int id) {
        String requet = "UPDATE reponse SET message=?, date=? WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setString(1, p.getMessage());
            pst.setDate(2, p.getDate());

            pst.setInt(3, id);

            pst.executeUpdate();
            System.out.println("reponse modifiée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }

    @Override
    public List<Reponse> afficherEntiite() {
        List<Reponse> reponses =new ArrayList<>();
        String req3="SELECT * FROM reponse";

        Statement stm = null;
        try {
            stm = cnx2.createStatement();
            ResultSet rs= stm.executeQuery(req3);
            while (rs.next())
            {
                Reponse p=new Reponse();
                p.setId(rs.getInt(1));
                p.setMessage(rs.getString(4));
                p.setDate(rs.getDate(5));
                p.setIdA(rs.getInt(2));
                p.setIdR(rs.getInt(3));


                reponses.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reponses;
    }

    @Override
    public void supprimerEntite(int id) {
        String requet = "DELETE FROM reponse WHERE id = ?";
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
    public void ajouterEntite(Reponse p) {
        String requet="INSERT INTO reponse (message,date,admin_id,reclamation_id) VALUES(?,?,?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setString(1,p.getMessage());
            pst.setDate(2,p.getDate());
            pst.setInt(3,p.getIdA());
            pst.setInt(4,p.getIdR());


            pst.executeUpdate();
            System.out.println("Ajoute");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
