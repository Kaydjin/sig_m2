package com.sig.etu.sig.bdd.tables;

public class MetierTable {

    //Table name
    public static final String NAME_TABLE = "Metier";
    public static final int NUM_TABLE = 1;
    public static final String[] allcolumns = {
            MetierTable.KEY_COL_ID,
            MetierTable.KEY_COL_NOM
    };

    public static final String KEY_COL_ID = "_id";
    public static final String KEY_COL_NOM = "_nom";


    public static final int NUM_ID_COLUMN = 0;
    public static final int NUM_NOM_COLUMN = 1;

    public static final String SQL_TABLE = "create table "
            + MetierTable.NAME_TABLE + "(" + MetierTable.KEY_COL_ID
            + " integer primary key autoincrement, "
            + MetierTable.KEY_COL_NOM + " TEXT) ";
}
