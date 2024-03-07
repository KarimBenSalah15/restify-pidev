package entities;

import java.sql.Date;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int tel;
    private String login;
    private String mdp;
    private String role;
    private String poste;
    private int salaire;
    private Date dateembauche;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String prenom, String email, int tel, String login, String role, String poste, int salaire, Date dateembauche) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.login = login;
        this.role = role;
        this.poste = poste;
        this.salaire = salaire;
        this.dateembauche = dateembauche;
    }

    public Utilisateur(String nom, String prenom, String email, int tel, String login, String mdp, String poste, int salaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.login = login;
        this.mdp = mdp;
        this.poste = poste;
        this.salaire = salaire;
    }

    public Utilisateur(String nom, String prenom, String email, int tel, String login, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.login = login;
        this.mdp = mdp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public int getSalaire() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire = salaire;
    }

    public Date getDateembauche() {
        return dateembauche;
    }

    public void setDateembauche(Date date) {
        this.dateembauche = date;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", tel=" + tel +
                ", login='" + login + '\'' +
                ", mdp='" + mdp + '\'' +
                ", role=" + role +
                ", poste='" + poste + '\'' +
                ", salaire=" + salaire +
                ", dateembauche=" + dateembauche +
                '}';
    }
}
