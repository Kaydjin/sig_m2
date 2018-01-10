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
        mListView = (ListView) findViewById(R.id.liste);
        adapter = new PersonneListAdapter(ListPersonnesActivity.this, entries);
        mListView.setAdapter(adapter);

        //Listener du bouton filtre.
        Button buttonFiltre = (Button)findViewById(R.id.buttonFiltre);
        buttonFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Filtre search
                String search =  ((TextView)findViewById(R.id.search)).getText()+"";
                entries = datasource.getPersonneByName(search);

                //Filtre search fail
                if(search.equals("") || entries==null || entries.size()==0)
                    entries = datasource.getAllPersonnes();

                mListView = (ListView) findViewById(R.id.liste);
                adapter = new PersonneListAdapter(ListPersonnesActivity.this, entries);
                mListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        //Button flottant de visualisation
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.visualiser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = ParserJson.parsePersonneTo(
                        new ParserCsvPersonnes(',',
                                datasource.getAllBatiments(),
                                datasource.getAllMetiers(),
                                entries).toCsvData());
                Intent intent = new Intent(ListPersonnesActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_NOM, "");
                intent.putExtra(MapActivity.EXTRA_DESCRIPTION, "");
                intent.putExtra(MapActivity.EXTRA_LATITUDE, "");
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, "");
                intent.putExtra(MapActivity.EXTRA_LIEUX, "");
                intent.putExtra(MapActivity.EXTRA_PERSONNES, object.toString());
                startActivity(intent);
            }
        });


        //ListView et la redirection vers la map par click.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = mListView.getItemAtPosition(position);
                Personne ent = (Personne)o;
                Personne envoi = datasource.getPersonneByAdresse(ent.getAdresse());
                Intent intent = new Intent(ListPersonnesActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_NOM, envoi.getNom());
                intent.putExtra(MapActivity.EXTRA_DESCRIPTION,envoi.getAdresse());
                intent.putExtra(MapActivity.EXTRA_LATITUDE, envoi.getLatitude()+"");
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, envoi.getLongitude()+"");
                intent.putExtra(MapActivity.EXTRA_LIEUX, "");
                intent.putExtra(MapActivity.EXTRA_PERSONNES, "");
                startActivity(intent);
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
