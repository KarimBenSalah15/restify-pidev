package entities;


import java.sql.Date;

public class Reclamation {
    private int id;
    private Date date;
    private Boolean etat;
    private String type;
    private String message;

    private int userId;
    private int platId;

    public Reclamation(Date date, Boolean etat, String message, int userId, int platId) {
        this.date = date;
        this.etat = etat;
        this.message = message;
        this.userId = userId;
        this.platId = platId;
    }

    public Reclamation(Date date, Boolean etat, String message, int platId) {
        this.date = date;
        this.etat = etat;
        this.message = message;
        this.platId = platId;
    }

    public Reclamation(Boolean etat, Date date, String type, String message, int userId, int platId) {
        this.date = date;
        this.etat = etat;
        this.type = type;
        this.message = message;
        this.userId = userId;
        this.platId = platId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlatId() {
        return platId;
    }

    public void setPlatId(int platId) {
        this.platId = platId;
    }

    public Reclamation(Date date, Boolean etat, String type, String message, int user, int plt) {
        this.date = date;
        this.etat = etat;
        this.type = type;
        this.message =message;

        this.userId=user;
        this.platId=plt;



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
        // Customize the string representation as needed, handling null etat
        String etatString = (this.etat != null && this.etat) ? "réclamation traitée" : "réclamation non traitée";
        return "Date: [" + this.date + "]  Message:[" + this.message + "]  Type:[" + this.type + "]  Etat:[" + etatString + "]";
    }

}

