package test.o2121076.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by o2121076 on 08/01/18.
 */

public class FormulaireActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);
        final Context ctx = getApplicationContext();

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
                    Spinner edit_Type = (Spinner) findViewById(R.id.edit_Type);
                    EditText edit_Ville = (EditText) findViewById(R.id.edit_Ville);

                    returnIntent.putExtra("Nom",edit_Nom.getText().toString());
                    returnIntent.putExtra("Adresse",edit_Adresse.getText().toString());
                    returnIntent.putExtra("CodePostale",edit_CodePostale.getText().toString());
                    returnIntent.putExtra("Telephone",edit_Telephone.getText().toString());
                    //returnIntent.putExtra("Type",edit_Type.get); TODO
                    returnIntent.putExtra("Ville",edit_Ville.getText().toString());

                    setResult(FormulaireActivity.RESULT_OK,returnIntent);
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

        return true;
    }
}
