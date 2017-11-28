package com.sig.etu.sig.modeles;

public class Metier {

    private int id;
    private String nom; //Avocat, notaire, huisser.

    public Metier(){}
    public Metier(String nom) {
        this.nom = nom;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metier metier = (Metier) o;

        if (id != metier.id) return false;
        return nom.equals(metier.nom);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nom.hashCode();
        return result;
    }
}
