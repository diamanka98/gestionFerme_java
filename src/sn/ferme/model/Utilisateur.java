package sn.ferme.model;

import java.text.DecimalFormat;
import sn.ferme.event.ActionEvenenement;

/**
 *
 * @author hp
 */
public class Utilisateur {

    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String photo;
    private String email;
    private String password;
    private String profile;
    private boolean isAdmin;

    private int salire;
    
    private int idCom;
    private int capacite;
    private String dateCom;

    public int getSalire() {
        return salire;
    }

    public Utilisateur(int idUtilisateur, int salire) {
        this.idUtilisateur = idUtilisateur;
        this.salire = salire;
    }
    
    

    public void setSalire(int salire) {
        this.salire = salire;
    }

    public Utilisateur(int idUtilisateur, String nom, String prenom, String telephone, String adresse, String photo, String email, String password, String profile, boolean isAdmin, int idCom, int capacite, String dateCom) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.photo = photo;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.isAdmin = isAdmin;
        this.idCom = idCom;
        this.capacite = capacite;
        this.dateCom = dateCom;
    }

    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getDateCom() {
        return dateCom;
    }

    public void setDateCom(String dateCom) {
        this.dateCom = dateCom;
    }
    
    public Utilisateur(int idUtilisateur, String nom, String prenom, String telephone, String adresse, String email, String password, String profile, boolean isAdmin) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.isAdmin = isAdmin;
    }

    public Utilisateur(int idUtilisateur, String nom, String prenom, String telephone, String adresse, String email, String profile, boolean isAdmin) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.email = email;
        this.profile = profile;
        this.isAdmin = isAdmin;
    }

    public Utilisateur() {
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
  public Object[] toRowTable(ActionEvenenement event) {
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        return new Object[]{new Utilisateur(idUtilisateur,nom, prenom, telephone, adresse, email, profile, isAdmin), new ModelAction(this, event)};
    }
}
