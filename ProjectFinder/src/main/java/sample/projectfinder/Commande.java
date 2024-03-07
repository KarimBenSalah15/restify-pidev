package sample.projectfinder;

import java.util.List;

public class Commande {
    private int id;
    private float total;
    private List<Plat> plats;
    private List<PlatCommande> platCommandes;
    private Boolean traiter;

    public Commande(int id, float total, List<Plat> plats , Boolean traiter) {
        this.id = id;
        this.total = total;
        this.plats = plats;
        this.traiter = traiter;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<Plat> getPlats() {
        return plats;
    }

    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }

    public List<PlatCommande> getPlatCommandes() {
        return platCommandes;
    }

    public void setPlatCommandes(List<PlatCommande> platCommandes) {
        this.platCommandes = platCommandes;
    }
}
