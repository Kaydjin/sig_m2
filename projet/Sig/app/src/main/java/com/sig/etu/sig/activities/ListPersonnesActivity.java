package com.sig.etu.sig.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.Personne;
import com.sig.etu.sig.util.ParserCsvLieux;
import com.sig.etu.sig.util.ParserCsvPersonnes;
import com.sig.etu.sig.util.ParserJson;
import com.sig.etu.sig.vues.BatimentListAdapter;
import com.sig.etu.sig.vues.PersonneListAdapter;

import org.json.JSONObject;

import java.util.List;

public class ListPersonnesActivity extends AppCompatActivity {

    private BDDManager datasource;
    private ListView mListView;
    private PersonneListAdapter adapter;
    private List<Personne> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_personnes);
        //We used the database to get all existing entries.
        datasource = new BDDManager(this);
        datasource.open();
        entries = datasource.getAllPersonnes();
        datasource.close();

        mListView = (ListView) findViewById(R.id.liste);
        adapter = new PersonneListAdapter(ListPersonnesActivity.this, entries);
        mListView.setAdapter(adapter);

        //Listener du bouton filtre.
        Button buttonFiltre = (Button)findViewById(R.id.buttonFiltre);
        buttonFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datasource.open();
                //Filtre search
                String search =  ((EditText)findViewById(R.id.search)).getText().toString()+"";
                entries = datasource.getPersonneByName(search);

                //Filtre search fail
                if(search.equals("") || entries==null || entries.size()==0)
                    entries = datasource.getAllPersonnes();

                mListView = (ListView) findViewById(R.id.liste);
                adapter = new PersonneListAdapter(ListPersonnesActivity.this, entries);
                mListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                datasource.close();
            }
        });

        //Button flottant de visualisation
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.visualiser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datasource.open();
                JSONObject object = ParserJson.parsePersonneTo(
                        new ParserCsvPersonnes(',',
                                datasource.getAllBatiments(),
                                datasource.getAllMetiers(),
                                entries).toCsvData());
                Intent intent = new Intent(ListPersonnesActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_TYPE, "");
                intent.putExtra(MapActivity.EXTRA_NOM, "");
                intent.putExtra(MapActivity.EXTRA_DESCRIPTION, "");
                intent.putExtra(MapActivity.EXTRA_LATITUDE, "");
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, "");
                intent.putExtra(MapActivity.EXTRA_LIEUX, "");
                intent.putExtra(MapActivity.EXTRA_PERSONNES, object.toString());
                startActivity(intent);
                datasource.close();
            }
        });


        //ListView et la redirection vers la map par click.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datasource.open();
                Object o = mListView.getItemAtPosition(position);
                Personne ent = (Personne)o;
                Personne envoi = datasource.getPersonneByAdresse(ent.getAdresse());
                Intent intent = new Intent(ListPersonnesActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_TYPE, "personne");
                intent.putExtra(MapActivity.EXTRA_NOM, envoi.getNom());
                intent.putExtra(MapActivity.EXTRA_DESCRIPTION,envoi.getAdresse());
                intent.putExtra(MapActivity.EXTRA_LATITUDE, envoi.getLatitude()+"");
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, envoi.getLongitude()+"");
                intent.putExtra(MapActivity.EXTRA_LIEUX, "");
                intent.putExtra(MapActivity.EXTRA_PERSONNES, "");
                startActivity(intent);
                datasource.close();
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
