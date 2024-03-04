package sample.Evenement.Repository;

import sample.Evenement.Entities.Evenement;
import sample.Evenement.ICrud;
import sample.Evenement.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons;

public class EventsRepositorySql  implements ICrud<Evenement> {



    @Override
    public void update(Evenement e, int id) {
        MyConnection connectNow = MyConnection.getInstance();
        Connection connectDB = connectNow.getCnx();
        try {
            String updateQuery = "UPDATE evenement SET nom=?, date=?, duree=?, etat=?, type=?, nbrparticipation=? WHERE id=?";
            PreparedStatement preparedStatement = connectDB.prepareStatement(updateQuery);
            preparedStatement.setString(1, e.getNom());
            preparedStatement.setDate(2,  e.getDate());
            preparedStatement.setString(3, e.getDuree());
            preparedStatement.setString(4, e.getEtat());
            preparedStatement.setString(5, e.getType());
            preparedStatement.setInt(6, e.getNbrparticipation());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de l'événement dans la base de données.");
        }
    }


    @Override
    public List<Evenement> getAll() {
        List<Evenement> eventsList = new ArrayList<>();
        MyConnection connectNow = MyConnection.getInstance();
        Connection connectDB = connectNow.getCnx();
        try {
            String query = "SELECT *  FROM evenement";
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Evenement evenement = this.mapTableToObject(resultSet);
                eventsList.add(evenement);
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
    public void create(Evenement p) {


    }

    @Override
    public Evenement mapTableToObject(ResultSet resultSet)  {
        try {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            Date date = resultSet.getDate("date");
            String duree = resultSet.getString("duree");
            String etat = resultSet.getString("etat");
            String type = resultSet.getString("type");
            int nbrparticipation = resultSet.getInt("nbrparticipation");

           return new Evenement(id,nom,date,duree,etat,type,nbrparticipation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
