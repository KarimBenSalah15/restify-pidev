package projectfinder.controllers;

public class PlatCommande {
    private int id;
    private Plat plat;
    private int quantite;

    public PlatCommande(Plat plat, int quantite) {
        this.plat = plat;
        this.quantite = quantite;
    }

    public PlatCommande(int id, Plat plat, int quantite) {
        this.id = id;
        this.plat = plat;
        this.quantite = quantite;
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

    public Plat getPlat() {
        return plat;
    }

    public int getQuantite() {
        return quantite;
    }
}
