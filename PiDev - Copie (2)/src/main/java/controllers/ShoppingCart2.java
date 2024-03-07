package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart2 {
    private Map<String, CardEntry2> entries2;

    private ShoppingCart2() {
        this.entries2 = new HashMap<>();
    }

    private static ShoppingCart2 instance;

    public static ShoppingCart2 getInstance() {
        if (instance == null) {
            instance = new ShoppingCart2();
        }
        return instance;
    }

    public void addProduit(ProductSearchModel produitCommande) {
        String productName = produitCommande.getNom(); // Accessing name directly
        CardEntry2 productEntry = entries2.get(productName.toUpperCase());
        if (productEntry != null) {
            productEntry.increaseQuantity();
        } else {
            CardEntry2 entry = new CardEntry2(produitCommande, 1);
            entries2.put(productName.toUpperCase(), entry);
        }
    }

    public void removeProduit(String productName) {
        CardEntry2 productEntry = entries2.get(productName.toUpperCase());
        if (productEntry != null) {
            productEntry.decreaseQuantity();
        }
    }

    public List<CardEntry2> getEntries2() {
        return new ArrayList<>(entries2.values());
    }

    public float calculTotal() {
        float total = 0;
        for (CardEntry2 entry : entries2.values()) {
            float entryCost = entry.getProductSearchModel().getPrix() * entry.getQuantity();
            total += entryCost;
        }
        return total;
    }

    public int getQuantity2(String productName) {
        CardEntry2 entry = entries2.get(productName.toUpperCase());
        return entry != null ? entry.getQuantity() : 0;
    }
}
