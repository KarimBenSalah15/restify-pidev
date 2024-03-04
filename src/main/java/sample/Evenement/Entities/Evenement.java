package sample.Evenement.Entities;

import java.sql.Date;

public class Evenement {
    private int id;
    private String nom;
    private Date date;

    private String duree;
    private String etat;
    private String type;
    private int nbrparticipation;

    public Evenement(int id, String nom, Date date, String duree, String etat, String type,int nbrparticipation) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.duree = duree;
        this.etat = etat;
        this.type = type;
        this.nbrparticipation=nbrparticipation;
    }

    public Evenement(String etat, String duree, Date date) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public int getNbrparticipation() {
        return nbrparticipation;
    }

    public void setNbrparticipation(int nbrparticipation) {
        this.nbrparticipation = nbrparticipation;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", duree='" + duree + '\'' +
                ", etat='" + etat + '\'' +
                ", type='" + type + '\'' +
                ", nbrparticipation='" + nbrparticipation + '\'' +
                '}';
    }
}
