package services;

import java.util.List;

public interface ICrud <T>{
    public void ajouterEntite(T p);
    public List<T> afficherEntite();
    public void supprimerEntite(int id);
    public void modifierEntite(T p, T p1);
}
