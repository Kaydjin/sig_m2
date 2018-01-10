package com.sig.etu.sig.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.Metier;
import com.sig.etu.sig.vues.MetierListAdapter;
import java.util.List;

public class ListMetiersActivity extends AppCompatActivity {

    private BDDManager datasource;
    private ListView mListView;
    private MetierListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);
        //We used the database to get all existing entries.
        datasource = new BDDManager(this);
        datasource.open();
        List<Metier> entries = datasource.getAllMetiers();

        mListView = (ListView) findViewById(R.id.liste);
        adapter = new MetierListAdapter(ListMetiersActivity.this, entries);
        mListView.setAdapter(adapter);

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
