            package projectfinder;

            import javafx.collections.FXCollections;
            import javafx.collections.ObservableList;
            import javafx.event.ActionEvent;
            import javafx.fxml.FXML;
            import javafx.fxml.Initializable;
            import javafx.scene.chart.*;
            import javafx.scene.control.*;
            import javafx.scene.control.cell.PropertyValueFactory;
            import javafx.scene.input.MouseEvent;

            import java.net.URL;
            import java.sql.*;
            import java.util.ResourceBundle;
            import java.util.logging.Level;
            import java.util.logging.Logger;
            public class ProdcutSearchController implements Initializable {
            @FXML
                private TableView<ProductSearchModel> ProductTableView;
                private final ObservableList<ProductSearchModel> data = FXCollections.observableArrayList();
            int id=0;
                @FXML
                private Pagination pagination;
                private static final int ITEMS_PER_PAGE = 5;
                @FXML
                private Button btnClear;

                @FXML
                private Button btnDelete;

                @FXML
                private Button btnSave;

                @FXML
                private Button btnUpdate;

                @FXML
                private Button btnSearch;

                @FXML
                private Button btnClearSearch;


                @FXML
                private TextField keywordTextField;

                @FXML
                private TextField nomTextField;

                @FXML
                private TextField prixTextField;
                @FXML
                private TextField typeTextField;
                @FXML
                private PieChart productPieChart;

                @FXML
                void clear( ) {
                    ProductTableView.getSelectionModel().clearSelection();
                    afficher();
                    nomTextField.setText(null);
                            prixTextField.setText(null);
                    typeTextField.setText(null);
                    btnSave.setDisable(false);



                }

            @FXML
                private TableColumn<ProductSearchModel, Integer> productIDTableColumn;
            @FXML
                private TableColumn<ProductSearchModel, String> productNomTableColumn;

            @FXML
                private TableColumn<ProductSearchModel,String>productTypeTableColumn;
            @FXML
                private TableColumn<ProductSearchModel,Integer>productPrixTableColumn;
                @FXML
                private BarChart<String, Integer> productBarChart;

                @FXML
                private CategoryAxis xAxis;

                @FXML
                private NumberAxis yAxis;

                @FXML
                void clearField(ActionEvent event) {

                }
                MyConnection cnx2 =null;
                PreparedStatement pst=null;

                @FXML


                void creatProduct(ActionEvent event) {
                    String nom = nomTextField.getText().trim();

                    String type = typeTextField.getText().trim();
                    String prix = prixTextField.getText().trim();

                    // Vérifier que les champs ne sont pas vides
                    if (!nom.isEmpty()  && !type.isEmpty() && !prix.isEmpty()) {
                        String query = "SELECT * FROM produit WHERE nom = ?  ";
                        cnx2 = MyConnection.getInstance();
                        try {
                            pst = cnx2.getCnx().prepareStatement(query);
                            pst.setString(1, nom);

                            //pst.setString(2, type);
                            //pst.setInt(3, Integer.parseInt(prix));

                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                showAlert(Alert.AlertType.INFORMATION, "Information",  "Le produit existe déjà dans la base de données.");
                            } else {
                                // Insérer le nouveau produit dans la base de données
                                String insertQuery = "INSERT INTO produit(nom, type, prix) VALUES (?, ?, ?)";
                                PreparedStatement insertPst = cnx2.getCnx().prepareStatement(insertQuery);
                                insertPst.setString(1, nom);

                                insertPst.setString(2, type);
                                insertPst.setInt(3, Integer.parseInt(prix));
                                insertPst.executeUpdate();
                                showAlert(Alert.AlertType.INFORMATION, "Succès",  "Produit ajouté avec succès.");
                                afficher();
                            }
                        }
                        catch(NumberFormatException e){
                            showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez saisir un entier");
                        }
                        catch (SQLException e) {
                            showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de l'ajout du produit : " + e.getMessage());
                        }
                    } else {
                        // Afficher une alerte si un champ est vide
                        showAlert(Alert.AlertType.WARNING, "Avertissement",  "Veuillez remplir tous les champs.");
                    }
                    setupPieChart();
                    setupBarChart();
                }

                private void showAlert(Alert.AlertType type, String title, String content) {
                    Alert alert = new Alert(type);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.setContentText(content);
                    alert.showAndWait();
                }



                @FXML
                void getData(MouseEvent event) {
                    ProductSearchModel produit = ProductTableView.getSelectionModel().getSelectedItem();
                    id =produit.getId();
                    nomTextField.setText(produit.getNom());

                    typeTextField.setText(produit.getType());
                    prixTextField.setText(String.valueOf(produit.getPrix()));
                    btnSave.setDisable(true);

                }


                @FXML
                void deleteProduct(ActionEvent event) {
                    ProductSearchModel produit = ProductTableView.getSelectionModel().getSelectedItem();
                    if (produit != null) {
                        // Un produit est sélectionné, afficher une alerte de confirmation
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Confirmation");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce produit ?");

                        // Afficher l'alerte et attendre la réponse de l'utilisateur
                        confirmationAlert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                // Si l'utilisateur a cliqué sur OK, supprimer le produit
                                String delete ="delete from produit where id =?";
                                cnx2=MyConnection.getInstance();
                                try {
                                    pst=cnx2.cnx.prepareStatement(delete);
                                    pst.setInt(1, produit.getId());
                                    pst.executeUpdate();
                                    afficher();
                                    clear();
                                } catch (SQLException e) {
                                    showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de la suppression du produit : " + e.getMessage());
                                }
                            }
                        });
                    } else {
                        // Aucun produit sélectionné, afficher un message d'erreur
                        showAlert(Alert.AlertType.ERROR, "Erreur",  "Veuillez sélectionner un produit à supprimer.");
                    }
                    setupPieChart();
                    setupBarChart();

                }



                @FXML
                void updateProduct(ActionEvent event) {
                    ProductSearchModel produit = ProductTableView.getSelectionModel().getSelectedItem();
                    if (produit != null) {
                        // Un produit est sélectionné, afficher une alerte de confirmation
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Confirmation");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour ce produit ?");

                        // Afficher l'alerte et attendre la réponse de l'utilisateur
                        confirmationAlert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                // Si l'utilisateur a cliqué sur OK, mettre à jour le produit
                                String update ="update produit set Nom=?, Type =?, Prix=? where id=?";
                                cnx2=MyConnection.getInstance();
                                try {
                                    pst=cnx2.cnx.prepareStatement(update);
                                    pst.setString(1, nomTextField.getText());

                                    pst.setString(2, typeTextField.getText());
                                    pst.setInt(3, Integer.parseInt(prixTextField.getText()));
                                    pst.setInt(4, produit.getId());
                                    pst.executeUpdate();
                                    afficher();
                                    clear();
                                }catch(NumberFormatException e){
                                    showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez saisir un entier");
                                }


                                catch (SQLException e) {
                                    showAlert(Alert.AlertType.ERROR, "Erreur",  "Une erreur s'est produite lors de la mise à jour du produit : " + e.getMessage());
                                }
                            }
                        });
                    } else {
                        // Aucun produit sélectionné, afficher un message d'erreur
                        showAlert(Alert.AlertType.ERROR, "Erreur",  "Veuillez sélectionner un produit à mettre à jour.");
                    }
                    setupPieChart();
                    setupBarChart();

                }



                ObservableList<ProductSearchModel> productSearchModelObservableList = FXCollections.observableArrayList();

                @FXML
                 public void initialize(URL url, ResourceBundle resourceBundle) {
                    // Charger les données à partir de la base de données
                    fetchDataFromDatabase();

                    // Afficher les données dans le TableView
                    afficher();
                    // Calculer le nombre total de pages en fonction du nombre total d'éléments et de la taille de chaque page
                    int pageCount = (int) Math.ceil((double) productSearchModelObservableList.size() / ITEMS_PER_PAGE);

                    // Définir le nombre total de pages dans la pagination
                    pagination.setPageCount(pageCount);

                    // Définir le gestionnaire d'événements pour charger les données de chaque page
                    pagination.setPageFactory(pageIndex -> {
                        int fromIndex = pageIndex * ITEMS_PER_PAGE;
                        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, productSearchModelObservableList.size());
                        ProductTableView.setItems(FXCollections.observableArrayList(productSearchModelObservableList.subList(fromIndex, toIndex)));
                        return ProductTableView;
                    });

                    // Mettre à jour les graphiques
                    setupPieChart();
                    setupBarChart();

                    // Ajouter un écouteur de changement de texte au champ de recherche
                    keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                        // Appeler la méthode searchProduct à chaque fois que le texte du champ de recherche change
                        searchProduct();
                    });

                    btnClearSearch.setOnAction(this::clearSearch);

                    // Afficher les données initiales dans la première page
                    pagination.setCurrentPageIndex(0);

                }


                private void fetchDataFromDatabase() {
                    // Code pour récupérer les données depuis la base de données et les stocker dans la liste 'data'
                }
                private void setupPieChart() {
                    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                    MyConnection connectNow = new MyConnection();
                    Connection connectDB = connectNow.getCnx();

                    try {
                        String productTypeQuery = "SELECT type, COUNT(*) as count FROM produit GROUP BY type";
                        Statement statement = connectDB.createStatement();
                        ResultSet queryOutput = statement.executeQuery(productTypeQuery);
                        while (queryOutput.next()) {
                            String type = queryOutput.getString("type");
                            int count = queryOutput.getInt("count");
                            pieChartData.add(new PieChart.Data(type, count));
                        }
                        productPieChart.setData(pieChartData);
                    } catch (SQLException e) {
                        Logger.getLogger(ProdcutSearchController.class.getName()).log(Level.SEVERE, null, e);
                        e.printStackTrace();
                    }
                }
                private void setupBarChart() {
                    XYChart.Series<String, Integer> series = new XYChart.Series<>();
                    MyConnection connectNow = new MyConnection();
                    Connection connectDB = connectNow.getCnx();

                    try {
                        String productTypeQuery = "SELECT type, SUM(prix) as total_prix FROM produit GROUP BY type";
                        Statement statement = connectDB.createStatement();
                        ResultSet queryOutput = statement.executeQuery(productTypeQuery);
                        while (queryOutput.next()) {
                            String type = queryOutput.getString("type");
                            int totalPrix = queryOutput.getInt("total_prix");
                            series.getData().add(new XYChart.Data<>(type, totalPrix));
                        }
                        productBarChart.getData().clear(); // Supprimer les données existantes pour éviter la duplication
                        productBarChart.getData().add(series);
                    } catch (SQLException e) {
                        Logger.getLogger(ProdcutSearchController.class.getName()).log(Level.SEVERE, null, e);
                        e.printStackTrace();
                    }
                }
                @FXML
                void searchProduct() {
                    String keyword = keywordTextField.getText().trim();
                    if (!keyword.isEmpty()) {
                        // Effacer la liste existante pour afficher les nouveaux résultats
                        productSearchModelObservableList.clear();
                        MyConnection connectNow = new MyConnection();
                        Connection connectDB = connectNow.getCnx();

                        try {
                            // Utiliser une requête paramétrée pour éviter les injections SQL
                            String productSearchQuery = "SELECT * FROM produit WHERE Nom LIKE ?";
                            PreparedStatement searchStatement = connectDB.prepareStatement(productSearchQuery);
                            // Modifier la requête pour rechercher les produits dont le nom commence par la lettre entrée
                            searchStatement.setString(1, keyword + "%");
                            ResultSet queryOutput = searchStatement.executeQuery();
                            while (queryOutput.next()) {
                                Integer queryid = queryOutput.getInt("ID");
                                String querynom = queryOutput.getString("Nom");
                                String querytype = queryOutput.getString("Type");
                                Integer queryprix = queryOutput.getInt("Prix");
                                productSearchModelObservableList.add(new ProductSearchModel(queryid, querynom, querytype, queryprix));
                            }
                            // Mettre à jour la TableView avec les résultats de la recherche
                            ProductTableView.setItems(productSearchModelObservableList);
                        } catch (SQLException e) {
                            Logger.getLogger(ProdcutSearchController.class.getName()).log(Level.SEVERE, null, e);
                            e.printStackTrace();
                        }
                    } else {
                        // Si le champ de recherche est vide, afficher tous les produits
                        afficher();
                    }
                }



                @FXML
                void clearSearch(ActionEvent event) {
                    // Effacer le champ de recherche et afficher tous les produits
                    keywordTextField.clear();
                    afficher();
                }




                private void afficher   () {
                    productSearchModelObservableList.clear();
                    MyConnection connectNow = new MyConnection();
                    Connection connectDB = connectNow.getCnx();

                    try {
                        String productViewQuery = "SELECT * from  produit ";
                        Statement statement = connectDB.createStatement();
                        ResultSet queryOutput = statement.executeQuery(productViewQuery);
                        while (queryOutput.next()) {
                            Integer queryid = queryOutput.getInt("ID");
                            String querynom = queryOutput.getString("Nom");

                            String querytype = queryOutput.getString("Type");
                            Integer queryprix = queryOutput.getInt("Prix");
                            productSearchModelObservableList.add(new ProductSearchModel(queryid, querynom, querytype, queryprix));
                        }

                        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                        productNomTableColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

                        productTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                        productPrixTableColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
                        ProductTableView.setItems(productSearchModelObservableList);
                    } catch (SQLException e) {
                        Logger.getLogger(ProdcutSearchController.class.getName()).log(Level.SEVERE, null, e);
                        e.printStackTrace();
                    }
                }




            }