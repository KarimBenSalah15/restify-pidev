package sample.Evenement.Repository;

import sample.Evenement.Entities.Participant;
import sample.Evenement.ICrud;
import sample.Evenement.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons;

public  class ParticipantRepositorySql implements ICrud<Participant> {

    @Override
    public void update(Participant p, int id) {}

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
               // showAlert(Alert.AlertType.INFORMATION, "Information", "Le participant existe déjà dans la base de données.");
                throw new RuntimeException("Existing participant");
            } else {
                String insertQuery = "INSERT INTO participant(nom, prenom, email,tel) VALUES (?, ?, ?,?)";
                PreparedStatement insertPst = connectNow.getCnx().prepareStatement(insertQuery);
                insertPst.setString(1, p.getNom());
                insertPst.setString(2, p.getPrenom());
                insertPst.setString(3, p.getEmail());
                insertPst.setInt(4, p.getTel());
                insertPst.executeUpdate();
                //showAlert(Alert.AlertType.INFORMATION, "Succès", "Participant ajouté avec succès.");
              //  afficher();
            }
        } catch (NumberFormatException e) {
            //showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir un entier pour l'âge.");
            throw new RuntimeException("Veuillez saisir un entier pour le telephone");
        } catch (SQLException e) {
           throw new RuntimeException("Une erreur s'est produite lors de l'ajout du participant");
           //showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout du participant : " + e.getMessage());
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

}
