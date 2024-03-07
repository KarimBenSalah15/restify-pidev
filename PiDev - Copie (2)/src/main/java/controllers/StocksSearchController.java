package controllers;

    import javafx.scene.chart.*;
    import com.twilio.Twilio;
    import com.twilio.rest.api.v2010.account.Message;
    import com.twilio.type.PhoneNumber;


    import javafx.scene.chart.BarChart;
    import java.io.IOException;
    import java.net.URL;
    import java.util.ResourceBundle;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.control.Button;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.control.TextField;
    import javafx.scene.input.MouseEvent;

    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;

    import java.net.URL;
    import java.sql.*;
    import java.util.ResourceBundle;
    import java.util.logging.Level;
    import java.util.logging.Logger;

    import javafx.scene.input.MouseEvent;
    import javafx.scene.control.Alert;
    import javafx.scene.chart.BarChart;
    import javafx.scene.chart.CategoryAxis;
    import javafx.scene.chart.NumberAxis;
    import javafx.scene.chart.XYChart;

    import javafx.scene.control.TextField;

public class StocksSearchController implements Initializable {
            private final String ACCOUNT_SID = "AC3bf5360731b75413420aba6003748918";
                private final String AUTH_TOKEN ="295bd6a16d6ac70aeee107071955582f";



            private Connection connectDB;
            @FXML
            private BarChart<String, Integer> stockBarChart;

            @FXML
            private TableView<StocksSearchModel> StocksTableView;



            @FXML
            private ResourceBundle resources;
            @FXML
            private LineChart<String, Number> lineChart;

            @FXML
            private URL location;


            @FXML
            private TableColumn<StocksSearchModel, Button> stocksPlusTableColumn;

            @FXML
            private TableColumn<StocksSearchModel, Button> stocksMoinsTableColumn;
            @FXML
            private BarChart<String, Number> barChart;

            @FXML
            private CategoryAxis xAxis;

            @FXML
            private TextField keywordTextField;

            @FXML
            private NumberAxis yAxis;

            int id=0;

            @FXML
            private Button btnDelete;

            @FXML
            private Button btnSave;

            @FXML
            private Button btnUpdate;
            @FXML
            private TextField nomTextField;
            @FXML
            private TextField quantiteTextField;

            @FXML
            private TableColumn<?, ?> stocksIDTableColumn;

            @FXML
            private TableColumn<?, ?> stocksNomTableColumn;
            @FXML
            private TableColumn<?,?>  stocksQuantiteTableColumn;



            MyConnection cnx2 =null;
            PreparedStatement pst=null;

           public void sendSMS(String to,String body){
                Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
                Message message =Message.creator(
                        new PhoneNumber("+21656607836"),
                        new PhoneNumber("+12195330561"),

                body)
                        .create();
            }


            @FXML
            void creatStocks(ActionEvent event) {
                String nom = nomTextField.getText().trim();
                String quantite = quantiteTextField.getText().trim();

                if (connectDB == null) {
                    MyConnection connectNow = new MyConnection();
                    connectDB = connectNow.getCnx();
                }
                if (!nom.isEmpty() && !quantite.isEmpty()) {
                    try {
                        MyConnection connectNow = new MyConnection();
                        Connection connectDB = connectNow.getCnx();

                        /*// Commencer une transaction
                        connectDB.setAutoCommit(false);*/

                        // Insérer le nouveau stock dans la table stock
                        String insertQueryStock = "INSERT INTO stock(nom, quantite) VALUES (?, ?)";
                        PreparedStatement insertPstStock = cnx2.getCnx().prepareStatement(insertQueryStock);
                        insertPstStock.setString(1, nom);
                        insertPstStock.setInt(2, Integer.parseInt(quantite));
                        insertPstStock.executeUpdate();

                       /* // Insérer le nouveau nom dans la table produit
                        String insertQueryProduct = "INSERT INTO produit(nom) VALUES (?)";
                        PreparedStatement insertPstProduct = cnx2.getCnx().prepareStatement(insertQueryProduct);
                        insertPstProduct.setString(1, nom);
                        insertPstProduct.executeUpdate();*/



                        /*// Valider la transaction
                        connectDB.commit();*/

                        showAlert(Alert.AlertType.INFORMATION, "Succès",  "Stock ajouté avec succès.");
                        afficher();
                    } catch (SQLException e) {
                        // En cas d'erreur, annuler la transaction
                        try {
                            connectDB.rollback();
                        } catch (SQLException rollbackException) {
                            rollbackException.printStackTrace();
                        }

                        showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de l'ajout du stock : " + e.getMessage());
                    } finally {
                        // Rétablir le mode de traitement automatique des commits
                        try {
                            connectDB.setAutoCommit(true);
                        } catch (SQLException autoCommitException) {
                            autoCommitException.printStackTrace();
                        }
                    }
                } else {
                    // Afficher une alerte si un champ est vide
                    showAlert(Alert.AlertType.WARNING, "Avertissement",  "Veuillez remplir tous les champs.");
                }
                setupBarChart();
            }
            public void rafraichirInterfaceStock() {
                afficher();
            }



