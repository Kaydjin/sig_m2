package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHelper {

    public static String getResourcePath(String fileName) {
       final File f = new File("");
       final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
       return dossierPath;
   }

   public static File getResource(String fileName) {
       final String completeFileName = getResourcePath(fileName);
       File file = new File(completeFileName);
       return file;
   }
   
   public static File getLocalResource(String fileName) {
       final String completeFileName = "src/main/resources/"+fileName;
       File file = new File(completeFileName);
       return file;
   }
   
   public static List<String> readFile(File file) {

       List<String> result = new ArrayList<String>();
       BufferedReader br;
		try {
		   br = new BufferedReader(new FileReader(file));
	       for (String line = br.readLine(); line != null; line = br.readLine()) {
	           result.add(line);
	       }
	       br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
       return result;
   }
   
   public static void writeFile(String file, List<String[]> datas, String SEPARATOR) {

       List<String> result = new ArrayList<String>();
       BufferedWriter bw;
		try {
		   bw = new BufferedWriter(new FileWriter(file));
		   for(String[] tab: datas){
			   for(String s : tab) {
				   bw.write(s+SEPARATOR);
			   }
			   bw.newLine();
		   }
	       bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   
}