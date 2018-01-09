package com.sig.etu.sig.bdd.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sig.etu.sig.bdd.tables.BatimentTable;
import com.sig.etu.sig.modeles.Batiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class BatimentDao {

    private SQLiteDatabase database;

    public BatimentDao(){}

    /**
     * Specify how a new batiment is create in the database
     */
    public Batiment create(int id_type, int id_ville, double latitude, double longitude,
            String nom, String adresse, String telephone) {

        long id_t = id_type;
        long id_v = id_ville;
        ContentValues values = new ContentValues();
        values.put(BatimentTable.KEY_COL_ID_TYPE, id_t);
        values.put(BatimentTable.KEY_COL_ID_VILLE, id_v);
        values.put(BatimentTable.KEY_COL_LATITUDE, latitude);
        values.put(BatimentTable.KEY_COL_LONGITUDE, longitude);
        values.put(BatimentTable.KEY_COL_NOM, nom);
        values.put(BatimentTable.KEY_COL_ADRESSE, adresse);
        values.put(BatimentTable.KEY_COL_TELEPHONE, telephone);

        long insertId = database.insert(BatimentTable.NAME_TABLE, null,
                values);
        Cursor cursor = database.query(BatimentTable.NAME_TABLE,
                BatimentTable.allcolumns, BatimentTable.KEY_COL_ID
                        + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Batiment newEntry = this.valueOf(cursor);
        cursor.close();
        return newEntry;
    }


    /**
     * Specify how a batiment is delete in the database
     */
    public Batiment delete(int id_entry) {
        long id = id_entry;
        Batiment ent = this.get(id_entry);
        int num = database.delete(BatimentTable.NAME_TABLE, BatimentTable.KEY_COL_ID
                + " = " + id, null);
        return ent;
    }

    /**
     * Specify how a batiment is update in the database
     */
    public Batiment update(int id_entry, int id_type, int id_ville, double latitude, double longitude,
                        String nom, String adresse, String telephone) {
        long id = id_entry;
        long id_t = id_type;
        long id_v = id_ville;
        ContentValues values = new ContentValues();
        values.put(BatimentTable.KEY_COL_ID_TYPE, id_t);
        values.put(BatimentTable.KEY_COL_ID_VILLE, id_v);
        values.put(BatimentTable.KEY_COL_LATITUDE, latitude);
        values.put(BatimentTable.KEY_COL_LONGITUDE, longitude);
        values.put(BatimentTable.KEY_COL_NOM, nom);
        values.put(BatimentTable.KEY_COL_ADRESSE, adresse);
        values.put(BatimentTable.KEY_COL_TELEPHONE, telephone);

        database.update(BatimentTable.NAME_TABLE, values,
                BatimentTable.KEY_COL_ID + " = " + id, null);
        return this.get(id_entry);
    }

    /**
     * Get one batiment of the database
     */
    public Batiment get(int id_entry){
        long id = id_entry;
        Batiment entry=null;
        Cursor cursor = database.query(BatimentTable.NAME_TABLE,
                BatimentTable.allcolumns,
                BatimentTable.KEY_COL_ID + " = " + id, null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Get one batiment of the database
     * @param nom
     * @return
     */
    public Batiment getByName(String nom) {
        Batiment entry=null;
        Cursor cursor = database.query(BatimentTable.NAME_TABLE,
                BatimentTable.allcolumns,
                BatimentTable.KEY_COL_NOM + " = " + "'" + nom + "'", null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Useful method which returns all the batiments in the database.
     * @return the batiments as a List collection
     */
    public List<Batiment> getAll() {
        List<Batiment> entries = new ArrayList<Batiment>();

        Cursor cursor = database.query(BatimentTable.NAME_TABLE,
                BatimentTable.allcolumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Batiment entry = this.valueOf(cursor);
            entries.add(entry);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return entries;
    }

    /**
     * Useful method which returns all the batiments in the database with a certain type.
     * @param type
     * @return the batiments as a List collection
     */
    public List<Batiment> getByType(Integer type) {
        List<Batiment> entries = new ArrayList<Batiment>();

        Cursor cursor = database.query(BatimentTable.NAME_TABLE,
                BatimentTable.allcolumns,
                BatimentTable.KEY_COL_ID_TYPE + " = " + type, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Batiment entry = this.valueOf(cursor);
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
    private Batiment valueOf(Cursor cursor) {
        Batiment entry = new Batiment();
        entry.setId((int)cursor.getLong(BatimentTable.NUM_ID_COLUMN));
        entry.setId_type((int)cursor.getLong(BatimentTable.NUM_ID_TYPE_COLUMN));
        entry.setId_ville((int)cursor.getLong(BatimentTable.NUM_ID_VILLE_COLUMN));
        entry.setLatitude(cursor.getDouble(BatimentTable.NUM_LATITUDE_COLUMN));
        entry.setLongitude(cursor.getDouble(BatimentTable.NUM_LONGITUDE_COLUMN));
        entry.setNom(cursor.getString(BatimentTable.NUM_NOM_COLUMN));
        entry.setAdresse(cursor.getString(BatimentTable.NUM_ADRESSE_COLUMN));
        entry.setTelephone(cursor.getString(BatimentTable.NUM_TELEPHONE_COLUMN));
        return entry;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

}