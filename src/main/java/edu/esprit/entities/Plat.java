package edu.esprit.entities;

import java.sql.Blob;

public class Plat {
    private int id;
    private String nom;
    private float prix;

    public Plat(int id, String nom, float prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    public Plat(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    private String ingredients;
    private int calories;

    public Plat(String upperCase) {
    }

    public static Plat valueOf(String platname) {
        return new Plat(platname.toUpperCase()); // Create a new instance with the provided platname
    }


    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    private Blob image;

    public Plat(int id, String nom, float prix, String ingredients, int calories, Blob image) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.ingredients = ingredients;
        this.calories = calories;
this.image=image;
    }

    public Plat(int id, String nom, float prix, String ingredients, int calories) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.ingredients = ingredients;
        this.calories = calories;
    }

    public Plat() {
    }

    public Plat(String nom, float prix, String ingredients, int calories, Blob image) {
        this.nom = nom;
        this.prix = prix;
        this.ingredients = ingredients;
        this.calories = calories;
        this.image=image;

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
