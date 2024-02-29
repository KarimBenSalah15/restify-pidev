package edu.esprit.entities;

import java.sql.Date;

public class Reservation {
    private int id;
    private Date date;
    private String heure;
    private int nbrpersonne;
    private int tabId;  // Foreign key

    public Reservation() {
    }

    public Reservation(int id, Date date, String heure, int nbrpersonne, int tabId) {
        this.id = id;
        this.date = date;
        this.heure = heure;
        this.nbrpersonne = nbrpersonne;
        this.tabId = tabId;
    }

    public Reservation(Date date, String heure, int nbrpersonne, int tabId) {
        this.date = date;
        this.heure = heure;
        this.nbrpersonne = nbrpersonne;
        this.tabId = tabId;
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

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date=" + date +
                ", heure='" + heure + '\'' +
                ", nbrpersonne=" + nbrpersonne +
                ", tabId=" + tabId +
                '}';
    }
}
