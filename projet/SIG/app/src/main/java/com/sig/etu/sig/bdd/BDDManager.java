package com.sig.etu.sig.bdd;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sig.etu.sig.bdd.daos.BatimentDao;
import com.sig.etu.sig.bdd.daos.MetierDao;
import com.sig.etu.sig.bdd.daos.PersonneDao;
import com.sig.etu.sig.bdd.daos.TypeBatimentDao;
import com.sig.etu.sig.bdd.daos.UtilisateurDao;
import com.sig.etu.sig.bdd.daos.VilleDao;
import com.sig.etu.sig.bdd.tables.BatimentTable;
import com.sig.etu.sig.bdd.tables.MetierTable;
import com.sig.etu.sig.bdd.tables.PersonneTable;
import com.sig.etu.sig.bdd.tables.VilleTable;
import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.Metier;
import com.sig.etu.sig.modeles.Personne;
import com.sig.etu.sig.modeles.TypeBatiment;
import com.sig.etu.sig.modeles.Ville;

import java.util.List;

/**
 * help from http://vogella.developpez.com/tutoriels/android/utilisation-base-donnees-sqlite/
 */

public class BDDManager {

    //The database
    private SQLiteDatabase database;

    //The database meta-data.
    private BDDHelper bddHelper;

    private BatimentDao batimentDao;
    private MetierDao metierDao;
    private PersonneDao personneDao;
    private TypeBatimentDao typeBatimentDao;
    private UtilisateurDao utilisateurDao;
    private VilleDao villeDao;

    //If we have multiple tables, all the tables must give their list of columns names.
    private String[][] allColumns = {
            VilleTable.allcolumns,
            MetierTable.allcolumns,
            BatimentTable.allcolumns,
            PersonneTable.allcolumns
    };
    private boolean open;

    /**
     * Build a mangaging tool to link to an already specified database.
     * @param context the context of the activity in which the bdd will be access.
     */
    public BDDManager(Context context) {
        bddHelper = new BDDHelper(context, BDDHelper.DATABASE_NAME, null,
                BDDHelper.DATABASE_VERSION);
        open = false;
        batimentDao = new BatimentDao();
        metierDao = new MetierDao();
        personneDao = new PersonneDao();
        typeBatimentDao = new TypeBatimentDao();
        utilisateurDao = new UtilisateurDao();
        villeDao = new VilleDao();
    }

    /**
     * Create a link to the database.
     * @throws SQLException throws when database opening doesn't work.
     */
    public void open() throws SQLException {
        database = bddHelper.getWritableDatabase();
        open = true;
        batimentDao.setDatabase(database);
        metierDao.setDatabase(database);
        personneDao.setDatabase(database);
        typeBatimentDao.setDatabase(database);
        utilisateurDao.setDatabase(database);
        villeDao.setDatabase(database);
    }

    /**
     * Close the link to the database.
     */
    public void close() {
        bddHelper.close();
        open = false;
    }

    public void allRemove(){
        database = bddHelper.getWritableDatabase();
        bddHelper.allRemove(database);
        bddHelper.onCreate(database);
        bddHelper.close();
    }

    /**
     * Try to connect with a password and a name.
     */
    public boolean connexion(String nom, String mdp){
        boolean test = false;
        return utilisateurDao.get(nom, mdp)!=null;
    }

    public List<Batiment> getAllBatiments(){ return batimentDao.getAll();}
    public List<Metier> getAllMetiers(){ return metierDao.getAll();}
    public List<Personne> getAllPersonnes(){ return personneDao.getAll();}
    public List<Ville> getAllVilles(){ return villeDao.getAll();}
    public List<TypeBatiment> getAllTypesBatiments(){ return typeBatimentDao.getAll();}

    public Batiment getBatiment(int id_entry){ return batimentDao.get(id_entry);}
    public Metier getMetier(int id_entry){ return metierDao.get(id_entry);}
    public Personne getPersonne(int id_entry){ return personneDao.get(id_entry);}
    public TypeBatiment getTypeBatiment(int id_entry){ return typeBatimentDao.get(id_entry);}
    public Ville getVille(int id_entry){ return villeDao.get(id_entry);}

    public Batiment deleteBatiment(int id_entry){ return batimentDao.delete(id_entry);}
    public Metier deleteMetier(int id_entry){ return metierDao.delete(id_entry);}
    public Personne deletePersonne(int id_entry){ return personneDao.delete(id_entry);}
    public TypeBatiment deleteTypeBatiment(int id_entry){ return typeBatimentDao.delete(id_entry);}
    public Ville deleteVille(int id_entry){ return villeDao.delete(id_entry);}

    public Batiment updateBatiment(int id_entry, int id_type, int id_ville, double latitude, double longitude,
                                   String nom, String adresse, String telephone){
        return batimentDao.update(id_entry,id_type, id_ville, latitude, longitude, nom, adresse, telephone);
    }
    public Metier updateMetier(int id_entry, String nom){
        return metierDao.update(id_entry, nom);
    }
    public Personne updatePersonne(int id_entry, String nom, String adresse,
                                   int id_batiment, int id_metier){
        return personneDao.update(id_entry,nom, adresse, id_batiment, id_metier);
    }
    public TypeBatiment updateTypeBatiment(int id_entry,String type, String description){
        return typeBatimentDao.update(id_entry, type, description);
    }
    public Ville updateVille(int id_entry, String code_postale, String nom){
        return villeDao.update(id_entry,code_postale, nom);
    }

    public Batiment createBatiment(int id_type, int id_ville, double latitude, double longitude,
                                    String nom, String adresse, String telephone){
        return createBatiment(id_type, id_ville, latitude, longitude, nom, adresse, telephone);
    }
    public Metier createMetier(String nom){
        return metierDao.create(nom);
    }
    public Personne createPersonne(String nom, String adresse,
                                   int id_batiment, int id_metier){
        return personneDao.create(nom, adresse, id_batiment, id_metier);
    }
    public TypeBatiment createTypeBatiment(String type, String description){
        return typeBatimentDao.create(type, description);
    }
    public Ville createVille(String code_postale, String nom){
        return villeDao.create(code_postale, nom);
    }
    public boolean isOpen() {
        return open;
    }
}
