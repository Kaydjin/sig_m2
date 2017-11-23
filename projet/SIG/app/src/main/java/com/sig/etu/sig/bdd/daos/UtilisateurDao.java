package com.sig.etu.sig.bdd.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sig.etu.sig.bdd.tables.UtilisateurTable;
import com.sig.etu.sig.modeles.Utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class UtilisateurDao {

    private SQLiteDatabase database;

    public UtilisateurDao(){}

    /**
     * Specify how a new user is create in the database
     */
    public Utilisateur create(String nom, String mdp) {

        ContentValues values = new ContentValues();
        values.put(UtilisateurTable.KEY_COL_MDP, mdp);
        values.put(UtilisateurTable.KEY_COL_NOM, nom);

        long insertId = database.insert(UtilisateurTable.NAME_TABLE, null,
                values);
        Cursor cursor = database.query(UtilisateurTable.NAME_TABLE,
                UtilisateurTable.allcolumns, UtilisateurTable.KEY_COL_ID
                        + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Utilisateur newEntry = this.valueOf(cursor);
        cursor.close();
        return newEntry;
    }


    /**
     * Specify how a user is delete in the database
     */
    public Utilisateur delete(int id_entry) {
        long id = id_entry;
        Utilisateur ent = this.get(id_entry);
        int num = database.delete(UtilisateurTable.NAME_TABLE, UtilisateurTable.KEY_COL_ID
                + " = " + id, null);
        return ent;
    }

    /**
     * Specify how a user is update in the database
     */
    public Utilisateur update(int id_entry, String nom, String mdp) {
        long id = id_entry;
        ContentValues values = new ContentValues();
        values.put(UtilisateurTable.KEY_COL_MDP, mdp);
        values.put(UtilisateurTable.KEY_COL_NOM, nom);

        database.update(UtilisateurTable.NAME_TABLE, values,
                UtilisateurTable.KEY_COL_ID + " = " + id, null);
        return this.get(id_entry);
    }

    /**
     * Get one user of the database
     */
    public Utilisateur get(int id_entry){
        long id = id_entry;
        Utilisateur entry = null;
        Cursor cursor = database.query(UtilisateurTable.NAME_TABLE,
                UtilisateurTable.allcolumns,
                UtilisateurTable.KEY_COL_ID + " = " + id, null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Get one user of the database
     */
    public Utilisateur get(String nom, String mdp){
        Utilisateur entry = null;
        Cursor cursor = database.query(UtilisateurTable.NAME_TABLE,
                UtilisateurTable.allcolumns,
                UtilisateurTable.KEY_COL_NOM + " = " + nom + " , "
                        + UtilisateurTable.KEY_COL_MDP + " = " + mdp, null, null, null, null);

        if(cursor.moveToFirst())
            entry = this.valueOf(cursor);

        cursor.close();
        return entry;
    }

    /**
     * Useful method which returns all the users in the database.
     * @return the users as a List collection
     */
    public List<Utilisateur> getAll() {
        List<Utilisateur> entries = new ArrayList<Utilisateur>();

        Cursor cursor = database.query(UtilisateurTable.NAME_TABLE,
                UtilisateurTable.allcolumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Utilisateur entry = this.valueOf(cursor);
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
    private Utilisateur valueOf(Cursor cursor) {
        Utilisateur entry = new Utilisateur();
        entry.setId((int)cursor.getLong(UtilisateurTable.NUM_ID_COLUMN));
        entry.setMdp(cursor.getString(UtilisateurTable.NUM_MDP_COLUMN));
        entry.setNom(cursor.getString(UtilisateurTable.NUM_NOM_COLUMN));
        return entry;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
