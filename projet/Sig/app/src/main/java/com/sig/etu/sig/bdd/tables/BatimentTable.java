package com.sig.etu.sig.bdd.tables;

public class BatimentTable {

    //Table name
    public static final String NAME_TABLE = "Batiment";
    public static final int NUM_TABLE = 0;
    public static final String[] allcolumns = {
            BatimentTable.KEY_COL_ID,
            BatimentTable.KEY_COL_ID_TYPE,
            BatimentTable.KEY_COL_ID_VILLE,
            BatimentTable.KEY_COL_LATITUDE,
            BatimentTable.KEY_COL_LONGITUDE,
            BatimentTable.KEY_COL_NOM,
            BatimentTable.KEY_COL_ADRESSE,
            BatimentTable.KEY_COL_TELEPHONE
    };

    public static final String KEY_COL_ID = "_id";
    public static final String KEY_COL_ID_TYPE = "_id_type";
    public static final String KEY_COL_ID_VILLE = "_id_ville";
    public static final String KEY_COL_LATITUDE = "_latitude";
    public static final String KEY_COL_LONGITUDE = "_longitude";
    public static final String KEY_COL_NOM = "_nom";
    public static final String KEY_COL_ADRESSE = "_adresse";
    public static final String KEY_COL_TELEPHONE = "_telephone";


    public static final int NUM_ID_COLUMN = 0;
    public static final int NUM_ID_TYPE_COLUMN = 1;
    public static final int NUM_ID_VILLE_COLUMN = 2;
    public static final int NUM_LATITUDE_COLUMN = 3;
    public static final int NUM_LONGITUDE_COLUMN = 4;
    public static final int NUM_NOM_COLUMN = 5;
    public static final int NUM_ADRESSE_COLUMN = 6;
    public static final int NUM_TELEPHONE_COLUMN = 7;

    public static final String SQL_TABLE = "create table "
            + BatimentTable.NAME_TABLE + "(" + BatimentTable.KEY_COL_ID
            + " integer primary key autoincrement, "
            + BatimentTable.KEY_COL_ID_TYPE + " integer, "
            + BatimentTable.KEY_COL_ID_VILLE + " integer, "
            + BatimentTable.KEY_COL_LATITUDE + " REAL, "
            + BatimentTable.KEY_COL_LONGITUDE + " REAL, "
            + BatimentTable.KEY_COL_NOM + " TEXT, "
            + BatimentTable.KEY_COL_ADRESSE + " TEXT, "
            + BatimentTable.KEY_COL_TELEPHONE + " TEXT) ";

}
