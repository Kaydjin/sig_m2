package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.CsvFileHelper;
import util.CsvFileImpl;
import util.ICsvFile;

public class VilleTraitement {

	public static void main(String[] args) {
		
		List<String> villes = new ArrayList<String>();
		
		System.out.println("Greffes normalisés");
		ICsvFile csv = new CsvFileImpl("greffes_normalises4.csv");
		
		int i=1;
		for(String[] tab: csv.getData()){
			i++;
			if(!villes.contains(tab[3])){
				System.out.println(i + " :" + tab[3]);
				villes.add(tab[3].trim());
			}
					
		}
		i=1;
		System.out.println("tribunaux enfants");
		ICsvFile csv2 = new CsvFileImpl("tribunaux_enfants4.csv");
		for(String[] tab: csv2.getData()){
			i++;
			if(!villes.contains(tab[3])){
				System.out.println(i + " :" + tab[3]);
				villes.add(tab[3].trim());
			}
		}
		
		i=1;
		System.out.println("Tribunaux gi normalisés");
		ICsvFile csv3 = new CsvFileImpl("tribunaux_gi_normalises4.csv");
		for(String[] tab: csv3.getData()){
			i++;
			if(!villes.contains(tab[3])){
				System.out.println(i + " :" + tab[3]);
				villes.add(tab[3].trim());
			}
		}

		i=1;
		System.out.println("Tribunaux ti normalisés");
		ICsvFile csv4 = new CsvFileImpl("tribunaux_ti_normalises4.csv");
		for(String[] tab: csv4.getData()){
			i++;
			if(!villes.contains(tab[3])){
				System.out.println(i + " :" + tab[3]);
				villes.add(tab[3].trim());
			}
		}
		
		
		Collections.sort(villes);
		String base="default";
		List<String> villes2 = new ArrayList<String>();
		for(String s : villes){
			if(!s.contains(base)){
				base = s;
				villes2.add(s);
			}
		}
		System.out.println("***********************************");
		System.out.println(villes.size());
		System.out.println(villes2.size());
		/*for(String s : villes2){
			System.out.println(s);
		}*/
		
		//TRAITEMENTS DES VILLES POUR EVITER LES DUPLICATAS:
		/*List<String[]> datas = new ArrayList<String[]>();
		
		System.out.println("Greffes normalisés");
		datas.add(csv.getTitles());
		i=1;
		for(String[] tab: csv.getData()){
			i++;
			//On est dans le cas où il faut changer la ligne
			if(!villes2.contains(tab[3])){
				//On cherche la base de la ville:
				for(String s : villes2){
					if(tab[3].contains(s)){
						tab[3] = s;
						datas.add(tab);
						break;
					}
				}	
			}else{
				datas.add(tab);
			}
					
		}
		CsvFileHelper.writeFile("greffes_normalises4.csv", datas, ",");
		datas.clear();
		
		
		
		
		i=1;
		System.out.println("tribunaux enfants");
		datas.add(csv2.getTitles());
		for(String[] tab: csv2.getData()){
			i++;
			//On est dans le cas où il faut changer la ligne
			if(!villes2.contains(tab[3])){
				//On cherche la base de la ville:
				for(String s : villes2){
					if(tab[3].contains(s)){
						tab[3] = s;
						datas.add(tab);
						break;
					}
				}	
			}else{
				datas.add(tab);
			}
		}
		CsvFileHelper.writeFile("tribunaux_enfants4.csv", datas, ",");
		datas.clear();
		
		
		
		i=1;
		System.out.println("Tribunaux gi normalisés");
		datas.add(csv3.getTitles());
		for(String[] tab: csv3.getData()){
			i++;
			//On est dans le cas où il faut changer la ligne
			if(!villes2.contains(tab[3])){
				//On cherche la base de la ville:
				for(String s : villes2){
					if(tab[3].contains(s)){
						tab[3] = s;
						datas.add(tab);
						break;
					}
				}	
			}else{
				datas.add(tab);
			}
		}
		CsvFileHelper.writeFile("tribunaux_gi_normalises4.csv", datas, ",");
		datas.clear();
		
		
		i=1;
		datas.add(csv4.getTitles());
		System.out.println("Tribunaux ti normalisés");
		for(String[] tab: csv4.getData()){
			i++;
			//On est dans le cas où il faut changer la ligne
			if(!villes2.contains(tab[3])){
				//On cherche la base de la ville:
				for(String s : villes2){
					if(tab[3].contains(s)){
						tab[3] = s;
						datas.add(tab);
						break;
					}
				}	
			}else{
				datas.add(tab);
			}
		}
		CsvFileHelper.writeFile("tribunaux_ti_normalises4.csv", datas, ",");
		datas.clear();*/
	}
	
}
