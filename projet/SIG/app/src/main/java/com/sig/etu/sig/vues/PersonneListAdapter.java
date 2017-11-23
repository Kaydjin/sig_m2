package com.sig.etu.sig.vues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.modeles.Personne;


import java.util.List;

public class PersonneListAdapter extends ArrayAdapter<Personne> {

    public PersonneListAdapter(Context context, List<Personne> entries) {
        super(context, 0, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_personne,parent, false);
        }

        PersonneViewHolder viewHolder = (PersonneViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PersonneViewHolder();
            viewHolder.nom = convertView.findViewById(R.id.nom);
            viewHolder.adresse = convertView.findViewById(R.id.adresse);
            viewHolder.nom_batiment_travail = convertView.findViewById(R.id.nom_batiment_travail);
            viewHolder.nom_metier = convertView.findViewById(R.id.nom_metier);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Personne personne = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nom.setText(personne.getNom());
        viewHolder.adresse.setText(personne.getAdresse());
        viewHolder.nom_batiment_travail.setText(personne.getNom_batiment_travail());
        viewHolder.nom_metier.setText(personne.getNom_metier());

        return convertView;
    }

    private class PersonneViewHolder {
        public TextView nom;
        public TextView adresse;
        public TextView nom_batiment_travail;
        public TextView nom_metier;
    }

}