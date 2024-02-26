package services;

import entities.Utilisateur;
import tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeCrud implements ICrud<Utilisateur> {
    Connection cnx2;

    public EmployeCrud() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    @Override
    public void ajouterEntite(Utilisateur u) {
        String req1 = "INSERT INTO Utilisateur (nom, prenom, email, tel, login, mdp, role, poste, salaire, dateembauche) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        java.util.Date today = new java.util.Date();
        java.sql.Date sqlDate =  new java.sql.Date(today.getTime());
        try {
            PreparedStatement pst = cnx2.prepareStatement(req1);
            pst.setString(1, u.getNom());
            pst.setString(2, u.getPrenom());
            pst.setString(3, u.getEmail());
            pst.setInt(4, u.getTel());
            pst.setString(5, u.getLogin());
            pst.setString(6, u.getMdp());
            pst.setString(7, u.getRole());
            pst.setString(8, u.getPoste());
            pst.setInt(9, u.getSalaire());
            pst.setDate(10, sqlDate);
            pst.executeUpdate();
            System.out.println("Employé ajouté.");
            ;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public List<Utilisateur> afficherEntite() {
        List<Utilisateur> list = new ArrayList<>();
        String req2 = "SELECT * from Utilisateur";
        try {
            Statement st1 = cnx2.createStatement();
            ResultSet rs = st1.executeQuery(req2);

            while (rs.next()){
                Utilisateur p = new Utilisateur();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setPrenom(rs.getString(3));
                p.setEmail(rs.getString(4));
                p.setTel(rs.getInt(5));
                p.setLogin(rs.getString(6));
                p.setMdp(rs.getString(7));
                p.setRole(rs.getString(8));
                p.setPoste(rs.getString(9));
                p.setSalaire(rs.getInt(10));
                p.setDateembauche(rs.getDate(11));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void supprimerEntite(int id) {
        String req3 = "DELETE from Utilisateur where id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req3);
            pst.setInt(1,id);
            pst.executeUpdate();
            System.out.println("Employé supprimé.");
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifierEntite(Utilisateur p, Utilisateur p1) {
        String req4 = "UPDATE Utilisateur set nom=?, prenom=?, email=?, tel=?, login=?, mdp=?, poste=?, salaire =?, dateembauche=?" +
                " where id = ?";
        try{
            PreparedStatement pst = cnx2.prepareStatement(req4);
            pst.setString(1, p1.getNom());
            pst.setString(2, p1.getPrenom());
            pst.setString(3, p1.getEmail());
            pst.setInt(4, p1.getTel());
            pst.setString(5, p1.getLogin());
            pst.setString(6, p1.getMdp());
            pst.setString(7, p1.getPoste());
            pst.setInt(8, p1.getSalaire());
            pst.setDate(9, p1.getDateembauche());
            pst.setInt(10, p.getId());
            pst.executeUpdate();
            System.out.println("Employé modifié.");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
