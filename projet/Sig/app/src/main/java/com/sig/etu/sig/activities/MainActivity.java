package com.sig.etu.sig.activities;

import android.content.Intent;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sig.etu.sig.R;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.TypeBatiment;
import com.sig.etu.sig.modeles.Ville;
import com.sig.etu.sig.util.ParserCsvLieux;
import com.sig.etu.sig.util.StringFormat;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BDDManager datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new BDDManager(this);
        datasource.open();

        Button button_generate = (Button)findViewById(R.id.generate);
        Button button_batiments = (Button)findViewById(R.id.batiments);
        Button button_metiers = (Button)findViewById(R.id.metiers);
        Button button_personnes = (Button)findViewById(R.id.personnes);
        Button button_types_batiments = (Button)findViewById(R.id.types_batiments);
        Button button_villes = (Button)findViewById(R.id.villes);
        button_generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //On efface toutes les anciennes données.
                datasource.allRemove();
                datasource.open();

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
                            b.getLongitude(), b.getNom().trim(), b.getAdresse().trim(),
                            b.getTelephone().trim());
                }
                datasource.close();

            }
        });
        button_batiments.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent(MainActivity.this, ListBatimentsActivity.class);
                startActivity(resultIntent);
            }
        });
        button_metiers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent(MainActivity.this, ListMetiersActivity.class);
                startActivity(resultIntent);
            }
        });
        button_personnes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent(MainActivity.this, ListPersonnesActivity.class);
                startActivity(resultIntent);
            }
        });
        button_types_batiments.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent(MainActivity.this, ListTypeBatimentsActivity.class);
                startActivity(resultIntent);
            }
        });
        button_villes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent(MainActivity.this, ListVillesActivity.class);
                startActivity(resultIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.v("onResume","ici");
        if(!datasource.isOpen())
            datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v("onPause","ici");
        if(datasource.isOpen())
            datasource.close();
        super.onPause();
    }
}
