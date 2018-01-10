package com.sig.etu.sig.activities;

import android.content.Intent;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sig.etu.sig.R;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.Metier;
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

        datasource = new BDDManager(this);
        datasource.open();

        Button button_batiments = (Button)findViewById(R.id.batiments);
        Button button_metiers = (Button)findViewById(R.id.metiers);
        Button button_personnes = (Button)findViewById(R.id.personnes);
        Button button_types_batiments = (Button)findViewById(R.id.types_batiments);
        Button button_villes = (Button)findViewById(R.id.villes);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds batiments to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_supprimer){
            datasource.allRemove();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
