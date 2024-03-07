package controllers;

import java.sql.Blob;

public class Plat {
    private int id;
    private String nom;
    private float prix;
    private String ingredients;
    private int calories;
    private Blob image;

    public Plat() {
    }

    public Plat(String nom, float prix, String ingredients, int calories, Blob image) {
        this.nom = nom;
        this.prix = prix;
        this.ingredients = ingredients;
        this.calories = calories;
        this.image = image;
    }

    public Plat(int id, String nom, float prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    public Plat(int id, String nom, float prix, String ingredients, int calories, Blob image) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.ingredients = ingredients;
        this.calories = calories;
        this.image = image;
    }

    public int getId() {
        return id;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", ingredients='" + ingredients + '\'' +
                ", calories=" + calories +
                '}';
    }
}
