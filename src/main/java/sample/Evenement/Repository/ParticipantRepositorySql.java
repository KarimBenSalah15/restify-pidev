package sample.Evenement.Repository;

import sample.Evenement.Entities.Evenement;
import sample.Evenement.Entities.Participant;
import sample.Evenement.ICrud;
import sample.Evenement.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons;

public  class ParticipantRepositorySql implements ICrud<Participant> {


    @Override
    public void update(Participant p, int id) {

    }

    @Override
    public List<Participant> getAll() {
        List<Participant> eventsList = new ArrayList<>();
        MyConnection connectNow = MyConnection.getInstance();;
        Connection connectDB = connectNow.getCnx();
        try {
            String query = "SELECT *  FROM particpant";
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Participant Participant = this.mapTableToObject(resultSet);
                eventsList.add(Participant);
            }
            return  eventsList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void create(Participant p) {
        String query = "SELECT * FROM participant WHERE nom = ?";
        MyConnection connectNow =  MyConnection.getInstance();
        PreparedStatement pst = null;
        try {

            pst = connectNow.getCnx().prepareStatement(query);
            pst.setString(1, p.getNom());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                throw new RuntimeException("Existing participant");
            } else {
                String insertQuery = "INSERT INTO participant(nom, prenom, email,tel,id_event) VALUES (?, ?, ?,?,?)";
                PreparedStatement insertPst = connectNow.getCnx().prepareStatement(insertQuery);
                insertPst.setString(1, p.getNom());
                insertPst.setString(2, p.getPrenom());
                insertPst.setString(3, p.getEmail());
                insertPst.setInt(4, p.getTel());

               insertPst.setInt(5,p.getEvenement().getId());

                insertPst.executeUpdate();//  afficher();
            }
        } catch (NumberFormatException e) {

            throw new RuntimeException("Veuillez saisir un entier pour le telephone");
        } catch (SQLException e) {
           throw new RuntimeException("Une erreur s'est produite lors de l'ajout du participant");
        }
    }

    @Override
    public Participant mapTableToObject(ResultSet resultSet)  {
        try {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String email = resultSet.getString("email");
            int tel = resultSet.getInt("tel");

           return new Participant(id,nom,prenom,email,tel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  List<Participant> getParticpantsByEvent(Evenement evenement) {
        List<Participant> eventsList = new ArrayList<>();
        MyConnection connectNow = MyConnection.getInstance();;
        Connection connectDB = connectNow.getCnx();

        try {
            System.out.println("getParticpantsByEvent"+evenement.getId());
           // String query = "SELECT * FROM participant WHERE id_event  = ? ";
            String query = "Select *  from participant left join evenement on participant.id = ?";
            PreparedStatement pst = connectDB.prepareStatement(query);
            pst.setInt(1, evenement.getId());
            ResultSet resultSet = pst.executeQuery(query);
            while (resultSet.next()) {
                Participant Participant = this.mapTableToObject(resultSet);
                eventsList.add(Participant);
            }
            return  eventsList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();

    }
}
