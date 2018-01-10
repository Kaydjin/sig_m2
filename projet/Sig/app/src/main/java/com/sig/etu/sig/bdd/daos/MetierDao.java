package com.sig.etu.sig.bdd.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sig.etu.sig.bdd.tables.MetierTable;
import com.sig.etu.sig.modeles.Metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class MetierDao {

    private SQLiteDatabase database;

    public MetierDao(){}

    /**
     * Specify how a new Metier is create in the database
     */
    public Metier create(String nom) {

        ContentValues values = new ContentValues();
        values.put(MetierTable.KEY_COL_NOM, nom);

        long insertId = database.insert(MetierTable.NAME_TABLE, null,
                values);
        Cursor cursor = database.query(MetierTable.NAME_TABLE,
                MetierTable.allcolumns, MetierTable.KEY_COL_ID
                        + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Metier newEntry = this.valueOf(cursor);
        cursor.close();
        return newEntry;
    }


    /**
     * Specify how a Metier is delete in the database
     */
    public Metier delete(int id_entry) {
        long id = id_entry;
        Metier ent = this.get(id_entry);
        int num = database.delete(MetierTable.NAME_TABLE, MetierTable.KEY_COL_ID
                + " = " + id, null);
        return ent;
    }

    /**
     * Specify how a Metier is update in the database
     */
    public Metier update(int id_entry, String nom) {
        long id = id_entry;
        ContentValues values = new ContentValues();
        values.put(MetierTable.KEY_COL_NOM, nom);

        database.update(MetierTable.NAME_TABLE, values,
                MetierTable.KEY_COL_ID + " = " + id, null);
        return this.get(id_entry);
    }

    /**
     * Get one metier of the database
     */
    public Metier get(int id_entry){
        long id = id_entry;
        Metier entry = null;
        Cursor cursor = database.query(MetierTable.NAME_TABLE,
                MetierTable.allcolumns,
                MetierTable.KEY_COL_ID + " = " + id, null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Get one metier of the database
     */
    public Metier getByName(String name){
        Metier entry = null;
        Cursor cursor = database.query(MetierTable.NAME_TABLE,
                MetierTable.allcolumns,
                MetierTable.KEY_COL_NOM + " = " + "'" + name + "'", null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Useful method which returns all the metiers in the database.
     * @return the metiers as a List collection
     */
    public List<Metier> getAll() {
        List<Metier> entries = new ArrayList<Metier>();

        Cursor cursor = database.query(MetierTable.NAME_TABLE,
                MetierTable.allcolumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Metier entry = this.valueOf(cursor);
            entries.add(entry);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return entries;
    }

    /**
     * Transform the result of a query in a class type format
     */
    private Metier valueOf(Cursor cursor) {
        Metier entry = new Metier();
        entry.setId((int)cursor.getLong(MetierTable.NUM_ID_COLUMN));
        entry.setNom(cursor.getString(MetierTable.NUM_NOM_COLUMN));
        return entry;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
