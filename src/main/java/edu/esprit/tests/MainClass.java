package edu.esprit.tests;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Table;
import edu.esprit.services.ReservationCrud;
import edu.esprit.services.TableCrud;

import java.sql.Date;
import java.util.Calendar;

public class MainClass {
    public static void main (String[] args){

        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(calendar.getTime().getTime());

        Reservation r = new Reservation(currentDate,"20:15",5);
        ReservationCrud rc=new ReservationCrud();
        rc.ajouterEntite(r);
        System.out.println(rc.afficherEntiite());
        rc.supprimerEntite(4);
        rc.modifierEntite(r,2);

        Table t = new Table(5,"normale",true);
        TableCrud tc=new TableCrud();
        tc.ajouterEntite(t);
        System.out.println(tc.afficherEntiite());
        tc.supprimerEntite(3);
        tc.modifierEntite(t,2);
}
}


