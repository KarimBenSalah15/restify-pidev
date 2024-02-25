package edu.esprit.entities;

import java.sql.Date;

public class Avi {
    private int id;
    private int nbretoile;
    private Date date;
    private String message ;

    public Avi(int id, int nbretoile, Date date, String message) {
        this.id = id;
        this.nbretoile = nbretoile;
        this.date = date;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Avi{" +
                "id=" + id +
                ", nbretoile=" + nbretoile +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbretoile() {
        return nbretoile;
    }

    public void setNbretoile(int nbretoile) {
        this.nbretoile = nbretoile;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Avi(int nbretoile, Date date, String message) {
        this.nbretoile = nbretoile;
        this.date = date;
        this.message = message;
    }

    public Avi() {

    }
}
