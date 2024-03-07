package projectfinder.controllers;

public class ProductSearchModel {
  private Integer id;
  String nom,type;

  Integer prix;
  String image;




    public ProductSearchModel(Integer queryid, String querynom, String querytype, Integer queryprix,String image) {
        this.id = queryid;
        this.nom = querynom;

        this.type = querytype;
        this.prix = queryprix;
        this.image = image;
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
    public String getImage() {return image;}

    public ProductSearchModel(String nom, Integer prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setid(Integer id) {
        this.id = id;
    }
    public void setImage(String image) {this.image =image;}

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
