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
import android.widget.ListView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.Metier;
import com.sig.etu.sig.vues.BatimentListAdapter;
import com.sig.etu.sig.vues.MetierListAdapter;

import java.util.Calendar;
import java.util.List;

public class ListMetiersActivity extends AppCompatActivity {

    private BDDManager datasource;
    private ListView mListView;
    private MetierListAdapter adapter;

    /*public static final String EXTRA_ID = "entry_id";
    public static final String EXTRA_DATE = "entry_date";
    public static final String EXTRA_TITRE = "entry_titre";
    public static final String EXTRA_TEXTE = "entry_texte";
    public static final String EXTRA_IMAGE = "entry_image";
    public static final String EXTRA_ACTION = "user_action";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //We used the database to get all existing entries.
        datasource = new BDDManager(this);
        datasource.open();
        List<Metier> entries = datasource.getAllMetiers();

        mListView = (ListView) findViewById(R.id.liste);
        adapter = new MetierListAdapter(ListMetiersActivity.this, entries);
        mListView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.retour);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListMetiersActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = mListView.getItemAtPosition(position);
                Entry ent = (Entry)o;

                Intent intent = new Intent(ListeActivity.this, DetailEntry.class);
                intent.putExtra(EXTRA_ID, ent.getId()+"");
                intent.putExtra(EXTRA_DATE, ent.getDate());
                intent.putExtra(EXTRA_TITRE, ent.getTitre());
                intent.putExtra(EXTRA_TEXTE, ent.getTexte());
                intent.putExtra(EXTRA_IMAGE, ent.getImage());
                startActivityForResult(intent, DetailEntry.REQUEST_CODE);
            }

        });*/


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
