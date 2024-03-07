package services;

import entities.Reservation;
import tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationCrud implements ICrud<Reservation> {
    Connection cnx2;

    public ReservationCrud() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    @Override
    public void modifierEntite(Reservation r, int id) {
        String requet = "UPDATE `reservation` SET date=?, heure=?, nbpersonne=?, tab_id=? WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setDate(1, r.getDate());
            pst.setString(2, r.getHeure());
            pst.setInt(3, r.getNbrpersonne());
            pst.setInt(4, r.getTabId());
            pst.setInt(5, id);
            pst.executeUpdate();
            System.out.println("Reservation modifiée avec succès");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reservation> afficherEntiite() {
        List<Reservation> reservations = new ArrayList<>();
        String req3 = "SELECT * FROM `reservation`";

        try (Statement stm = cnx2.createStatement()) {
            try (ResultSet rs = stm.executeQuery(req3)) {
                while (rs.next()) {
                    Reservation r = new Reservation(
                            rs.getInt("id"),
                            rs.getDate("date"),
                            rs.getString("heure"),
                            rs.getInt("nbpersonne"),
                            rs.getInt("tab_id"),
                            rs.getInt("user_id")
                    );
                    reservations.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    @Override
    public void supprimerEntite(int id) {
        String requet = "DELETE FROM `reservation` WHERE id = ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Réservation supprimée avec succès");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouterEntite(Reservation r) {
        String requet = "INSERT INTO `reservation` (date,heure,nbpersonne,tab_id,user_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setDate(1, r.getDate());
            pst.setString(2, r.getHeure());
            pst.setInt(3, r.getNbrpersonne());
            pst.setInt(4, r.getTabId());
            pst.setInt(5,MyConnection.getInstance().getIdenvoi());

            pst.executeUpdate();

            System.out.println("Réservation ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void updateDispo(int id) {
        String req3 = "UPDATE `table` SET dispo = FALSE WHERE idtable = ?";

        try (PreparedStatement pst = cnx2.prepareStatement(req3)) {

            pst.setInt(1,id);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Table modifiée avec succès");
            } else {
                System.out.println("Aucune table modifiée. L'ID n'existe peut-être pas.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erreur de conversion d'ID en entier : " + e.getMessage());
        }
    }
    public void updateDispo2(int id) {
        String req3 = "UPDATE `table` SET dispo = TRUE WHERE idtable = ?";

        try (PreparedStatement pst = cnx2.prepareStatement(req3)) {

            pst.setInt(1,id);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Table modifiée avec succès");
            } else {
                System.out.println("Aucune table modifiée. L'ID n'existe peut-être pas.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erreur de conversion d'ID en entier : " + e.getMessage());
        }
    }
}
