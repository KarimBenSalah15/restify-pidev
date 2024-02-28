package edu.esprit.entities;

import edu.esprit.entities.Plat;

public class PlatCommande {
    private Plat plat;
    private int quantite;

    public PlatCommande(Plat plat, int quantite) {
        this.plat = plat;
        this.quantite = quantite;
    }

    public  void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Plat getPlat() {
        return plat;
    }

    public int getQuantite() {
        return quantite;
    }
}
