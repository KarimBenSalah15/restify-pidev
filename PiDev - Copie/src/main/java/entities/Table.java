package entities;

import java.sql.Date;

public class Table {
    private  int idtable;
    private int nbrplace;
    private  String place;
    private  boolean dispo;

    public Table(int idtable, int nbrplace, String place, boolean dispo) {
        this.idtable = idtable;
        this.nbrplace = nbrplace;
        this.place = place;
        this.dispo = dispo;
    }

    public Table(int nbrplace, String place, boolean dispo) {
        this.nbrplace = nbrplace;
        this.place = place;
        this.dispo = dispo;
    }

    public Table() {
    }

    public int getIdtable() {
        return idtable;
    }

    public void setIdtable(int idtable) {
        this.idtable = idtable;
    }

    public int getNbrplace() {
        return nbrplace;
    }

    public void setNbrplace(int nbrplace) {
        this.nbrplace = nbrplace;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    @Override
    public String toString() {
        return "Table{" +
                "idtable=" + idtable +
                ", nbrplace=" + nbrplace +
                ", place='" + place + '\'' +
                ", dispo=" + dispo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        return idtable == table.idtable;
    }

    @Override
    public int hashCode() {
        return idtable;
    }


    public int getTabId() {
        return idtable;
    };
}

