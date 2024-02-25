package edu.esprit.services;

import java.util.List;

public interface ICrud <T>{
    public  void modifierEntite(T p, int id);
    public List<T> afficherEntiite();
    public  void supprimerEntite(int id);
    public  void ajouterEntite(T p);
}
