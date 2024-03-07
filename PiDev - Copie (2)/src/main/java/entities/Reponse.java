package entities;

import java.sql.Date;

public class Reponse {

  private int id;
  private Reclamation reclamation;

  private String message;
  private Date date;
    private int idA;
    private int idR;

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", idA=" + idA +
                ", idR=" + idR +
                '}';
    }

    public Reponse(int id, String message, Date date, int idA, int idR) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.idA = idA;
        this.idR = idR;
    }

    public Reponse(String message, Date date, int idA, int idR) {
        this.message = message;
        this.date = date;
        this.idA = idA;
        this.idR = idR;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public Reponse() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



}
