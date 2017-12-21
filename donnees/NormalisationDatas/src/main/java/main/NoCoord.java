package main;

import java.util.ArrayList;
import java.util.List;

import util.CsvFileHelper;
import util.CsvFileImpl;
import util.ICsvFile;

public class NoCoord {
	
	public static void main(String[] args) {
		System.out.println("Greffes normalisés");
		ICsvFile csv = new CsvFileImpl("greffes_normalises2.csv");
		List<String[]> datas = new ArrayList<String[]>();
		datas.add(csv.getTitles());
		int i=1;
		for(String[] tab: csv.getData()){
			i++;
			if(tab.length<7)System.out.println(i);
			else datas.add(tab);
		}
		CsvFileHelper.writeFile("greffes_normalises3.csv", datas, ",");
		
		datas.clear();
		i=1;
		System.out.println("Lieux normalisés");
		ICsvFile csv2 = new CsvFileImpl("lieux_normalises2.csv");
		datas.add(csv2.getTitles());
		for(String[] tab: csv2.getData()){
			i++;
			if(tab.length<7)System.out.println(i);
			else datas.add(tab);
		}
		CsvFileHelper.writeFile("lieux_normalises3.csv", datas, ",");
		
		datas.clear();
		i=1;
		System.out.println("Tribunaux gi normalisés");
		ICsvFile csv3 = new CsvFileImpl("tribunaux_gi_normalises2.csv");
		datas.add(csv3.getTitles());
		for(String[] tab: csv3.getData()){
			i++;
			if(tab.length<7)System.out.println(i);
			else datas.add(tab);
		}
		CsvFileHelper.writeFile("tribunaux_gi_normalises3.csv", datas, ",");
		
		datas.clear();
		i=1;
		System.out.println("Tribunaux ti normalisés");
		ICsvFile csv4 = new CsvFileImpl("tribunaux_ti_normalises2.csv");
		datas.add(csv4.getTitles());
		for(String[] tab: csv4.getData()){
			i++;
			if(tab.length<7)System.out.println(i);
			else datas.add(tab);
		}
		CsvFileHelper.writeFile("tribunaux_ti_normalises3.csv", datas, ",");
	}
}
