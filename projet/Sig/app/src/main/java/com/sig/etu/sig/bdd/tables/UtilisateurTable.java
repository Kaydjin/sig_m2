package com.sig.etu.sig.bdd.tables;

/**
 * Created by vogel on 23/11/17.
 */

public class UtilisateurTable {

    //Table name
    public static final String NAME_TABLE = "Utilisateur";
    public static final int NUM_TABLE = 4;
    public static final String[] allcolumns = {
            UtilisateurTable.KEY_COL_ID,
            UtilisateurTable.KEY_COL_NOM,
            UtilisateurTable.KEY_COL_MDP
    };

    public static final String KEY_COL_ID = "_id";
    public static final String KEY_COL_NOM = "_nom";
    public static final String KEY_COL_MDP = "_mdp";

    public static final int NUM_ID_COLUMN = 0;
    public static final int NUM_NOM_COLUMN = 1;
    public static final int NUM_MDP_COLUMN = 2;

    public static final String SQL_TABLE = "create table "
            + UtilisateurTable.NAME_TABLE + "(" + UtilisateurTable.KEY_COL_ID
            + " integer primary key autoincrement, "
            + UtilisateurTable.KEY_COL_NOM + " TEXT, "
            + UtilisateurTable.KEY_COL_MDP + " TEXT) ";
}