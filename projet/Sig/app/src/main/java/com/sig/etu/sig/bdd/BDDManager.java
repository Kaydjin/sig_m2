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

import java.util.ArrayList;
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
     * Tell if the database is empty or not.
     * @return a boolean
     */
    public boolean empty(){
        if(!open){
            this.open();
        }

        //If there is no type batiment, then basic insertion is not done so the database is empty.
        if(typeBatimentDao.get(1)==null)
            return true;

        return false;
    }

    /**
     * Close the link to the database.
     */
    public void close() {
        bddHelper.close();
        open = false;
    }

    /**
     * Destory and create (PRODUCTION MODE BDD)
     */
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

    public List<Batiment> getAllBatiments(){
        List<Batiment> batiments = batimentDao.getAll();
        for(Batiment b : batiments) {
            b.setType(typeBatimentDao.get(b.getId_type()).getType());
            b.setVille(villeDao.get(b.getId_ville()).getNom());
        }
        return batiments;
    }
    public List<Metier> getAllMetiers(){ return metierDao.getAll();}
    public List<Personne> getAllPersonnes(){
        List<Personne> personnes = personneDao.getAll();
        for(Personne p : personnes) {
            p.setNom_metier(metierDao.get(p.getId_metier()).getNom());
            p.setNom_batiment_travail(batimentDao.get(p.getId_batiment()).getNom());
        }
        return personnes;
    }
    public List<Personne> getPersonnesByBatiment(Integer id_batiment){
        List<Personne> personnes = personneDao.getByBatiment(id_batiment);
        for(Personne p : personnes) {
            p.setNom_metier(metierDao.get(p.getId_metier()).getNom());
            p.setNom_batiment_travail(batimentDao.get(p.getId_batiment()).getNom());
        }
        return personnes;
    }
    public List<Ville> getAllVilles(){ return villeDao.getAll();}
    public List<TypeBatiment> getAllTypesBatiments(){ return typeBatimentDao.getAll();}

    //Filtres
    public List<Batiment> getBatimentsFilterDpType(Integer code, String choixType) {
        List<Batiment> entries = this.getBatimentsFilterType(choixType);
        List<Batiment> finaux = new ArrayList<Batiment>();
        for(Batiment b : entries)
            if(this.getVille(b.getId_ville()).getCode_postale().equals(code+""))
                finaux.add(b);

        for(Batiment b : finaux) {
            b.setType(typeBatimentDao.get(b.getId_type()).getType());
            b.setVille(villeDao.get(b.getId_ville()).getNom());
        }
        return finaux;
    }
    public List<Batiment> getBatimentsFilterType(String choixType) {
        Integer id = this.getTypeBatimentByName(choixType).getId();
        List<Batiment> finaux = batimentDao.getByType(id);
        for(Batiment b : finaux) {
            b.setType(typeBatimentDao.get(b.getId_type()).getType());
            b.setVille(villeDao.get(b.getId_ville()).getNom());
        }
        return finaux;
    }
    public List<Batiment> getBatimentsFilterDepartement(Integer code) {
        List<Batiment> entries = this.getAllBatiments();
        List<Batiment> finaux = new ArrayList<Batiment>();
        for(Batiment b : entries)
            if(this.getVille(b.getId_ville()).getCode_postale().equals(code+""))
                finaux.add(b);

        for(Batiment b : finaux) {
            b.setType(typeBatimentDao.get(b.getId_type()).getType());
            b.setVille(villeDao.get(b.getId_ville()).getNom());
        }
        return finaux;
    }

    public Batiment getBatiment(int id_entry){
        Batiment b = batimentDao.get(id_entry);
        b.setType(typeBatimentDao.get(b.getId_type()).getType());
        b.setVille(villeDao.get(b.getId_ville()).getNom());
        return b;
    }
    public Metier getMetier(int id_entry){ return metierDao.get(id_entry);}
    public Metier getMetierByName(String name){ return metierDao.getByName(name);}
    public Personne getPersonne(int id_entry){
        Personne p = personneDao.get(id_entry);
        p.setNom_metier(metierDao.get(p.getId_metier()).getNom());
        p.setNom_batiment_travail(batimentDao.get(p.getId_batiment()).getNom());

        return p;
    }
    public TypeBatiment getTypeBatiment(int id_entry){ return typeBatimentDao.get(id_entry);}
    public TypeBatiment getTypeBatimentByName(String name){ return typeBatimentDao.getByName(name);}
    public Ville getVille(int id_entry){ return villeDao.get(id_entry);}
    public Ville getVilleByName(String name){ return villeDao.getByName(name);}

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
                                   int id_batiment, int id_metier, double latitude, double longitude){
        return personneDao.update(id_entry,nom, adresse, id_batiment, id_metier, latitude, longitude);
    }
    public TypeBatiment updateTypeBatiment(int id_entry,String type, String description){
        return typeBatimentDao.update(id_entry, type, description);
    }
    public Ville updateVille(int id_entry, String code_postale, String nom){
        return villeDao.update(id_entry,code_postale, nom);
    }

    public Batiment createBatiment(int id_type, int id_ville, double latitude, double longitude,
                                    String nom, String adresse, String telephone){
        return batimentDao.create(id_type, id_ville, latitude, longitude, nom, adresse, telephone);
    }
    public Metier createMetier(String nom){
        return metierDao.create(nom);
    }
    public Personne createPersonne(String nom, String adresse,
                                   int id_batiment, int id_metier, double latitude, double longitude){
        return personneDao.create(nom, adresse, id_batiment, id_metier, latitude, longitude);
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

    public Personne getPersonneByAdresse(String adresse) {
        Personne p = personneDao.getByAddress(adresse);
        p.setNom_metier(metierDao.get(p.getId_metier()).getNom());
        p.setNom_batiment_travail(batimentDao.get(p.getId_batiment()).getNom());
        return p;
    }

    public List<Personne> getPersonneByName(String name) {
        List<Personne> personnes = personneDao.getByName(name);
        for(Personne p : personnes) {
            p.setNom_metier(metierDao.get(p.getId_metier()).getNom());
            p.setNom_batiment_travail(batimentDao.get(p.getId_batiment()).getNom());
        }
        return personnes;
    }

    public Batiment getBatimentByName(String nom) {
        return batimentDao.getByName(nom);
    }
}
