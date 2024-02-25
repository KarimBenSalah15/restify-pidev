package edu.esprit.tests;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ReclamtionCrud;
import edu.esprit.tools.MyConnection;

import java.sql.Date;
import java.util.Calendar;

public class MainClass {
    public static void main (String[] args){

        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(calendar.getTime().getTime());

        Reclamation r = new Reclamation(currentDate,false,"plats","oooo");
        ReclamtionCrud rc=new ReclamtionCrud();
     //  rc.ajouterEntite(r);
     // System.out.println(rc.afficherEntiite());
        Reclamation r2 = new Reclamation(currentDate,true,"fgg","ok");
       // rc.supprimerEntite(2);
     rc.modifierEntite(r2,3);
    }
}
