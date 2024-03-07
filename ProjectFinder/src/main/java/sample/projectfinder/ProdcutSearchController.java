            package sample.projectfinder;

            import javafx.fxml.FXML;
            import javafx.fxml.Initializable;
            import javafx.scene.control.Button;
            import javafx.scene.control.Pagination;
            import javafx.scene.control.TableView;
            import javafx.scene.control.TableColumn;
            import javafx.collections.FXCollections;
            import javafx.collections.ObservableList;
            import com.google.zxing.BarcodeFormat;
            import com.google.zxing.WriterException;
            import com.google.zxing.client.j2se.MatrixToImageWriter;
            import com.google.zxing.common.BitMatrix;
            import com.google.zxing.oned.Code128Writer;



            import java.awt.image.ImageObserver;
            import java.awt.image.ImageProducer;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.net.MalformedURLException;
            import java.net.URI;
            import java.net.URL;
            import java.util.ResourceBundle;
            import javafx.collections.FXCollections;
            import javafx.collections.ObservableList;
            import javafx.event.ActionEvent;
            import javafx.fxml.FXML;
            import javafx.fxml.Initializable;
            import javafx.scene.chart.BarChart;
            import javafx.scene.chart.CategoryAxis;
            import javafx.scene.chart.NumberAxis;
            import javafx.scene.chart.PieChart;
            import javafx.scene.control.*;
            import javafx.scene.control.cell.PropertyValueFactory;
            import javafx.scene.chart.BarChart;
            import javafx.scene.chart.CategoryAxis;
            import javafx.scene.chart.NumberAxis;
            import javafx.scene.chart.XYChart;
            import java.net.URL;
            import java.sql.*;
            import java.util.ResourceBundle;
            import java.util.logging.Level;
            import java.util.logging.Logger;
            import javafx.fxml.FXMLLoader;
            import javafx.scene.Parent;
            import javafx.scene.Scene;
            import javafx.stage.Modality;
            import javafx.stage.Stage;

            import java.io.File;
            import java.io.IOException;

            import javafx.scene.image.ImageView;
            import javafx.scene.input.MouseEvent;
            import javafx.scene.control.Alert;
            import javafx.scene.control.ButtonType;


            import javafx.stage.FileChooser;
            import org.apache.poi.ss.usermodel.Row;
            import org.apache.poi.ss.usermodel.Sheet;
            import org.apache.poi.ss.usermodel.Workbook;
            import org.apache.poi.xssf.usermodel.XSSFWorkbook;

            import javafx.application.Application;
            import javafx.fxml.FXMLLoader;
            import javafx.scene.Parent;
            import javafx.scene.Scene;
            import javafx.stage.Stage;
            import java.awt.* ;
            import javafx.scene.control.TextField;
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
                private Button btnExport;

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
                private TextField imageTextField;


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
                private TableColumn<ProductSearchModel,String> productImageTableColumn;
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
                void generateBarcode(ActionEvent event) {
                    ProductSearchModel produit = ProductTableView.getSelectionModel().getSelectedItem();
                    if (produit != null) {
                        // Concaténez l'ID, le nom et d'autres informations du produit
                        String productInfo = "id" + produit.getId() + "prix" + produit.getPrix();

                        generateBarcode(productInfo);

                    } else {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un produit.");
                    }
                }

                // Méthode pour générer le code-barres
                private void generateBarcode(String productInfo) {
                    int width = 400;
                    int height = 100;

                    // Utilisez le format Code 128 pour le code-barres
                    Code128Writer writer = new Code128Writer();
                    BitMatrix bitMatrix;
                    try {
                        bitMatrix = writer.encode(productInfo, BarcodeFormat.CODE_128, width, height);

                        // Enregistrez le code-barres généré dans un fichier
                        File file = new File("barcode.png");
                        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", file.toPath());

                        // Débogage : imprime le chemin du fichier du code-barres
                        System.out.println("Chemin du fichier du code-barres : " + file.getAbsolutePath());

                        // Appel de la méthode pour afficher la fenêtre modale avec le code-barres
                        showBarcodeWindow(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Méthode pour afficher une fenêtre modale avec l'image du code-barres généré
                // Déclaration de la méthode pour afficher une fenêtre modale avec l'image du code-barres généré
                private void showBarcodeWindow(File barcodeImageFile) {
                    try {
                        // Chargement du fichier FXML
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("BarcodeWindow.fxml"));
                        Parent root = loader.load();

                        // Obtention du contrôleur de la fenêtre FXML
                        BarcodeWindowController controller = loader.getController();

                        // Passage de l'image du code-barres au contrôleur de la fenêtre
                        controller.setBarcodeImage(barcodeImageFile);

                        // Création de la scène et du stage pour afficher la fenêtre modale
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Code-barres");
                        stage.setScene(new Scene(root));

                        // Affichage de la fenêtre modale et attente de sa fermeture
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                @FXML
                void creatProduct(ActionEvent event) {
                    String nom = nomTextField.getText().trim();
                    String type = typeTextField.getText().trim();
                    String prix = prixTextField.getText().trim();
                    String imageUrl = imageTextField.getText().trim(); // Récupérer l'URL de l'image depuis le TextField

                    // Vérifier que les champs ne sont pas vides
                    if (!nom.isEmpty() && !type.isEmpty() && !prix.isEmpty() && !imageUrl.isEmpty()) {
                        String query = "SELECT * FROM produit WHERE nom = ?";
                        cnx2 = MyConnection.getInstance();
                        try {
                            pst = cnx2.getCnx().prepareStatement(query);
                            pst.setString(1, nom);
                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                showAlert(Alert.AlertType.INFORMATION, "Information", "Le produit existe déjà dans la base de données.");
                            } else {
                                // Insérer le nouveau produit dans la base de données
                                String insertQuery = "INSERT INTO produit(nom, type, prix, image) VALUES (?, ?, ?, ?)";
                                PreparedStatement insertPst = cnx2.getCnx().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                                insertPst.setString(1, nom);
                                insertPst.setString(2, type);
                                insertPst.setInt(3, Integer.parseInt(prix));
                                insertPst.setString(4, imageUrl); // Insérer l'URL de l'image

                                // Exécuter la requête d'insertion
                                insertPst.executeUpdate();

                                // Récupérer l'ID du produit nouvellement inséré
                                ResultSet generatedKeys = insertPst.getGeneratedKeys();
                                int productId = -1; // Initialise à une valeur par défaut
                                if (generatedKeys.next()) {
                                    productId = generatedKeys.getInt(1);
                                }

                                // Maintenant, insérer une nouvelle entrée dans la table stock en utilisant l'ID du produit
                                if (productId != -1) {
                                    String insertStockQuery = "INSERT INTO stock(produit_id, quantite, nom) VALUES (?, ?, ?)";
                                    PreparedStatement insertStockPst = cnx2.getCnx().prepareStatement(insertStockQuery);
                                    insertStockPst.setInt(1, productId);
                                    insertStockPst.setInt(2, 10); // Par exemple, vous pouvez initialiser la quantité à 0
                                    insertStockPst.setString(3, nom);
                                    insertStockPst.executeUpdate();
                                }

                                showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit ajouté avec succès.");

                                afficher();
                            }
                        } catch (NumberFormatException e) {
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir un entier");
                        } catch (SQLException e) {
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout du produit : " + e.getMessage());
                        }
                    } else {
                        // Afficher une alerte si un champ est vide
                        showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez remplir tous les champs.");
                    }
                    setupPieChart();
                    setupBarChart();
                    // Insérer le nouveau produit dans la base de données (code existant)
// Après l'insertion réussie, appelez generateBarcode avec l'ID du nouveau produit
                    int productId =id; // Récupérez l'ID du produit nouvellement inséré depuis la base de données
                    generateBarcode(String.valueOf(productId));

                }



                private void showAlert(Alert.AlertType type, String title, String content) {
                    Alert alert = new Alert(type);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.setContentText(content);
                    alert.showAndWait();
                }


                @FXML
                void exportProduct(ActionEvent event) {
                    try (Workbook workbook = new XSSFWorkbook()) {
                        Sheet sheet = workbook.createSheet("Produits");

                        // Créer l'en-tête
                        Row headerRow = sheet.createRow(0);
                        headerRow.createCell(0).setCellValue("ID");
                        headerRow.createCell(1).setCellValue("Nom");
                        headerRow.createCell(2).setCellValue("Type");
                        headerRow.createCell(3).setCellValue("Prix");

                        // Remplir les données
                        int rowNum = 1;
                        for (ProductSearchModel produit : productSearchModelObservableList) {
                            Row row = sheet.createRow(rowNum++);
                            row.createCell(0).setCellValue(produit.getId());
                            row.createCell(1).setCellValue(produit.getNom());
                            row.createCell(2).setCellValue(produit.getType());
                            row.createCell(3).setCellValue(produit.getPrix());
                        }

                        String fileName = "export.xlsx"; // nom du fichier
                        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                            workbook.write(fileOut);
                            showAlert(Alert.AlertType.INFORMATION, "Succès", "Export Excel réussi !");
                            openFile(fileName); // passer le nom du fichier à la méthode openFile
                        } catch (IOException e) {
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'export Excel : " + e.getMessage());
                        } finally {
                            try {
                                workbook.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }



                private static void openFile(String fileName) {
                    try {
                        File file = new File(fileName);

                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                            Desktop.getDesktop().open(file);
                        } else {
                            System.out.println("Impossible d'ouvrir le fichier. Veuillez le faire manuellement.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("Erreur lors de l'ouverture du fichier Excel.");
                    }
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
                private Button btnImportImage;

                @FXML
                void importImage(ActionEvent event) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Sélectionner une image");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Fichiers d'images", "*.png", "*.jpg", "*.gif")
                    );
                    File selectedFile = fileChooser.showOpenDialog(null);
                    if (selectedFile != null) {
                        // Mettre à jour le TextField avec l'URL de l'image sélectionnée
                        imageTextField.setText(selectedFile.toURI().toString());
                    }
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
                                String queryimage= queryOutput.getString("image");
                                productSearchModelObservableList.add(new ProductSearchModel(queryid, querynom, querytype, queryprix,queryimage));
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
                            String queryimage = queryOutput.getString("image");
                            productSearchModelObservableList.add(new ProductSearchModel(queryid, querynom, querytype, queryprix,queryimage));
                        }

                        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                        productNomTableColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

                        productTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                        productPrixTableColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
                        productImageTableColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
                        ProductTableView.setItems(productSearchModelObservableList);
                    } catch (SQLException e) {
                        Logger.getLogger(ProdcutSearchController.class.getName()).log(Level.SEVERE, null, e);
                        e.printStackTrace();
                    }
                }




            }