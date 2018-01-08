package com.sig.etu.sig.util;

/**
 * Created by vogel on 05/01/18.
 */

public class StringFormat {

    public static String correction(String initial){
        String fin = initial.trim();
        fin = fin.replaceAll("'", " ");
        return fin;
    }
}
