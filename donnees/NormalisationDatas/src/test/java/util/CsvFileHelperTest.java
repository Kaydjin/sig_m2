package util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class CsvFileHelperTest {


private final static String FILE_NAME = "chien-test-01.csv";

	@Test
	public void testGetLocalResource() {
	    // Param
	    final String fileName = FILE_NAME;
	
	    // Result
	    // ...
	
	    // Appel
	    final File file = CsvFileHelper.getLocalResource(fileName);
	
	    // Test
	    // On sait que le fichier existe bien puisque c'est avec lui qu'on travaille depuis le début.
	    assertTrue(file.exists());
	}

}