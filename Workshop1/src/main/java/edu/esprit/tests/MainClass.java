package edu.esprit.tests;

import edu.esprit.entities.Personne;
import edu.esprit.services.PersonneCrud;
import edu.esprit.tools.MyConnection;

public class MainClass {
    public static void main(String[] args) {
        //MyConnection mc = new MyConnection();
        Personne p = new Personne("Ben Ezzeddine", "Selim");
        PersonneCrud pcd = new PersonneCrud();
        //pcd.ajouterEntite2(p);
        System.out.println(pcd.afficherEntite());
    }
}
