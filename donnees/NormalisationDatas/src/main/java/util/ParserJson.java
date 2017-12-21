package util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserJson {

	private final static String[] header_lieux = { "intitule", "type", "adresse", "codepostal", 
			"ville", "telephone", "latitude", "longitude"
	};
	
	private final static String[] header_personnes = { "nom", "adresse", "batiment", "metier"};
	
	/**
	 * Parse from a csv lines format to a usable json string format.
	 * @param datas
	 * @return
	 */
	public static String parseToString(String type, 
			List<String[]> datas, String[] header, boolean ignore_header){
		String res = " { \""+type+"\": [";
		int i;
		int j=0;
		
		if(ignore_header)
			datas.remove(0);
			
		for(String[] d : datas){
			res += "{";
			i=0;
			for(String s : d){
				res += "\""+ header[i++] + "\":" + "\"" + s + "\"";
				
				//Le dernier élément ne doit pas comprendre de virgule;
				if(i!=header.length)
					res += ",";
			}
			res += "}";
			
			//Le dernier élément ne doit pas comprendre de virgule;
			if(j++!=datas.size()-1)
				res += ",";
		}
		res += "]}";
		
		return res;
	}
	
	public static JSONObject parseLieuxTo(List<String[]> datas){
		return ParserJson.parseTo(datas,"lieux", header_lieux, true);
	}
	
	public static JSONObject parsePersonneTo(List<String[]> datas){
		return ParserJson.parseTo(datas,"personnes", header_personnes, true);
	}
	
	private static JSONObject parseTo(List<String[]> datas, 
			String type, String[] header, boolean erase_header){
		JSONObject obj=null;
		try {
			obj = new JSONObject(ParserJson.parseToString(type, datas, header, erase_header));
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return obj;		
	}
	
	public static List<String[]> parseLieuxFrom(JSONObject object){
		return ParserJson.parseFrom(object, "lieux", header_lieux);
	}
	
	public static List<String[]> parsePersonnesFrom(JSONObject object){
		return ParserJson.parseFrom(object, "personnes", header_personnes);
	}
	
	private static List<String[]> parseFrom(JSONObject object, String type, String[] header){
		List<String[]> datas = new ArrayList<String[]>();
		
		JSONArray arr;
		try {
			arr = object.getJSONArray(type);
			for (int i = 0; i < arr.length(); i++)
			{
				
				String[] inter = new String[header.length];
				for(int j=0; j<header.length; j++){
					inter[j] = arr.getJSONObject(i).getString(header[j]);
				}
				datas.add(inter);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return datas;
	}
	
}
