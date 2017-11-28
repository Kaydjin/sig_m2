package com.sig.etu.sig.modeles;

public class Batiment {

    //Attributes for batiment in the bdd
    private int id;
    private int id_type;
    private int id_ville;
    private double latitude;
    private double longitude;
    private String nom;
    private String adresse;
    private String telephone;

    //Attributes use in a view, set after the constructor.
    private String type;
    private String ville;

    public Batiment(){}

    public Batiment(int id_type, int id_ville, double latitude, double longitude,
                    String nom, String adresse, String telephone) {
        this.id_type = id_type;
        this.id_ville = id_ville;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public int getId_ville() {
        return id_ville;
    }

    public void setId_ville(int id_ville) {
        this.id_ville = id_ville;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Batiment batiment = (Batiment) o;

        if (id != batiment.id) return false;
        if (id_type != batiment.id_type) return false;
        if (id_ville != batiment.id_ville) return false;
        if (Double.compare(batiment.latitude, latitude) != 0) return false;
        if (Double.compare(batiment.longitude, longitude) != 0) return false;
        if (!nom.equals(batiment.nom)) return false;
        if (!adresse.equals(batiment.adresse)) return false;
        return telephone.equals(batiment.telephone);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + id_type;
        result = 31 * result + id_ville;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + nom.hashCode();
        result = 31 * result + adresse.hashCode();
        result = 31 * result + telephone.hashCode();
        return result;
    }
}
