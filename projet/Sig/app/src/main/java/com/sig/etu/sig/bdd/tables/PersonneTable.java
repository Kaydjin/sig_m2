package com.sig.etu.sig.bdd.tables;

public class PersonneTable {

    //Table name
    public static final String NAME_TABLE = "Personne";
    public static final int NUM_TABLE = 2;
    public static final String[] allcolumns = {
            PersonneTable.KEY_COL_ID,
            PersonneTable.KEY_COL_ID_BATIMENT,
            PersonneTable.KEY_COL_ID_METIER,
            PersonneTable.KEY_COL_NOM,
            PersonneTable.KEY_COL_ADRESSE,
            PersonneTable.KEY_COL_LATITUDE,
            PersonneTable.KEY_COL_LONGITUDE

    };

    public static final String KEY_COL_ID = "_id";
    public static final String KEY_COL_ID_BATIMENT = "_id_batiment";
    public static final String KEY_COL_ID_METIER = "_id_metier";
    public static final String KEY_COL_NOM = "_nom";
    public static final String KEY_COL_ADRESSE = "_adresse";
    public static final String KEY_COL_LATITUDE = "_latitude";
    public static final String KEY_COL_LONGITUDE = "_longitude";


    public static final int NUM_ID_COLUMN = 0;
    public static final int NUM_ID_BATIMENT_COLUMN = 1;
    public static final int NUM_ID_METIER_COLUMN = 2;
    public static final int NUM_NOM_COLUMN = 3;
    public static final int NUM_ADRESSE_COLUMN = 4;
    public static final int NUM_LATITUDE_COLUMN = 5;
    public static final int NUM_LONGITUDE_COLUMN = 6;


    public static final String SQL_TABLE = "create table "
            + PersonneTable.NAME_TABLE + "(" + PersonneTable.KEY_COL_ID
            + " integer primary key autoincrement, "
            + PersonneTable.KEY_COL_ID_BATIMENT + " integer, "
            + PersonneTable.KEY_COL_ID_METIER + " integer, "
            + PersonneTable.KEY_COL_NOM + " TEXT, "
            + PersonneTable.KEY_COL_ADRESSE + " TEXT, "
            + PersonneTable.KEY_COL_LATITUDE + " REAL, "
            + PersonneTable.KEY_COL_LONGITUDE + " REAL) ";
}
