package modeles;

public class Ville {
    private int id;
    private String code_postale;
    private String nom;
    public static int nbr=0;
    
    public Ville(){}
    public Ville(String code_postale, String nom) {
    	this.id = nbr++;
        this.code_postale = code_postale;
        this.nom = nom;
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

    public String getCode_postale() {
        return code_postale;
    }

    public void setCode_postale(String code_postale) {
        this.code_postale = code_postale;
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

        Ville ville = (Ville) o;

        if (id != ville.id) return false;
        if (!code_postale.equals(ville.code_postale)) return false;
        return nom.equals(ville.nom);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + code_postale.hashCode();
        result = 31 * result + nom.hashCode();
        return result;
    }
}
