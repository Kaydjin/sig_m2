package com.sig.etu.sig.bdd.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sig.etu.sig.bdd.tables.PersonneTable;
import com.sig.etu.sig.modeles.Personne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class PersonneDao {

    private SQLiteDatabase database;

    public PersonneDao(){ }

    /**
     * Specify how a new personne is create in the database
     */
    public Personne create(String nom, String adresse, int id_batiment,
                           int id_metier, double latitude, double longitude) {

        long id_b = id_batiment;
        long id_m = id_metier;
        ContentValues values = new ContentValues();
        values.put(PersonneTable.KEY_COL_ID_BATIMENT, id_b);
        values.put(PersonneTable.KEY_COL_ID_METIER, id_m);
        values.put(PersonneTable.KEY_COL_NOM, nom);
        values.put(PersonneTable.KEY_COL_ADRESSE, adresse);
        values.put(PersonneTable.KEY_COL_LATITUDE, latitude);
        values.put(PersonneTable.KEY_COL_LONGITUDE, longitude);

        long insertId = database.insert(PersonneTable.NAME_TABLE, null,
                values);
        Cursor cursor = database.query(PersonneTable.NAME_TABLE,
                PersonneTable.allcolumns, PersonneTable.KEY_COL_ID
                        + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Personne newEntry = this.valueOf(cursor);
        cursor.close();
        return newEntry;
    }


    /**
     * Specify how a personne is delete in the database
     */
    public Personne delete(int id_entry) {
        long id = id_entry;
        Personne ent = this.get(id_entry);
        int num = database.delete(PersonneTable.NAME_TABLE, PersonneTable.KEY_COL_ID
                + " = " + id, null);
        return ent;
    }

    /**
     * Specify how a personne is update in the database
     */
    public Personne update(int id_entry, String nom, String adresse,
                           int id_batiment, int id_metier, double latitude, double longitude) {
        long id = id_entry;
        long id_b = id_batiment;
        long id_m = id_metier;
        ContentValues values = new ContentValues();
        values.put(PersonneTable.KEY_COL_ID_BATIMENT, id_b);
        values.put(PersonneTable.KEY_COL_ID_METIER, id_m);
        values.put(PersonneTable.KEY_COL_NOM, nom);
        values.put(PersonneTable.KEY_COL_ADRESSE, adresse);
        values.put(PersonneTable.KEY_COL_LATITUDE, latitude);
        values.put(PersonneTable.KEY_COL_LONGITUDE, longitude);

        database.update(PersonneTable.NAME_TABLE, values,
                PersonneTable.KEY_COL_ID + " = " + id, null);
        return this.get(id_entry);
    }

    /**
     * Get one personne of the database
     */
    public Personne get(int id_entry){
        long id = id_entry;
        Personne entry = null;
        Cursor cursor = database.query(PersonneTable.NAME_TABLE,
                PersonneTable.allcolumns,
                PersonneTable.KEY_COL_ID + " = " + id, null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Get one personne of the database by address
     */
    public Personne getByAddress(String address){
        Personne entry = null;
        Cursor cursor = database.query(PersonneTable.NAME_TABLE,
                PersonneTable.allcolumns,
                PersonneTable.KEY_COL_ADRESSE + " = " + "'" + address + "'", null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Useful method which returns all the personnes in the database with a name
     * @param name
     * @return the personnes as a List collection
     */
    public List<Personne> getByName(String name) {
        List<Personne> entries = new ArrayList<Personne>();

        Cursor cursor = database.query(PersonneTable.NAME_TABLE,
                PersonneTable.allcolumns,
                PersonneTable.KEY_COL_NOM + " LIKE " + "'" + name + "'", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Personne entry = this.valueOf(cursor);
            entries.add(entry);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return entries;
    }

    /**
     * Useful method which returns all the personnes in the database with a certain id batiment
     * @param id_batiment
     * @return the personnes as a List collection
     */
    public List<Personne> getByBatiment(Integer id_batiment) {
        List<Personne> entries = new ArrayList<Personne>();

        Cursor cursor = database.query(PersonneTable.NAME_TABLE,
                PersonneTable.allcolumns,
                PersonneTable.KEY_COL_ID_BATIMENT + " = " + id_batiment, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Personne entry = this.valueOf(cursor);
            entries.add(entry);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return entries;
    }

    /**
     * Useful method which returns all the personnes in the database.
     * @return the personnes as a List collection
     */
    public List<Personne> getAll() {
        List<Personne> entries = new ArrayList<Personne>();

        Cursor cursor = database.query(PersonneTable.NAME_TABLE,
                PersonneTable.allcolumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Personne entry = this.valueOf(cursor);
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
    private Personne valueOf(Cursor cursor) {
        Personne entry = new Personne();
        entry.setId((int)cursor.getLong(PersonneTable.NUM_ID_COLUMN));
        entry.setId_batiment((int)cursor.getLong(PersonneTable.NUM_ID_BATIMENT_COLUMN));
        entry.setId_metier((int)cursor.getLong(PersonneTable.NUM_ID_METIER_COLUMN));
        entry.setNom(cursor.getString(PersonneTable.NUM_NOM_COLUMN));
        entry.setAdresse(cursor.getString(PersonneTable.NUM_ADRESSE_COLUMN));
        entry.setLatitude(cursor.getDouble(PersonneTable.NUM_LATITUDE_COLUMN));
        entry.setLongitude(cursor.getDouble(PersonneTable.NUM_LONGITUDE_COLUMN));
        return entry;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
