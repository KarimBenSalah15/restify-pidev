package projectfinder.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    public static ShoppingCart instance ;
    public static  ShoppingCart getInstance(){
        if (instance==null){
            instance = new ShoppingCart();
        }
        return instance;
    }
    private Map<String , CardEntry> entries;

    public ShoppingCart(){
        this.entries = new HashMap<>();
    }

    public void addPlat(Plat plat) {
        String platName = plat.getNom();
        CardEntry platEntry = entries.get(platName.toUpperCase());
        if (platEntry != null) {
            platEntry.increaseQuantity();
        } else {
            CardEntry entry = new CardEntry(plat, 1);
            entries.put(platName.toUpperCase(), entry);
        }
    }



    public void removePlat(String platName){
        CardEntry prodcutEntry = entries.get(platName.toUpperCase());
        if (prodcutEntry!=null){
            prodcutEntry.decreaseQuantity();
        }
    }

    public int getQuantity(String platName){
        CardEntry entry = entries.get(platName.toUpperCase());
        if (entry!=null) {
            return entry.getQuantity();
        }
        return 0;

    }

    public float calculTotal(){
        float total = 0;
        for (CardEntry entry:entries.values()){
            float entryCost = entry.getPlat().getPrix()*entry.getQuantity();
            total = total + entryCost;
        }

        return total;
    }

    public List<CardEntry> getEntries(){
        return new ArrayList<>(entries.values());
    }

}
