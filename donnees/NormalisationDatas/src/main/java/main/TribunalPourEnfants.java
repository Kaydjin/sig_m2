package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.CsvFileHelper;
import util.CsvFileImpl;
import util.ICsvFile;

public class TribunalPourEnfants {

	public static void main(String[] args) {
		
		List<String[]> datas = new ArrayList<String[]>();
		int i=1;
		System.out.println("Lieux normalisés");
		ICsvFile csv2 = new CsvFileImpl("lieux_normalises3.csv");
		datas.add(csv2.getTitles());
		for(String[] tab: csv2.getData()){
			i++;
			if(tab[0].contains("Tribunal pour Enfants")){
				datas.add(tab);
			}
		}
		
		CsvFileHelper.writeFile("tribunal_enfants.csv", datas, ",");
		
	}
}