            private void showAlert(Alert.AlertType type, String title, String content) {
                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setContentText(content);
                alert.showAndWait();
            }

            @FXML
            void clear( ) {
                StocksTableView.getSelectionModel().clearSelection();
                afficher();
                nomTextField.setText(null);
                quantiteTextField.setText(null);
                btnSave.setDisable(false);



            }


            @FXML
            void searchstocks() {
                String keyword = keywordTextField.getText().trim();
                if (!keyword.isEmpty()) {
                    // Effacer la liste existante pour afficher les nouveaux résultats
                    StocksSearchModelObservableList.clear();
                    MyConnection connectNow = new MyConnection();
                    Connection connectDB = connectNow.getCnx();

                    try {
                        // Utiliser une requête paramétrée pour éviter les injections SQL
                        String productSearchQuery = "SELECT * FROM stock WHERE Nom LIKE ?";
                        PreparedStatement searchStatement = connectDB.prepareStatement(productSearchQuery);
                        // Modifier la requête pour rechercher les stock dont le nom commence par la lettre entrée
                        searchStatement.setString(1, keyword + "%");
                        ResultSet queryOutput = searchStatement.executeQuery();
                        while (queryOutput.next()) {
                            Integer queryid = queryOutput.getInt("ID");
                            String querynom = queryOutput.getString("Nom");

                            Integer queryquantite = queryOutput.getInt("quantite");
                            StocksSearchModelObservableList.add(new StocksSearchModel(queryid, querynom, queryquantite));
                        }
                        // Mettre à jour la TableView avec les résultats de la recherche
                        StocksTableView.setItems(StocksSearchModelObservableList);
                    } catch (SQLException e) {
                        Logger.getLogger(ProdcutSearchController.class.getName()).log(Level.SEVERE, null, e);
                        e.printStackTrace();
                    }
                } else {
                    // Si le champ de recherche est vide, afficher tous les stocks
                    afficher();
                }
            }
            @FXML
            void deleteSocks(ActionEvent event) {

                StocksSearchModel stock = StocksTableView.getSelectionModel().getSelectedItem();
                if (stock != null) {
                    // Un produit est sélectionné, afficher une alerte de confirmation
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce produit ?");

                    // Afficher l'alerte et attendre la réponse de l'utilisateur
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // Si l'utilisateur a cliqué sur OK, supprimer le produit
                            String delete ="delete from stock where id =?";
                            cnx2=MyConnection.getInstance();
                            try {
                                pst=cnx2.getCnx().prepareStatement(delete);
                                pst.setInt(1,stock.getId());
                                pst.executeUpdate();
                                afficher();
                                clear();
                            } catch (SQLException e) {
                                showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de la suppression du stock : " + e.getMessage());
                            }
                        }
                    });
                } else {
                    // Aucun produit sélectionné, afficher un message d'erreur
                    showAlert(Alert.AlertType.ERROR, "Erreur",  "Veuillez sélectionner un stock à supprimer.");
                }
                setupBarChart();
            }


            private void nbStock(){
                StringBuilder messageBody = new StringBuilder("Réapprovisionner les stocks pour les produits suivants :\n");
                boolean needsReplenishment = false;

                for(StocksSearchModel stock: StocksSearchModelObservableList){
                    if(stock.getQuantite() < 5){
                        messageBody.append(stock.getNom()).append("\n");
                        needsReplenishment = true;
                    }
                }

                if(needsReplenishment){
                    sendSMS("+21656607836", messageBody.toString());
                }
            }



            @FXML
            void getData(MouseEvent event) {
                StocksSearchModel stocks = StocksTableView.getSelectionModel().getSelectedItem();
                id =stocks.getId();
                nomTextField.setText(stocks.getNom());
                quantiteTextField.setText(String.valueOf(stocks.getQuantite()));
                btnSave.setDisable(true);

            }

            @FXML
            void updateStocks(ActionEvent event) {

                StocksSearchModel stock = StocksTableView.getSelectionModel().getSelectedItem();
                if (stock != null) {
                    // Un produit est sélectionné, afficher une alerte de confirmation
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour ce stock ?");

                    // Afficher l'alerte et attendre la réponse de l'utilisateur
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // Si l'utilisateur a cliqué sur OK, mettre à jour le produit
                            String update ="update stock set Nom=?, Quantite =? where id=?";
                            cnx2=MyConnection.getInstance();
                            try {
                                pst=cnx2.cnx.prepareStatement(update);
                                pst.setString(1, nomTextField.getText());


                                pst.setInt(2, Integer.parseInt(quantiteTextField.getText()));
                                pst.setInt(3,id);

                                pst.executeUpdate();
                                afficher();
                                clear();
                            }catch(NumberFormatException e){
                                showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez saisir un entier");
                            }


                            catch (SQLException e) {
                                showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de la mise à jour du stock : " + e.getMessage());
                            }
                        }
                    });
                } else {
                    // Aucun produit sélectionné, afficher un message d'erreur
                    showAlert(Alert.AlertType.ERROR, "Erreur",  "Veuillez sélectionner un stock à mettre à jour.");
                }
                setupBarChart();
            }
            ObservableList<StocksSearchModel> StocksSearchModelObservableList = FXCollections.observableArrayList();


            private void afficher() {
                StocksSearchModelObservableList.clear();
                MyConnection connectNow = new MyConnection();
                Connection connectDB = connectNow.getCnx();

                try {
                    String stocksViewQuery = "SELECT s.ID AS stock_id, p.nom AS produit_nom, s.Quantite FROM stock s LEFT JOIN produit p ON s.produit_id = p.ID";
                    Statement statement = connectDB.createStatement();
                    ResultSet queryOutput = statement.executeQuery(stocksViewQuery);
                    while (queryOutput.next()) {
                        Integer stockId = queryOutput.getInt("stock_id");
                        String produitNom = queryOutput.getString("produit_nom");
                        Integer quantite = queryOutput.getInt("Quantite");
                        StocksSearchModelObservableList.add(new StocksSearchModel(stockId, produitNom, quantite));
                    }

                    stocksIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                    stocksNomTableColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
                    stocksQuantiteTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));

                    StocksTableView.setItems(StocksSearchModelObservableList);
                } catch (SQLException e) {
                    Logger.getLogger(StocksSearchController.class.getName()).log(Level.SEVERE, null, e);
                    e.printStackTrace();
                }
            }

            private void setupBarChart() {
                XYChart.Series<String, Integer> series = new XYChart.Series<>();
                try {
                    String productTypeQuery = "SELECT nom, SUM(quantite) as total_quantite FROM stock GROUP BY nom";
                    Statement statement = cnx2.getCnx().createStatement(); // Utilisation de cnx2 ici
                    ResultSet queryOutput = statement.executeQuery(productTypeQuery);
                    while (queryOutput.next()) {
                        String type = queryOutput.getString("nom");
                        int totalPrix = queryOutput.getInt("total_quantite");
                        series.getData().add(new XYChart.Data<>(type, totalPrix));
                    }
                    stockBarChart.getData().clear(); // Supprimer les données existantes pour éviter la duplication
                    stockBarChart.getData().add(series);
                } catch (SQLException e) {
                    Logger.getLogger(ProdcutSearchController.class.getName()).log(Level.SEVERE, null, e);
                    e.printStackTrace();
                }
            }

            private void executeQuery() {
                String sql = "SELECT s.*, p.* FROM stock s INNER JOIN produit p ON s.nom = p.nom";

                try (Connection connection = MyConnection.getInstance().getCnx();
                     PreparedStatement statement = connection.prepareStatement(sql);
                     ResultSet resultSet = statement.executeQuery()) {

                    // Traiter les résultats de la requête
                    while (resultSet.next()) {
                        // Exemple de traitement des résultats
                        int stockId = resultSet.getInt("s.id");
                        String stockNom = resultSet.getString("s.nom");
                        int stockQuantite = resultSet.getInt("s.quantite");

                        int produitId = resultSet.getInt("p.id");
                        String produitNom = resultSet.getString("p.nom");
                        // Continuez à récupérer d'autres colonnes selon vos besoins

                        // Faites quelque chose avec les résultats, comme les afficher dans votre interface utilisateur
                        afficher();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer les erreurs de base de données
                }
            }



            @Override
            public void initialize(URL url, ResourceBundle resourceBundle) {

                cnx2 = MyConnection.getInstance();
                setupBarChart();

                afficher();

                nbStock();
               /* stocksPlusTableColumn.setCellValueFactory(new PropertyValueFactory<>("buttonPlus"));
                stocksMoinsTableColumn.setCellValueFactory(new PropertyValueFactory<>("buttonMoins"));*/
                // Ajouter un écouteur de changement de texte au champ de recherche
                keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    // Appeler la méthode searchProduct à chaque fois que le texte du champ de recherche change
                    searchstocks();
                });

            }

        }



