package projectfinder.controllers;

import java.util.List;

public class Commande2 {
    private int id;
    private float prix;
    private List<ProductSearchModel> produits;
    private List<ProduitCommande> produitCommandes;
    private Boolean traiter;

    public List<ProductSearchModel> getProduits() {
        return produits;
    }

    public void setProduits(List<ProductSearchModel> produits) {
        this.produits = produits;
    }

    public Boolean isTraiter() {
        return traiter;
    }

    public void setTraiter(Boolean traiter) {
        this.traiter = traiter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Commande2(int id, float prix, List<ProductSearchModel> produits, Boolean traiter) {
        this.id = id;
        this.prix = prix;
        this.produits = produits;
        this.traiter = traiter;
    }

    public Commande2(int totalQuantity, float totalPrice, List<ProductSearchModel> products, boolean b) {
        this.prix=totalPrice;

        this.traiter=b;
    }
}
