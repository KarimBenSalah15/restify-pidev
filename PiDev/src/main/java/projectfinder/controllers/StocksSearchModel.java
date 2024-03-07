package projectfinder.controllers;

import java.awt.*;

public class StocksSearchModel {


    private Integer id;
    String nom;
    String stockNom;
    String produitNom;

    Integer quantite;

    private final Button buttonPlus;
    private final Button buttonMoins;


    public StocksSearchModel(Integer queryid, String querynom, Integer queryquantite) {
        this.id = queryid;
        this.nom = querynom;


        this.quantite = queryquantite;
        // Initialiser les boutons plus et moins
        this.buttonPlus = new Button("+");
        this.buttonMoins = new Button("-");

    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Integer getQuantite() {
        return quantite;
    }


    public String getStockNom(){return stockNom;}




    public void setid(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(Integer quantite) {
        this.quantite = quantite;
    }


    public void setStockNom(String StockNom){this.stockNom = stockNom;}
    public Button getButtonPlus() {
        return buttonPlus;
    }

    public Button getButtonMoins() {
        return buttonMoins;
    }



}

