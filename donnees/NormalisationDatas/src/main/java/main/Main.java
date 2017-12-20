package main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modeles.Batiment;
import modeles.TypeBatiment;
import modeles.Ville;
import util.ParserCsvLieux;
import util.ParserJson;

public class Main {

	public static void main(String[] args) {
		
        //Instanciation des types de batiments de bases si il n'existe pas.
        List<TypeBatiment> typesBatiments = new ArrayList<TypeBatiment>();
        
        typesBatiments.add(new TypeBatiment(TypeBatiment.Tribunaux.ENFANT.toString(), 
	        		"Tribunaux pour enfants"));
        typesBatiments.add(new TypeBatiment(TypeBatiment.Tribunaux.GRANDE_INSTANCE.toString(), 
	        		"Tribunaux de grand instances"));
        typesBatiments.add(new TypeBatiment(TypeBatiment.Tribunaux.INSTANCE.toString(), 
	        		"Tribunaux d'instances"));
        typesBatiments.add(new TypeBatiment(TypeBatiment.Tribunaux.GREFFE.toString(), 
	        		"Greffes"));
        
        List<Batiment> batiments = new ArrayList<Batiment>();
        List<Ville> villes = new ArrayList<Ville>();
        
		ParserCsvLieux p = new ParserCsvLieux(',',batiments, villes, typesBatiments);
		
		p.fromCsvFile("lieux.csv");
		
		List<Batiment> b = p.getBatiments();
		for(Batiment bat : b){}
			//System.out.println(bat.getLatitude());
		
		JSONObject obj = ParserJson.parseLieuxTo(p.toCsvData());
		
		JSONArray arr;
		try {
			arr = obj.getJSONArray("lieux");
			for (int i = 0; i < arr.length(); i++)
			{
				//System.out.println(arr.getJSONObject(i).getString("longitude"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
        batiments = new ArrayList<Batiment>();
		ParserCsvLieux p2 = new ParserCsvLieux(',',batiments, villes, typesBatiments);
		
		p2.fromCsvData(ParserJson.parseLieuxFrom(obj));
		
		b = p2.getBatiments();
		for(Batiment bat : b){}
			//System.out.println(bat.getId_type());
		
		p2.toCsvFile("lieux2.csv");
	}

}
