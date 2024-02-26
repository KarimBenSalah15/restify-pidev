package edu.esprit.services;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Table;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableCrud implements ICrud <Table>{
    Connection cnx2;

    public TableCrud() {
        cnx2= MyConnection.getInstance().getCnx();
    }
    @Override
    public void modifierEntite(Table p, int id) {
        String requet = "UPDATE `table` SET nbrplace=?, place=?, dispo=? WHERE idtable=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet);
            pst.setInt(1, p.getNbrplace());
            pst.setString(2, p.getPlace());
            pst.setBoolean(3, p.isDispo());
            pst.setInt(4, id);
            pst.executeUpdate();
            System.out.println("Table modifiée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}

    }

    @Override
    public List<Table> afficherEntiite() {
        List<Table> reservations=new ArrayList<>();
        String req3="SELECT * FROM `table`";

        Statement stm = null;
        try {
            stm = cnx2.createStatement();
            ResultSet rs= stm.executeQuery(req3);
            while (rs.next())
            {
                Table p=new Table();
                p.setIdtable(rs.getInt(1));
                p.setNbrplace(rs.getInt(2));
                p.setPlace(rs.getString(3));
                p.setDispo(rs.getBoolean(4));
                reservations.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    @Override
    public void supprimerEntite(int id) {
        String requet = "DELETE FROM `table` WHERE idtable = ?";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Table supprimée avec succès");
        }
        catch (SQLException e)
        {System.out.println(e.getMessage());}

    }

    @Override
    public void ajouterEntite(Table p) {
        String requet="INSERT INTO `table` (nbrplace,place,dispo) VALUES(?,?,?)";
        try {
            PreparedStatement pst =cnx2.prepareStatement(requet);
            pst.setInt(1,p.getNbrplace());
            pst.setString(2,p.getPlace());
            pst.setBoolean(3,p.isDispo());
            pst.executeUpdate();
            System.out.println("Table ajoutée avec succès");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
