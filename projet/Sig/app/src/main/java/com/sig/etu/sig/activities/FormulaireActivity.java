package com.sig.etu.sig.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.bdd.BDDManager;
import com.sig.etu.sig.modeles.TypeBatiment;
import com.sig.etu.sig.modeles.Ville;

import org.osmdroid.google.wrapper.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by o2121076 on 09/01/18.
 */

public class FormulaireActivity extends AppCompatActivity {

    private BDDManager datasource;
    private String choixType;
    private Spinner edit_Type;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);
        final Context ctx = getApplicationContext();

        final Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);

        /**Pour la partie du spinner**/
        //We used the database
        datasource = new BDDManager(ctx);
        datasource.open();


        //Spinner types batiments
        ArrayList<String> types = new ArrayList<String>();
        List<TypeBatiment> t = datasource.getAllTypesBatiments();
        for(TypeBatiment tb : t)
            types.add(tb.getType());

        edit_Type = (Spinner) findViewById(R.id.edit_Type);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        types);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        edit_Type.setAdapter(spinnerArrayAdapter);
        edit_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object o = edit_Type.getItemAtPosition(position);
                choixType = (String)o;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        datasource.close();
                    /**/


        Button valider = (Button) findViewById(R.id.button_valider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifierEditTextNonVide())
                {
                    Intent returnIntent = new Intent();

                    EditText edit_Nom = (EditText) findViewById(R.id.edit_Nom);
                    EditText edit_Adresse = (EditText) findViewById(R.id.edit_Adresse);
                    EditText edit_CodePostale = (EditText) findViewById(R.id.edit_CodePostale);
                    EditText edit_Telephone = (EditText) findViewById(R.id.edit_Telephone);
                    EditText edit_Ville = (EditText) findViewById(R.id.edit_Ville);
                    //Le type est deja recuperer

                    if(saveInBDD(edit_Nom.getText().toString(), edit_Adresse.getText().toString(), edit_CodePostale.getText().toString(), edit_Telephone.getText().toString(),choixType,edit_Ville.getText().toString()))
                    {
                        returnIntent.putExtra("Nom",edit_Nom.getText().toString());
                        returnIntent.putExtra("Adresse",edit_Adresse.getText().toString());
                        returnIntent.putExtra("CodePostale",edit_CodePostale.getText().toString());
                        returnIntent.putExtra("Telephone",edit_Telephone.getText().toString());
                        returnIntent.putExtra("Type",choixType);
                        returnIntent.putExtra("Ville",edit_Ville.getText().toString());

                        setResult(FormulaireActivity.RESULT_OK,returnIntent);

                    }
                    else
                        setResult(FormulaireActivity.RESULT_CANCELED,returnIntent);
                    finish();
                }
            }
        });

        Button annuler = (Button) findViewById(R.id.button_annuler);

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();

                setResult(FormulaireActivity.RESULT_CANCELED,returnIntent);
                finish();

            }
        });
    }

    private boolean saveInBDD(String nom, String adresse, String codePostale, String telephone, String type, String ville)
    {
        datasource.open();
        //On vérifie que la ville n'existe pas deja dans la BDD
        Ville tmpVille = datasource.getVilleByName(ville);
        if(tmpVille == null)
        {
            String tmp ="";
            codePostale = codePostale.trim();
            if(codePostale.length() > 4)
                tmp = codePostale.substring(0,2);
            else if (codePostale.length() == 4)
                tmp = codePostale.substring(0,1);
            else {
                datasource.close();
                return false;
            }
            tmpVille = datasource.createVille(tmp, ville);
        }
        TypeBatiment typeBatiment = datasource.getTypeBatimentByName(type);
        datasource.createBatiment(typeBatiment.getId(),tmpVille.getId(),latitude,longitude,nom,adresse,telephone);

        datasource.close();


        return true;
    }

    private boolean verifierEditTextNonVide()
    {

        String messageErreur = "Ce champ doit être rempli.";
        EditText edit_Nom = (EditText) findViewById(R.id.edit_Nom);
        EditText edit_Adresse = (EditText) findViewById(R.id.edit_Adresse);
        EditText edit_CodePostale = (EditText) findViewById(R.id.edit_CodePostale);
        EditText edit_Telephone = (EditText) findViewById(R.id.edit_Telephone);
        Spinner edit_Type = (Spinner) findViewById(R.id.edit_Type); //TODO la liste des type :D
        EditText edit_Ville = (EditText) findViewById(R.id.edit_Ville);


        String str = edit_Nom.getText().toString();
        if(TextUtils.isEmpty(str)) {
            edit_Nom.setError(messageErreur);
            return false;
        }

        str = edit_Adresse.getText().toString();
        if(TextUtils.isEmpty(str)) {
            edit_Adresse.setError(messageErreur);
            return false;
        }

        str = edit_CodePostale.getText().toString();
        if(TextUtils.isEmpty(str)) {
            edit_CodePostale.setError(messageErreur);
            return false;
        }

        str = edit_Telephone.getText().toString();
        if(TextUtils.isEmpty(str)) {
            edit_Telephone.setError(messageErreur);
            return false;
        }

        str = edit_Ville.getText().toString();
        if(TextUtils.isEmpty(str)) {
            edit_Ville.setError(messageErreur);
            return false;
        }

        str= choixType;
        if(TextUtils.isEmpty(str))
        {
            TextView errorText = (TextView)edit_Type.getSelectedView();
            errorText.setError(messageErreur);
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            return false;

        }

        return true;
    }
}
