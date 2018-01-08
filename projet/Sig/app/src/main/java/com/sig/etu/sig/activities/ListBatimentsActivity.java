package com.sig.etu.sig.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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

    /*public static final String EXTRA_ID = "entry_id";
    public static final String EXTRA_DATE = "entry_date";
    public static final String EXTRA_TITRE = "entry_titre";
    public static final String EXTRA_TEXTE = "entry_texte";
    public static final String EXTRA_IMAGE = "entry_image";
    public static final String EXTRA_ACTION = "user_action";*/

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


        //Button flottant de retour
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.visualiser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject object = ParserJson.parseLieuxTo(new ParserCsvLieux(',',entries, datasource.getAllVilles(),
                        datasource.getAllTypesBatiments()).toCsvData());
                Intent intent = new Intent(ListBatimentsActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_LATITUDE, "");
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, "");
                intent.putExtra(MapActivity.EXTRA_LIEUX, object.toString());
                startActivity(intent);
            }
        });


        //ListView et la redirection vers la map par click.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = mListView.getItemAtPosition(position);
                Batiment ent = (Batiment)o;

                Intent intent = new Intent(ListBatimentsActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_LATITUDE, ent.getLatitude()+"");
                intent.putExtra(MapActivity.EXTRA_LONGITUDE, ent.getLongitude()+"");
                intent.putExtra(MapActivity.EXTRA_LIEUX, "");
                startActivity(intent);
            }

        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(!datasource.isOpen())
            datasource.open();

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //We get all the data
            /*String texte = data.getStringExtra(ListeActivity.EXTRA_TEXTE);
            String titre = data.getStringExtra(ListeActivity.EXTRA_TITRE);
            String image = data.getStringExtra(ListeActivity.EXTRA_IMAGE);*/

            Calendar now = Calendar.getInstance();
            String n = now.get(Calendar.DAY_OF_MONTH) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.YEAR);

            //We add the new entry in the database
            /*Entry e = datasource.createEntry(n, texte, titre, image+"");*/

            //We modify the view
            //adapter.add(e);
            adapter.notifyDataSetChanged();
        }

        if (requestCode == 2 && resultCode ==Activity.RESULT_OK) {
            //We get all the data
            /*Integer id = Integer.valueOf(data.getStringExtra(ListeActivity.EXTRA_ID));
            String action = data.getStringExtra(ListeActivity.EXTRA_ACTION);

            if(action.equals("modifier")){
                Entry ent = datasource.getEntry(id);
                Log.v("test", ent.getId()+"");
                Intent intent = new Intent(ListeActivity.this, ModifierEntry.class);
                intent.putExtra(EXTRA_ID, ent.getId()+"");
                intent.putExtra(EXTRA_DATE, ent.getDate());
                intent.putExtra(EXTRA_TITRE, ent.getTitre());
                intent.putExtra(EXTRA_TEXTE, ent.getTexte());
                intent.putExtra(EXTRA_IMAGE, ent.getImage());
                startActivityForResult(intent, ModifierEntry.REQUEST_CODE);
            }
            if(action.equals("supprimer")){
                Entry e = datasource.deleteEntry(id);
                adapter.remove(e);
                adapter.notifyDataSetChanged();
            }*/
        }
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            /*Integer id = Integer.valueOf(data.getStringExtra(ListeActivity.EXTRA_ID));
            String date = data.getStringExtra(ListeActivity.EXTRA_DATE);
            String texte = data.getStringExtra(ListeActivity.EXTRA_TEXTE);
            String titre = data.getStringExtra(ListeActivity.EXTRA_TITRE);
            String image = data.getStringExtra(ListeActivity.EXTRA_IMAGE);

            Entry prec = datasource.getEntry(id);
            int pos = adapter.getPosition(prec);
            Entry e = datasource.updateEntry(id, date, texte, titre, image);
            adapter.remove(prec);
            adapter.insert(e, pos);
            adapter.notifyDataSetChanged();*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
