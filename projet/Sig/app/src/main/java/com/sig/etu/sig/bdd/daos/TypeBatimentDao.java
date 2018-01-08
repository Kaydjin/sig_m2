package com.sig.etu.sig.bdd.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sig.etu.sig.bdd.tables.TypeBatimentTable;
import com.sig.etu.sig.modeles.TypeBatiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class TypeBatimentDao {

    private SQLiteDatabase database;

    public TypeBatimentDao(){ }

    /**
     * Specify how a new type of batiment is create in the database
     */
    public TypeBatiment create(String type, String description) {

        ContentValues values = new ContentValues();
        values.put(TypeBatimentTable.KEY_COL_TYPE, type);
        values.put(TypeBatimentTable.KEY_COL_DESCRIPTION, description);

        long insertId = database.insert(TypeBatimentTable.NAME_TABLE, null,
                values);
        Cursor cursor = database.query(TypeBatimentTable.NAME_TABLE,
                TypeBatimentTable.allcolumns, TypeBatimentTable.KEY_COL_ID
                        + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        TypeBatiment newEntry = this.valueOf(cursor);
        cursor.close();
        return newEntry;
    }


    /**
     * Specify how a type of batiment is delete in the database
     */
    public TypeBatiment delete(int id_entry) {
        long id = id_entry;
        TypeBatiment ent = this.get(id_entry);
        int num = database.delete(TypeBatimentTable.NAME_TABLE, TypeBatimentTable.KEY_COL_ID
                + " = " + id, null);
        return ent;
    }

    /**
     * Specify how a type of batiment is update in the database
     */
    public TypeBatiment update(int id_entry, String type, String description) {
        long id = id_entry;
        ContentValues values = new ContentValues();
        values.put(TypeBatimentTable.KEY_COL_TYPE, type);
        values.put(TypeBatimentTable.KEY_COL_DESCRIPTION, description);

        database.update(TypeBatimentTable.NAME_TABLE, values,
                TypeBatimentTable.KEY_COL_ID + " = " + id, null);
        return this.get(id_entry);
    }

    /**
     * Get one type of batiment of the database
     */
    public TypeBatiment get(int id_entry){
        long id = id_entry;
        TypeBatiment entry = null;
        Cursor cursor = database.query(TypeBatimentTable.NAME_TABLE,
                TypeBatimentTable.allcolumns,
                TypeBatimentTable.KEY_COL_ID + " = " + id, null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Get one type of batiment of the database
     */
    public TypeBatiment getByName(String name){
        TypeBatiment entry = null;
        Cursor cursor = database.query(TypeBatimentTable.NAME_TABLE,
                TypeBatimentTable.allcolumns,
                TypeBatimentTable.KEY_COL_TYPE + " = " + "'" + name + "'", null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Useful method which returns all the types of batiment in the database.
     * @return the types of batiment as a List collection
     */
    public List<TypeBatiment> getAll() {
        List<TypeBatiment> entries = new ArrayList<TypeBatiment>();

        Cursor cursor = database.query(TypeBatimentTable.NAME_TABLE,
                TypeBatimentTable.allcolumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TypeBatiment entry = this.valueOf(cursor);
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
    private TypeBatiment valueOf(Cursor cursor) {
        TypeBatiment entry = new TypeBatiment();
        entry.setId((int)cursor.getLong(TypeBatimentTable.NUM_ID_COLUMN));
        entry.setType(cursor.getString(TypeBatimentTable.NUM_TYPE_COLUMN));
        entry.setDescription(cursor.getString(TypeBatimentTable.NUM_DESCRIPTION_COLUMN));
        return entry;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
