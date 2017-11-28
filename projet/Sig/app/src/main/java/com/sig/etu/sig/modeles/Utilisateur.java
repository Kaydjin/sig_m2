package com.sig.etu.sig.modeles;

/**
 * Created by vogel on 23/11/17.
 */

public class Utilisateur {
    private int id;
    private String nom;
    private String mdp;

    public Utilisateur() { }

    public Utilisateur(String nom, String mdp) {
        this.nom = nom;
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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur that = (Utilisateur) o;

        if (id != that.id) return false;
        if (!nom.equals(that.nom)) return false;
        return mdp.equals(that.mdp);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nom.hashCode();
        result = 31 * result + mdp.hashCode();
        return result;
    }
}
