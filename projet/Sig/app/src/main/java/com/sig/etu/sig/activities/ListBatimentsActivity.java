package com.sig.etu.sig.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.sig.etu.sig.R;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.TypeBatiment;
import com.sig.etu.sig.util.ParserCsvLieux;
import com.sig.etu.sig.util.ParserJson;
import com.sig.etu.sig.vues.BatimentListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListBatimentsActivity extends AppCompatActivity {

    private BDDManager datasource;
    private Spinner spinner;
    private Spinner spinner2;
    private String choixType="All";
    private String choixDepartement="All";
    private ListView mListView;
    private BatimentListAdapter adapter;
    private List<Batiment> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_batiments);


        //We used the database
        datasource = new BDDManager(this);
        datasource.open();


        //Spinner types batiments
        ArrayList<String> types = new ArrayList<String>();
        types.add("All");
        List<TypeBatiment> t = datasource.getAllTypesBatiments();
        for(TypeBatiment tb : t)
            types.add(tb.getType());

        spinner = (Spinner) findViewById(R.id.spinnerTB);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        types);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object o = spinner.getItemAtPosition(position);
                choixType = (String)o;
                Log.e("ici", "spinnerchoixtype");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Spinner départements
        ArrayList<String> departements = new ArrayList<String>();
        departements.add("All");
        for(int i=0; i<95; i++)
            departements.add(i+"");
        spinner2 = (Spinner) findViewById(R.id.spinnerDP);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        departements);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner2.setAdapter(spinnerArrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object o = spinner2.getItemAtPosition(position);
                String ent = (String)o;
                choixDepartement = ent;
                Log.e("ici", "spinner2choixdepartement");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Listener du bouton filtre.
        Button buttonFiltre = (Button)findViewById(R.id.buttonFiltre);
        buttonFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Filtre departement
                if(!choixDepartement.equals("All") || !choixType.equals("All")){
                    if(!choixType.equals("All")){

                        //Filtre departement et type
                        if(!choixDepartement.equals("All")){
                            entries = datasource.getBatimentsFilterDpType(
                                    Integer.valueOf(choixDepartement), choixType);
                        }else{ //Filtre type
                            entries = datasource.getBatimentsFilterType(choixType);
                        }
                    }else{//Filtre departement
                        entries = datasource.getBatimentsFilterDepartement(Integer.valueOf(choixDepartement));
                    }
                }else{
                    entries = datasource.getAllBatiments();
                }

                mListView = (ListView) findViewById(R.id.liste);
                if(mListView ==null)Log.e("ici","erreur4");
                adapter = new BatimentListAdapter(ListBatimentsActivity.this, entries);
                if(adapter ==null)Log.e("ici","erreur5");
                mListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        //Par défault on affiche tous les batiments.
        entries = datasource.getAllBatiments();
        mListView = (ListView) findViewById(R.id.liste);
        adapter = new BatimentListAdapter(ListBatimentsActivity.this, entries);
        mListView.setAdapter(adapter);


        //Button flottant de visualisation
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.visualiser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = ParserJson.parseLieuxTo(new ParserCsvLieux(',',entries, datasource.getAllVilles(),
                        datasource.getAllTypesBatiments()).toCsvData());
                Intent intent = new Intent(ListBatimentsActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_NOM, "");
                intent.putExtra(MapActivity.EXTRA_DESCRIPTION, "");
                intent.putExtra(MapActivity.EXTRA_LATITUDE, "");
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, "");
                intent.putExtra(MapActivity.EXTRA_LIEUX, object.toString());
                intent.putExtra(MapActivity.EXTRA_PERSONNES, "");
                startActivity(intent);
            }
        });


        //ListView et la redirection vers la map par click.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = mListView.getItemAtPosition(position);
                Batiment ent = (Batiment)o;
                Batiment envoi = datasource.getBatimentByName(ent.getNom());

                Intent intent = new Intent(ListBatimentsActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_NOM, envoi.getNom());
                intent.putExtra(MapActivity.EXTRA_DESCRIPTION,envoi.getAdresse()+"\n"
                        +envoi.getTelephone());
                intent.putExtra(MapActivity.EXTRA_LATITUDE, envoi.getLatitude()+"");
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, envoi.getLongitude()+"");
                intent.putExtra(MapActivity.EXTRA_LIEUX, "");
                intent.putExtra(MapActivity.EXTRA_PERSONNES, "");
                startActivity(intent);
            }

        });


    }
}
