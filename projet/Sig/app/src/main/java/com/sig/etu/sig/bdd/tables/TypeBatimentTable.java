package com.sig.etu.sig.bdd.tables;


public class TypeBatimentTable {

    //Table name
    public static final String NAME_TABLE = "TypeBatiment";
    public static final int NUM_TABLE = 3;
    public static final String[] allcolumns = {
            TypeBatimentTable.KEY_COL_ID,
            TypeBatimentTable.KEY_COL_TYPE,
            TypeBatimentTable.KEY_COL_DESCRIPTION,
    };

    public static final String KEY_COL_ID = "_id";
    public static final String KEY_COL_TYPE = "_type";
    public static final String KEY_COL_DESCRIPTION = "_description";


    public static final int NUM_ID_COLUMN = 0;
    public static final int NUM_TYPE_COLUMN = 1;
    public static final int NUM_DESCRIPTION_COLUMN = 2;

    public static final String SQL_TABLE = "create table "
            + TypeBatimentTable.NAME_TABLE + "(" + TypeBatimentTable.KEY_COL_ID
            + " integer primary key autoincrement, "
            + TypeBatimentTable.KEY_COL_TYPE + " TEXT, "
            + TypeBatimentTable.KEY_COL_DESCRIPTION + " TEXT) ";
}
