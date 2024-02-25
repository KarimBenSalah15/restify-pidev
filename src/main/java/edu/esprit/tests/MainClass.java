package edu.esprit.tests;

import edu.esprit.entities.Plat;
import edu.esprit.services.PlatCrud;
import edu.esprit.tools.MyConnection;

public class MainClass {
    public static void main(String[] args){
        MyConnection mc =  MyConnection.getInstance();
        PlatCrud pcd = new PlatCrud();
       // pcd.ajouterEntite(p);
        System.out.println(pcd.afficherEntite());
    }
}
