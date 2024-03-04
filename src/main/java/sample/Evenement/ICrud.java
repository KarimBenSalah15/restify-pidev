package sample.Evenement;

import java.sql.ResultSet;
import java.util.List;

public interface ICrud<T>{
    public  void update(T p, int id);
    public List<T> getAll();
    public  void delete(int id);
    public  void create(T p);

    public T mapTableToObject(ResultSet row);
}






















/**package sample.Evenement;

import sample.Evenement.Entities.Participant;

import java.sql.ResultSet;
import java.util.List;

public interface ICrud<T>{
    public  void updateProduct(T p, int id);
    public List<T> afficherEntiite();
    public  void deleteProduct(int id);
    public  void creatProduct(T p);

    void update(Participant p, int id);

    List<Participant> getAll();

    void delete(int id);

    void create(Participant p);

    Participant mapTableToObject(ResultSet resultSet);
}*/