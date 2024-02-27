package sample.projectfinder;

public class ProductSearchModel {
  private Integer id;
  String nom,type;

  Integer prix;




    public ProductSearchModel(Integer queryid, String querynom, String querytype, Integer queryprix) {
        this.id = queryid;
        this.nom = querynom;

        this.type = querytype;
        this.prix = queryprix;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }



    public Integer getPrix() {
        return prix;
    }

    public void setid(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }



    public void setPrix(Integer prix) {
        this.prix = prix;
    }
}
