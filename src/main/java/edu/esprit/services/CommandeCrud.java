package edu.esprit.services;

import edu.esprit.controllers.ShoppingCart;
import edu.esprit.entities.Commande;
import edu.esprit.entities.Plat;
import edu.esprit.entities.PlatCommande;
import edu.esprit.tools.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandeCrud implements ICrud<Commande> {

    private static final String INSERT_COMMANDE_SQL = "INSERT INTO commande (prix, traiter) VALUES (?, ?)";
    private static final String INSERT_COMMANDE_PLAT_SQL = "INSERT INTO plat_commande (commande_id, plat_id, quantite) VALUES (?, ?, ?)";
    private static final String UPDATE_COMMANDE_TRAITER_SQL = "UPDATE commande SET traiter = ? WHERE id = ?";
    private static final String UPDATE_COMMANDE_PLAT_SQL = "UPDATE plat_commande SET quantite = ? WHERE plat_id = ?";

    private Connection connection;

    public CommandeCrud() {
        connection = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouterEntite(Commande commande) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMANDE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, commande.getTotal());
            preparedStatement.setBoolean(2, commande.isTraiter());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int commandeId = generatedKeys.getInt(1);
                ajouterPlats(commande.getPlats(), commandeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int ajouterEntite2(Commande commande) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMANDE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, commande.getTotal());
            preparedStatement.setBoolean(2, commande.isTraiter());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int commandeId = generatedKeys.getInt(1);
                ajouterPlats(commande.getPlats(), commandeId);
                return commandeId; // Return the generated command ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void ajouterPlats(List<Plat> plats, int commandeId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMANDE_PLAT_SQL)) {
            for (Plat plat : plats) {
                int quantity = ShoppingCart.getInstance().getQuantity(plat.getNom());
                preparedStatement.setInt(1, commandeId);
                preparedStatement.setInt(2, plat.getId());
                preparedStatement.setInt(3, quantity);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Commande> afficherEntite() {
        List<Commande> commandes = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.id, c.prix, pc.plat_id, pc.quantite FROM commande c JOIN plat_commande pc ON c.id = pc.commande_id")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Integer, Commande> commandeMap = new HashMap<>();
            while (resultSet.next()) {
                int commandeId = resultSet.getInt("id");
                float total = resultSet.getFloat("prix");
                int platId = resultSet.getInt("plat_id");
                int quantite = resultSet.getInt("quantite");

                Commande commande;
                if (!commandeMap.containsKey(commandeId)) {
                    commande = new Commande(commandeId, total, new ArrayList<>() , false);
                    commande.setPlatCommandes(new ArrayList<>()); // Initialize platCommandes
                    commandeMap.put(commandeId, commande);
                } else {
                    commande = commandeMap.get(commandeId);
                }

                Plat plat = recupererPlat(platId);
                PlatCommande platCommande = new PlatCommande(plat, quantite);
                commande.getPlatCommandes().add(platCommande);
            }
            commandes.addAll(commandeMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }
    public void traiterCommande(int commandeId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMMANDE_TRAITER_SQL)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, commandeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Commande> afficherEntite2() {
        List<Commande> commandes = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.id, c.prix, pc.plat_id, pc.quantite FROM commande c JOIN plat_commande pc ON c.id = pc.commande_id where c.traiter=0")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Integer, Commande> commandeMap = new HashMap<>();
            while (resultSet.next()) {
                int commandeId = resultSet.getInt("id");
                float total = resultSet.getFloat("prix");
                int platId = resultSet.getInt("plat_id");
                int quantite = resultSet.getInt("quantite");

                Commande commande;
                if (!commandeMap.containsKey(commandeId)) {
                    commande = new Commande(commandeId, total, new ArrayList<>() , false);
                    commande.setPlatCommandes(new ArrayList<>()); // Initialize platCommandes
                    commandeMap.put(commandeId, commande);
                } else {
                    commande = commandeMap.get(commandeId);
                }

                Plat plat = recupererPlat(platId);
                PlatCommande platCommande = new PlatCommande(plat, quantite);
                commande.getPlatCommandes().add(platCommande);
            }
            commandes.addAll(commandeMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }
    public List<Commande> afficherEntite3() {
        List<Commande> commandes = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.id, c.prix, pc.plat_id, pc.quantite FROM commande c JOIN plat_commande pc ON c.id = pc.commande_id where c.traiter=1")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Integer, Commande> commandeMap = new HashMap<>();
            while (resultSet.next()) {
                int commandeId = resultSet.getInt("id");
                float total = resultSet.getFloat("prix");
                int platId = resultSet.getInt("plat_id");
                int quantite = resultSet.getInt("quantite");

                Commande commande;
                if (!commandeMap.containsKey(commandeId)) {
                    commande = new Commande(commandeId, total, new ArrayList<>() , false);
                    commande.setPlatCommandes(new ArrayList<>()); // Initialize platCommandes
                    commandeMap.put(commandeId, commande);
                } else {
                    commande = commandeMap.get(commandeId);
                }

                Plat plat = recupererPlat(platId);
                PlatCommande platCommande = new PlatCommande(plat, quantite);
                commande.getPlatCommandes().add(platCommande);
            }
            commandes.addAll(commandeMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }
    private Plat recupererPlat(int platId) {
        Plat plat = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM plat WHERE id = ?")) {
            preparedStatement.setInt(1, platId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Retrieve Plat attributes from ResultSet and create Plat object
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                float prix = resultSet.getFloat("prix");
                plat = new Plat(id, nom, prix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plat;
    }



    @Override
    public void supprimerEntite(int id) {
        try {
            // Delete related records in the plat table first
            String deletePlatSQL = "DELETE FROM plat_commande WHERE commande_id = ?";
            try (PreparedStatement deletePlatStatement = connection.prepareStatement(deletePlatSQL)) {
                deletePlatStatement.setInt(1, id);
                deletePlatStatement.executeUpdate();
            }

            // Now, delete the Commande entity
            String deleteCommandeSQL = "DELETE FROM commande WHERE id = ?";
            try (PreparedStatement deleteCommandeStatement = connection.prepareStatement(deleteCommandeSQL)) {
                deleteCommandeStatement.setInt(1, id);
                deleteCommandeStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions or log errors as needed
        }
    }

    @Override
    public void modifierEntite(Commande p, int id) {

    }

    public void modifierCommande2(int platId, int newQuantity, int commandeId) {
        try {
            String sql = "UPDATE plat_commande SET quantite = ? WHERE plat_id = ? AND commande_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, newQuantity);
                preparedStatement.setInt(2, platId);
                preparedStatement.setInt(3, commandeId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions or log errors as needed
        }
    }

    public void modifierCommande3(int commandeId, float newPrice) {
        try {
            String sql = "UPDATE commande SET prix = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setFloat(1, newPrice);
                preparedStatement.setInt(2, commandeId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions or log errors as needed
        }
    }
}







