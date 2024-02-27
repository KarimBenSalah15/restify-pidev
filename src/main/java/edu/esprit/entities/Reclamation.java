package edu.esprit.entities;


import java.sql.Date;

public class Reclamation {
    private int id;
    private Date date;
    private Boolean etat;
    private String type;
    private String message;
    private int 	idRO;


    public Reclamation(Date date, Boolean etat, String type,String message,int isp) {
        this.date = date;
        this.etat = etat;
        this.type = type;
        this.message =message;
        this.idRO=isp;
    }
    public Reclamation(Date date, Boolean etat, String type,String message) {
        this.date = date;
        this.etat = etat;
        this.type = type;
        this.message =message;

    }

    public Reclamation() {
    }

    public Reclamation(int id, Date date, Boolean etat, String type, String message) {
        this.id = id;
        this.date = date;
        this.etat = etat;
        this.type = type;
        this.message = message;
    }

    public int getIdRO() {
        return idRO;
    }

    public void setIdRO(int idRO) {
        this.idRO = idRO;
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

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", date=" + date +
                ", etat=" + etat +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

