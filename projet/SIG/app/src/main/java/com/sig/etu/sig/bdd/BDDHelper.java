package com.sig.etu.sig.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sig.etu.sig.bdd.tables.BatimentTable;
import com.sig.etu.sig.bdd.tables.MetierTable;
import com.sig.etu.sig.bdd.tables.PersonneTable;
import com.sig.etu.sig.bdd.tables.UtilisateurTable;
import com.sig.etu.sig.bdd.tables.VilleTable;

/**
 * help from
 * http://vogella.developpez.com/tutoriels/android/utilisation-base-donnees-sqlite/
 */

public class BDDHelper extends SQLiteOpenHelper {

    //The database name
    public static final String DATABASE_NAME = "sig.bdd";
    public static final int DATABASE_VERSION = 1;

    //The static string to create the database. Here there is no relation between table
    //If not the creation could be mandatory to be in one String.
    private static final String[] DATABASE_CREATE = {
            VilleTable.SQL_TABLE,
            MetierTable.SQL_TABLE,
            BatimentTable.SQL_TABLE,
            PersonneTable.SQL_TABLE,
            UtilisateurTable.SQL_TABLE
    };

    /**
     * Constructor of the database meta-data and connection.
     *
     * @param context the context of the activity in which the bdd will be access.
     * @param name    the name of the database
     * @param factory the factory (null works nicely too)
     * @param version version (useless most of the time)
     */
    public BDDHelper(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * One of the two method to override, when there is no table as specified, create it.
     *
     * @param db the database link to this database informations.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            for (int i = 0; i < DATABASE_CREATE.length; i++) {
                db.execSQL(DATABASE_CREATE[i]);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
    }

    public void allRemove(SQLiteDatabase db) {
        try{
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS "+ PersonneTable.NAME_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+ BatimentTable.NAME_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+ MetierTable.NAME_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+ VilleTable.NAME_TABLE);
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        /*try{
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS "+ UtilisateurTable.NAME_TABLE);
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }*/
    }

    /**
     * The second method to override
     * @param db the database link to this database informations.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("BDDHelper", "Mise à jour de la version "+
                oldVersion + "vers la version " + newVersion
                + ", les anciennes données seront détruites ");
        this.allRemove(db);
        onCreate(db);
    }



}
