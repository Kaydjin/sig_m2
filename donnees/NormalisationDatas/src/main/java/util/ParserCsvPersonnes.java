package util;

import java.util.ArrayList;
import java.util.List;

import modeles.Batiment;
import modeles.Metier;
import modeles.Personne;
import modeles.TypeBatiment;
import modeles.Ville;

public class ParserCsvPersonnes {

	private final static String[] header_personnes = { "nom", "adresse", "batiment", "metier"};
	
	private Character SEPARATOR = ',';
 
    private List<Batiment> batiments;
    private List<Metier> metiers;
    private List<Personne> personnes;
    
    public ParserCsvPersonnes(Character sep, List<Batiment> batiments,
    		List<Metier> metiers,List<Personne> personnes){
    	this.SEPARATOR = sep;
    	this.metiers = metiers;
    	this.personnes = personnes;
    	this.batiments = batiments;
    }
    
    /**
     * Change java datas in csv lines format and write a new file.
     * @param filename
     */
    public void toCsvFile(String filename) {
    	
    	List<String[]>datas = this.toCsvData();
    	CsvFileHelper.writeFile(filename, datas, Character.toString(SEPARATOR));
    }
    
    /**
     * 
     * @return
     */
    public List<String[]> toCsvData(){
    	List<String[]>datas = new ArrayList<String[]>();
    	datas.add(header_personnes);
    	for(Personne p : personnes) {
    		String[] entry = new String[4];
    		entry[0] = p.getNom();
    		entry[1] = p.getAdresse();
    		entry[2] = batiments.get(p.getId_batiment()).getNom();
    		entry[3] = metiers.get(p.getId_metier()).getNom();
    		datas.add(entry);
    	}
    	return datas;
    }
    
    /**
     * Charge the java data from a csv file.
     * @param filename
     */
    public void fromCsvFile(String filename) {
        
        ICsvFile file = new CsvFileImpl(filename);
        List<String[]>datas = file.getData();
        
        this.fromCsvData(datas);
    }
    
    /**
     * Charge data from csv line format et ajoute des données si elles sont inexistantes
     * dans les listes ajoutés à cette instance à la construction.
     * @param datas
     */
    public void fromCsvData(List<String[]>datas){
        
        for(String[] d : datas){
        	
        	Batiment b = existBatiment(d[2]);
        	
        	//Gestion du batiment : le batiment doit exister avant
        	if(b!=null){
        		
            	//Gestion du type de métier.
            	Metier m = existMetier(d[3]);
            	if(m==null){
            		m = new Metier(d[3]);
            		metiers.add(m);
            	}
            	
            	//Gestion de la personne;
            	Personne p = existPersonne(d[0]);
            	if(p==null){
            		p = new Personne(d[0], d[1], b.getId(), m.getId());
            		personnes.add(p);
            	}            	
        	}
        	
        }
    }
    
    public Metier existMetier(String name){
    	for(Metier m : metiers)
    		if(m.getNom().equals(name))
    			return m;
    	return null;
    }
    
    public Personne existPersonne(String name){
    	for(Personne p : personnes)
    		if(p.getNom().equals(name))
    			return p;
    	return null;
    }
    
    public Batiment existBatiment(String name){
    	for(Batiment b : batiments)
    		if(b.getNom().equals(name))
    			return b;
    	return null;
    }
    
	public List<Batiment> getBatiments() {
		return batiments;
	}
    
    
}
