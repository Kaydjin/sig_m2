package com.sig.etu.sig.util;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvFileImpl implements ICsvFile {
	
	private final Character SEPARATOR = ',';
	private File file;
    private String[] titles;
    private List<String> lines;
    private List<String[] > datas;
    private InputStreamReader input = null;
    
    public CsvFileImpl(String filename) {
    	file = CsvFileHelper.getLocalResource(filename);
    	this.init();
    }
    public CsvFileImpl(File file) {
    	this.file = file;
    	this.init();
    }
    public CsvFileImpl(InputStreamReader input) {
        this.input = input;
        this.init();
    }

    private void init() {

        if(input == null)
            lines = CsvFileHelper.readFile(file);
        else
            lines = CsvFileHelper.readFile(input);

        datas = new ArrayList<String[]>(lines.size());
        String regex = new Character(SEPARATOR).toString();
        boolean first = true;
        for (String line : lines) {
        	
            // Suppression des espaces de fin de ligne
            line = line.trim();
            
            // On saute les lignes vides
            if (line.length() == 0) {
                continue;
            }

            // On saute les lignes de commentaire
            if (line.startsWith("#")) {
                continue;
            }

            String[] oneData = line.split(regex);

            if (first) {
                titles = oneData;
                first = false;
            } else {
                datas.add(oneData);
            }
        }
    }

    /*public String[] valeurTraiter(String[] oneData){
    	String[] res = new String[oneData.length];
    	int i=0;
    	for(String s : oneData){
	    	res[i] = s.trim();
	    	res[i] = res[i].replace(" \"", "");
	    	i++;
    	}
    	
    	return res;
    }*/
    
    public String[] getTitles() {
        return titles;
    }

	public File getFile() {
		return file;
	}

	public List<String[]> getData() {
		return datas;
	}
    
}
