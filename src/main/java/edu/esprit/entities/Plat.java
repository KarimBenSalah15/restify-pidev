package edu.esprit.entities;

public class Plat {
private  int id;

private  String nom;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "id=" + id +


                ", nom='" + nom + '\'' +

                '}';
    }

    public void setId(int id) {
        this.id = id;
    }





    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public Plat() {
    }

    public Plat(String nomù) {

        this.nom = nom;

    }

    public Plat(int id, String nom) {
        this.id = id;

        this.nom = nom;

    }


}
