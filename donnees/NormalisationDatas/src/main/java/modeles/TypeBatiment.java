package modeles;

public class TypeBatiment {
    public enum Tribunaux {INSTANCE, GRANDE_INSTANCE,ENFANT,GREFFE;}
    private int id;
    private String type;
    private String description;
    public static int nbr=0;
    
    public TypeBatiment(){}
    public TypeBatiment(String type, String description) {
    	this.id = nbr++;
        this.type = type;
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeBatiment typeBatiment1 = (TypeBatiment) o;

        if (id != typeBatiment1.id) return false;
        if (!type.equals(typeBatiment1.type)) return false;
        return description.equals(typeBatiment1.description);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
