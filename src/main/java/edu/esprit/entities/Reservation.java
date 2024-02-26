package edu.esprit.entities;

import java.sql.Date;

public class Reservation {
    private  int id;
    private Date date;
    private  String heure;
    private  int nbrpersonne;

    public Reservation() {
    }

    public Reservation(int id, Date date, String heure, int nbrpersonne) {
        this.id = id;
        this.date = date;
        this.heure = heure;
        this.nbrpersonne = nbrpersonne;
    }

    public Reservation(Date date, String heure, int nbrpersonne) {
        this.date = date;
        this.heure = heure;
        this.nbrpersonne = nbrpersonne;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date=" + date +
                ", heure='" + heure + '\'' +
                ", nbrpersonne=" + nbrpersonne +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getNbrpersonne() {
        return nbrpersonne;
    }

    public void setNbrpersonne(int nbrpersonne) {
        this.nbrpersonne = nbrpersonne;
    }
}
