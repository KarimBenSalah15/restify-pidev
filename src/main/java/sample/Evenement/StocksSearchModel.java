package sample.Evenement;

public class StocksSearchModel {


    private Integer id;
    String nom;

    Integer quantite;




    public StocksSearchModel(Integer queryid, String querynom, Integer queryquantite) {
        this.id = queryid;
        this.nom = querynom;

        this.quantite = queryquantite;

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





    public void setid(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(Integer quantite) {
        this.quantite = quantite;
    }



}

