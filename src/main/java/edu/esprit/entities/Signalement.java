package edu.esprit.entities;

import java.sql.Date;

public class Signalement {

  private int id;
  private String message;
  private Date date;

    public Signalement() {
    }

    public Signalement(int id, String message, Date date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Signalement{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
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

    public Signalement(String message, Date date) {
        this.message = message;
        this.date = date;
    }

}
