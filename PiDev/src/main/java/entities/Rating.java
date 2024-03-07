package entities;

public class Rating {
    private int id;
    private int id_event;
    private int etoiles;

    public Rating(int id, int id_event, int etoiles) {
        this.id = id;
        this.id_event = id_event;
        this.etoiles = etoiles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getEtoiles() {
        return etoiles;
    }

    public void setEtoiles(int etoiles) {
        this.etoiles = etoiles;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", id_event=" + id_event +
                ", etoiles=" + etoiles +
                '}';
    }
}
