package com.sig.etu.sig.bdd.tables;

public class VilleTable {
    private int id;
    private String code_postale;
    private String nom;

    //Table name
    public static final String NAME_TABLE = "Ville";
    public static final int NUM_TABLE = 5;
    public static final String[] allcolumns = {
            VilleTable.KEY_COL_ID,
            VilleTable.KEY_COL_CODE_POSTALE,
            VilleTable.KEY_COL_NOM
    };

    public static final String KEY_COL_ID = "_id";
    public static final String KEY_COL_CODE_POSTALE = "_id_batiment";
    public static final String KEY_COL_NOM = "_nom";


    public static final int NUM_ID_COLUMN = 0;
    public static final int NUM_CODE_POSTALE_COLUMN = 1;
    public static final int NUM_NOM_COLUMN = 2;

    public static final String SQL_TABLE = "create table "
            + VilleTable.NAME_TABLE + "(" + VilleTable.KEY_COL_ID
            + " integer primary key autoincrement, "
            + VilleTable.KEY_COL_CODE_POSTALE + " TEXT, "
            + VilleTable.KEY_COL_NOM + " TEXT) ";

}
