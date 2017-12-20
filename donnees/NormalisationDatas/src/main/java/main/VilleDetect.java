package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.CsvFileHelper;
import util.CsvFileImpl;
import util.ICsvFile;

public class VilleDetect {

	public static void main(String[] args) {
		
		List<String> villes = new ArrayList<String>();
		
		System.out.println("Greffes normalisés");
		ICsvFile csv = new CsvFileImpl("greffes_normalises3.csv");
		
		int i=1;
		for(String[] tab: csv.getData()){
			i++;
			if(!villes.contains(tab[3])){
				System.out.println(i + " :" + tab[3]);
				villes.add(tab[3]);
			}
					
		}
		i=1;
		System.out.println("tribunaux enfants");
		ICsvFile csv2 = new CsvFileImpl("tribunal_enfants.csv");
		for(String[] tab: csv2.getData()){
			i++;
			if(!villes.contains(tab[3])){
				System.out.println(i + " :" + tab[3]);
				villes.add(tab[3]);
			}
		}
		
		i=1;
		System.out.println("Tribunaux gi normalisés");
		ICsvFile csv3 = new CsvFileImpl("tribunaux_gi_normalises3.csv");
		for(String[] tab: csv3.getData()){
			i++;
			if(!villes.contains(tab[3])){
				System.out.println(i + " :" + tab[3]);
				villes.add(tab[3]);
			}
		}

		i=1;
		System.out.println("Tribunaux ti normalisés");
		ICsvFile csv4 = new CsvFileImpl("tribunaux_ti_normalises3.csv");
		for(String[] tab: csv4.getData()){
			i++;
			if(!villes.contains(tab[3])){
				System.out.println(i + " :" + tab[3]);
				villes.add(tab[3]);
			}
		}
		Collections.sort(villes);
		for(String s : villes)
			System.out.println(s);
		
		System.out.println(villes.size());
		
	}
}
