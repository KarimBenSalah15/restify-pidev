package edu.esprit.services;

import edu.esprit.entities.Reservation;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationCrud implements ICrud <Reservation> {
    Connection cnx2;

    public ReservationCrud() {
        cnx2= MyConnection.getInstance().getCnx();
    }

    @Override
    public void modifierEntite(Reservation p, int id) {
        String requet = "UPDATE reservation SET date=?, heure=?, nbpersonne=? WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setDate(1, p.getDate());
            pst.setString(2, p.getHeure());
            pst.setInt(3, p.getNbrpersonne());
            pst.setInt(4, id);
            pst.executeUpdate();
            System.out.println("Reservation modifiée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }

    @Override
    public List<Reservation> afficherEntiite() {
        List<Reservation> reservations=new ArrayList<>();
        String req3="SELECT * FROM reservation";

        Statement stm = null;
        try {
            stm = cnx2.createStatement();
            ResultSet rs= stm.executeQuery(req3);
            while (rs.next())
            {
                Reservation p=new Reservation();
                p.setId(rs.getInt(1));
                p.setDate(rs.getDate(2));
                p.setHeure(rs.getString(3));
                p.setNbrpersonne(rs.getInt(4));
                reservations.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    @Override
    public void supprimerEntite(int id) {
        String requet = "DELETE FROM reservation WHERE id = ?";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Réservation supprimée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}
    }

    @Override
    public void ajouterEntite(Reservation r) {
        String requet="INSERT INTO reservation (date,heure,nbpersonne) VALUES(?,?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setDate(1,r.getDate());
            pst.setString(2,r.getHeure());
            pst.setInt(3,r.getNbrpersonne());

            pst.executeUpdate();
            System.out.println("Réservation ajoutée avec succès");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
