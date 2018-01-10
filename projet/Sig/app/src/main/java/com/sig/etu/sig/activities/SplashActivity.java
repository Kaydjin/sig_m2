package com.sig.etu.sig.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sig.etu.sig.R;
import com.sig.etu.sig.activities.MainActivity;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.TypeBatiment;
import com.sig.etu.sig.modeles.Ville;
import com.sig.etu.sig.util.ParserCsvLieux;
import com.sig.etu.sig.util.StringFormat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private BDDManager datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        datasource = new BDDManager(this);

        //PRODUCTION MODE BDD destroy and create;
        //datasource.allRemove();
        datasource.open();

        //Si la datasource est empty on crée un thread pour générer la base de donnée.
        if(datasource.empty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    generate();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).start();
        }else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void generate(){
        //Batiments:
        List<TypeBatiment> typesBatiments = new ArrayList<TypeBatiment>();
        typesBatiments.add(new TypeBatiment(TypeBatiment.Tribunaux.ENFANT.toString(),
                "Tribunaux pour enfants"));
        typesBatiments.add(new TypeBatiment(TypeBatiment.Tribunaux.GRANDE_INSTANCE.toString(),
                "Tribunaux de grand instances"));
        typesBatiments.add(new TypeBatiment(TypeBatiment.Tribunaux.INSTANCE.toString(),
                "Tribunaux d'instances"));
        typesBatiments.add(new TypeBatiment(TypeBatiment.Tribunaux.GREFFE.toString(),
                "Greffes"));
        String test = TypeBatiment.Tribunaux.GREFFE.toString();

        List<Batiment> batiments = new ArrayList<Batiment>();
        List<Ville> villes = new ArrayList<Ville>();

        ParserCsvLieux p = new ParserCsvLieux(',',batiments, villes, typesBatiments);

        try {
            InputStreamReader i = new InputStreamReader(getAssets().open("lieux.csv"), "UTF-8");
            p.fromCsvFileInputStream(i);
        } catch (IOException e) {
            Log.e("MainActivity", "Erreur de lecture");
        }

        for(TypeBatiment tb : typesBatiments)
            datasource.createTypeBatiment(tb.getType(), tb.getDescription());
        String nom;
        for(Ville vi: villes) {
            nom = StringFormat.correction(vi.getNom());
            datasource.createVille(vi.getCode_postale().trim(), nom);
        }
        Ville v_inter;
        TypeBatiment tb_inter;

        for(Batiment b : batiments) {
            tb_inter = datasource.getTypeBatimentByName(b.getType().trim());
            v_inter = datasource.getVilleByName(StringFormat.correction(b.getVille()));
            datasource.createBatiment(tb_inter.getId(), v_inter.getId(), b.getLatitude(),
                    b.getLongitude(), b.getNom().trim(), StringFormat.correction(b.getAdresse()),
                    b.getTelephone().trim());
        }

        //Personnes:

        // Metiers: Avocat, notaire, huisser
        List<String> metiers = new ArrayList<String>();
        metiers.add("Avocat");
        metiers.add("Notaire");
        metiers.add("Huissier");

        for(String s : metiers)
            datasource.createMetier(s);

        datasource.close();
    }
}
