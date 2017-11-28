package com.sig.etu.sig.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sig.etu.sig.R;
import com.sig.etu.sig.activities.MainActivity;
import com.sig.etu.sig.bdd.BDDManager;

public class SplashActivity extends AppCompatActivity {

    private BDDManager datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        datasource = new BDDManager(this);

        //PRODUCTION MODE BDD destroy and create;
        datasource.allRemove();
        datasource.open();

        Intent intent  = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
