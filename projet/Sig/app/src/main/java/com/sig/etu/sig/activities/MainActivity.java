package com.sig.etu.sig.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sig.etu.sig.R;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.TypeBatiment;

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
                datasource.createVille("45100", "Orléans la source");
                datasource.createVille("75000", "Paris");
                datasource.createTypeBatiment(TypeBatiment.Tribunaux.GRANDE_INSTANCE.toString(), "a");
                datasource.createMetier("Notaire");
                datasource.createMetier("Avocat");
                datasource.createBatiment(1,1,43.02,43.02,"Nom batiment","Adresse Batiment","numero");
                datasource.createBatiment(1,1,45.02,80.02,"Nom batiment2","Adresse Batiment2","2numero");
                datasource.createPersonne("personne", "adresse", 1, 1);
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
