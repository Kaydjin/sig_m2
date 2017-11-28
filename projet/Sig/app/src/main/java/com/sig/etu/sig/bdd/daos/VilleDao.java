package com.sig.etu.sig.bdd.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sig.etu.sig.bdd.tables.VilleTable;
import com.sig.etu.sig.modeles.Ville;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class VilleDao {

    private SQLiteDatabase database;

    public VilleDao(){}

    /**
     * Specify how a new batiment is create in the database
     */
    public Ville create(String code_postale, String nom) {

        ContentValues values = new ContentValues();
        values.put(VilleTable.KEY_COL_CODE_POSTALE, code_postale);
        values.put(VilleTable.KEY_COL_NOM, nom);

        long insertId = database.insert(VilleTable.NAME_TABLE, null,
                values);
        Cursor cursor = database.query(VilleTable.NAME_TABLE,
                VilleTable.allcolumns, VilleTable.KEY_COL_ID
                        + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Ville newEntry = this.valueOf(cursor);
        cursor.close();
        return newEntry;
    }


    /**
     * Specify how a batiment is delete in the database
     */
    public Ville delete(int id_entry) {
        long id = id_entry;
        Ville ent = this.get(id_entry);
        int num = database.delete(VilleTable.NAME_TABLE, VilleTable.KEY_COL_ID
                + " = " + id, null);
        return ent;
    }

    /**
     * Specify how a batiment is update in the database
     */
    public Ville update(int id_entry, String code_postale, String nom) {
        long id = id_entry;
        ContentValues values = new ContentValues();
        values.put(VilleTable.KEY_COL_CODE_POSTALE, code_postale);
        values.put(VilleTable.KEY_COL_NOM, nom);

        database.update(VilleTable.NAME_TABLE, values,
                VilleTable.KEY_COL_ID + " = " + id, null);
        return this.get(id_entry);
    }

    /**
     * Get one batiment of the database
     */
    public Ville get(int id_entry){
        long id = id_entry;
        Ville entry=null;
        Cursor cursor = database.query(VilleTable.NAME_TABLE,
                VilleTable.allcolumns,
                VilleTable.KEY_COL_ID + " = " + id, null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Useful method which returns all the batiments in the database.
     * @return the batiments as a List collection
     */
    public List<Ville> getAll() {
        List<Ville> entries = new ArrayList<Ville>();

        Cursor cursor = database.query(VilleTable.NAME_TABLE,
                VilleTable.allcolumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ville entry = this.valueOf(cursor);
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
    private Ville valueOf(Cursor cursor) {
        Ville entry = new Ville();
        entry.setId((int)cursor.getLong(VilleTable.NUM_ID_COLUMN));
        entry.setCode_postale(cursor.getString(VilleTable.NUM_CODE_POSTALE_COLUMN));
        entry.setNom(cursor.getString(VilleTable.NUM_NOM_COLUMN));
        return entry;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
