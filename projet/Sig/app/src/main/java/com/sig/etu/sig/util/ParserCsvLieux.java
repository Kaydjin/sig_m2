package com.sig.etu.sig.util;

import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.TypeBatiment;
import com.sig.etu.sig.modeles.Ville;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParserCsvLieux {

	private final static String[] header = { "intitule", "type", "adresse", "codepostal", 
		"ville", "telephone", "latitude", "longitude"
	};
	private Character SEPARATOR = ',';
 
    private List<Batiment> batiments;
    private List<Ville> villes;
    private List<TypeBatiment> typesBatiments;
    
    public ParserCsvLieux(Character sep, List<Batiment> batiments,
    		List<Ville> villes,List<TypeBatiment> typesBatiments){
    	this.SEPARATOR = sep;
    	this.batiments = batiments;
    	this.villes = villes;
    	this.typesBatiments = typesBatiments;
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
    	datas.add(header);
    	for(Batiment b : batiments) {
    		String[] entry = new String[8];
    		entry[0] = b.getNom();
    		entry[1] = typesBatiments.get(b.getId_type()-1).getType();
    		entry[2] = b.getAdresse().replace("\"", "");
    		entry[3] = villes.get(b.getId_ville()-1).getCode_postale();
    		entry[4] = villes.get(b.getId_ville()-1).getNom();
    		entry[5] = b.getTelephone();
    		entry[6] = ""+b.getLatitude();
    		entry[7] = ""+b.getLongitude();
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
	 * Charge the java data from a csv file input stream.
	 */
	public void fromCsvFileInputStream(InputStreamReader input) {

		ICsvFile file = new CsvFileImpl(input);
		List<String[]>datas = file.getData();

		this.fromCsvData(datas);
	}

	/**
	 * Charge the java data from a csv file.
	 * @param filename
	 */
	public void fromCsvFile(File filename) {

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
        	
        	//Gestion de la ville.
        	Ville interville = existVille(d[4]);
        	if(interville==null){
        		if(d[3].length()==4)
        			interville = new Ville(d[3].substring(0, 1), d[4]);
        		else
					if(d[3].length()==1)
        				interville = new Ville(d[3].substring(0, 1), d[4]);
        			else
						interville = new Ville(d[3].substring(0, 2), d[4]);

        		villes.add(interville);
        	}
        	
        	//Gestion du type de batiment.
        	TypeBatiment intertb = existTypeBatiment(d[1]);
        	if(intertb==null){
        		intertb = new TypeBatiment(d[1], "no description");
        		typesBatiments.add(intertb);
        	}
        	
        	//Gestion du batiment.
        	Batiment b = existBatiment(d[0]);
        	if(b==null){
				//Ici 0 0 car on ne sait pas encore les id correspondants.
        		b = new Batiment(0,0,
        			Double.valueOf(d[6]), Double.valueOf(d[7]), d[0], d[2], d[5]);
				b.setType(intertb.getType());
				b.setVille(interville.getNom());
        		batiments.add(b);
        	}
        	
        }
    }
    
    public TypeBatiment existTypeBatiment(String name){
    	for(TypeBatiment tb : typesBatiments)
    		if(tb.getType().equals(name))
    			return tb;
    	return null;
    }
    
    public Ville existVille(String name){
    	for(Ville v : villes)
    		if(v.getNom().equals(name))
    			return v;
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

	public List<Ville> getVilles() {
		return villes;
	}

	public List<TypeBatiment> getTypesBatiments() {
		return typesBatiments;
	}
    
    
}
