package sample.projectfinder;

public class ProduitCommande {
    private int id;
    private ProductSearchModel productSearchModel;
    private int quantite;

    public ProduitCommande(ProductSearchModel productSearchModel, int quantite) {
        this.productSearchModel = productSearchModel;
        this.quantite = quantite;
    }

    public ProduitCommande(int id, ProductSearchModel productSearchModel, int quantite) {
        this.id = id;
        this.productSearchModel = productSearchModel;
        this.quantite = quantite;
    }

    public ProductSearchModel getProductSearchModel() {
        return productSearchModel;
    }

    public void setProductSearchModel(ProductSearchModel productSearchModel) {
        this.productSearchModel = productSearchModel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }


    public int getQuantite() {
        return quantite;
    }
}
