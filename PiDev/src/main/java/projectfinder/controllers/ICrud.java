package projectfinder.controllers;

import java.util.List;

public interface ICrud<T>{
    public  void updateProduct(T p, int id);
    public List<T> afficherEntiite();
    public  void deleteProduct(int id);
    public  void creatProduct(T p);
}