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
import com.sig.etu.sig.modeles.TypeBatiment;
import com.sig.etu.sig.vues.TypeBatimentListAdapter;

import java.util.Calendar;
import java.util.List;

public class ListTypeBatimentsActivity extends AppCompatActivity {

    private BDDManager datasource;
    private ListView mListView;
    private TypeBatimentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);
        //We used the database to get all existing entries.
        datasource = new BDDManager(this);
        datasource.open();
        List<TypeBatiment> entries = datasource.getAllTypesBatiments();

        mListView = (ListView) findViewById(R.id.liste);
        adapter = new TypeBatimentListAdapter(ListTypeBatimentsActivity.this, entries);
        mListView.setAdapter(adapter);

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
