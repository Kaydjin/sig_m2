package modeles;

public class Personne {

    //Attributes in the bdd.
    private int id;
    private String nom;
    private String adresse;
    private int id_batiment;
    private int id_metier;
    public static int nbr=0;

    //Attributes set after the constructor for use in view.
    private String nom_batiment_travail;
    private String nom_metier;

    public Personne(){}
    public Personne(String nom, String adresse, int id_batiment, int id_metier) {
    	this.id = nbr++;
        this.nom = nom;
        this.adresse = adresse;
        this.id_batiment = id_batiment;
        this.id_metier = id_metier;
    }
    public void reset(){
    	this.nbr=0;
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

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nom.hashCode();
        result = 31 * result + adresse.hashCode();
        result = 31 * result + id_batiment;
        result = 31 * result + id_metier;
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
        if (!nom.equals(personne.nom)) return false;
        return adresse.equals(personne.adresse);

    }
}
