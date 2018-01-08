package com.sig.etu.sig.modeles;

public class Personne {

    //Attributes in the bdd.
    private int id;
    private String nom;
    private String adresse;
    private int id_batiment;
    private int id_metier;
    private double latitude;
    private double longitude;

    //Attributes set after the constructor for use in view.
    private String nom_batiment_travail;
    private String nom_metier;

    public Personne(){}
    public Personne(String nom, String adresse, int id_batiment, int id_metier, double latitude,
                    double longitude ) {
        this.nom = nom;
        this.adresse = adresse;
        this.id_batiment = id_batiment;
        this.id_metier = id_metier;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId_batiment() {
        return id_batiment;
    }

    public void setId_batiment(int id_batiment) {
        this.id_batiment = id_batiment;
    }

    public int getId_metier() {
        return id_metier;
    }

    public void setId_metier(int id_metier) {
        this.id_metier = id_metier;
    }

    public String getNom_batiment_travail() {
        return nom_batiment_travail;
    }

    public void setNom_batiment_travail(String nom_batiment_travail) {
        this.nom_batiment_travail = nom_batiment_travail;
    }

    public String getNom_metier() {
        return nom_metier;
    }

    public void setNom_metier(String nom_metier) {
        this.nom_metier = nom_metier;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
        result = 31 * result + id_batiment;
        result = 31 * result + id_metier;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (nom_batiment_travail != null ? nom_batiment_travail.hashCode() : 0);
        result = 31 * result + (nom_metier != null ? nom_metier.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Personne personne = (Personne) o;

        if (id != personne.id) return false;
        if (id_batiment != personne.id_batiment) return false;
        if (id_metier != personne.id_metier) return false;
        if (Double.compare(personne.latitude, latitude) != 0) return false;
        if (Double.compare(personne.longitude, longitude) != 0) return false;
        if (nom != null ? !nom.equals(personne.nom) : personne.nom != null) return false;
        if (adresse != null ? !adresse.equals(personne.adresse) : personne.adresse != null)
            return false;
        if (nom_batiment_travail != null ? !nom_batiment_travail.equals(personne.nom_batiment_travail) : personne.nom_batiment_travail != null)
            return false;
        return nom_metier != null ? nom_metier.equals(personne.nom_metier) : personne.nom_metier == null;
    }
}
