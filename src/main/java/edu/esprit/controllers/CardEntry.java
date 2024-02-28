package edu.esprit.controllers;

import edu.esprit.entities.Plat;

public class CardEntry {
    private Plat plat ;
    private int quantity;

    public CardEntry(Plat plat, int quantity) {
        this.plat = plat;
        this.quantity = quantity;
    }



    public  Plat getPlat() {
        return plat;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(){
        this.quantity++;
    }
    public void decreaseQuantity(){
        if (this.quantity>0){
            this.quantity--;
        }

    }
}
