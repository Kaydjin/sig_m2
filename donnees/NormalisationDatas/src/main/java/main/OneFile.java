package main;

import java.util.ArrayList;
import java.util.List;

import util.CsvFileHelper;
import util.CsvFileImpl;
import util.ICsvFile;

public class OneFile {

    public enum Tribunaux {INSTANCE, GRANDE_INSTANCE,ENFANT,GREFFE;}
    
	public static void main(String[] args) {
		
		//Mettre tout dans un gros fichier.
		//Ligne 2 représente le type de bâtiment.
		List<String[]> datas = new ArrayList<String[]>();
		
		System.out.println("Greffes normalisés");
		ICsvFile csv = new CsvFileImpl("greffes_normalises4.csv");
		
		String[] inter = csv.getTitles();
		String[] titre = new String[8];
		titre[0]=inter[0];
		titre[1]="type";
		for(int i=2; i<titre.length; i++)
			titre[i]=inter[i-1];
		datas.add(titre);
		
		for(String[] tab: csv.getData()){
			String[] ligne = new String[8];
			ligne[0]="Greffe de " + tab[0];
			ligne[1]=Tribunaux.GREFFE.toString();
			for(int i=2; i<ligne.length; i++){
				if(i==5)ligne[i]="+33 "+tab[i-1];
				else ligne[i]=tab[i-1];
			}
			datas.add(ligne);
		}
		
		System.out.println("Lieux normalisés");
		ICsvFile csv2 = new CsvFileImpl("tribunaux_enfants4.csv");
		for(String[] tab: csv2.getData()){
			String[] ligne = new String[8];
			ligne[0]=tab[0];
			ligne[1]=Tribunaux.ENFANT.toString();
			for(int i=2; i<ligne.length; i++)
				ligne[i]=tab[i-1];
			datas.add(ligne);
		}
		
		System.out.println("Tribunaux gi normalisés");
		ICsvFile csv3 = new CsvFileImpl("tribunaux_gi_normalises4.csv");
		for(String[] tab: csv3.getData()){
			String[] ligne = new String[8];
			ligne[0]=tab[0];
			ligne[1]=Tribunaux.GRANDE_INSTANCE.toString();
			for(int i=2; i<ligne.length; i++)
				ligne[i]=tab[i-1];
			datas.add(ligne);
		}
		
		System.out.println("Tribunaux ti normalisés");
		ICsvFile csv4 = new CsvFileImpl("tribunaux_ti_normalises4.csv");
		for(String[] tab: csv4.getData()){
			String[] ligne = new String[8];
			ligne[0]=tab[0];
			ligne[1]=Tribunaux.INSTANCE.toString();
			for(int i=2; i<ligne.length; i++)
				ligne[i]=tab[i-1];
			datas.add(ligne);
		}
		
		CsvFileHelper.writeFile("lieux.csv", datas, ",");
	}

}
