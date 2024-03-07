package edu.esprit.services;

import java.util.List;

public interface ICrud <T>{
    public void ajouterEntite(T p);
    public List<T> afficherEntite();

}
