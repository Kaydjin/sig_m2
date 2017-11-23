package com.sig.etu.sig.vues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.modeles.Batiment;

import java.util.List;

public class BatimentListAdapter extends ArrayAdapter<Batiment> {

    public BatimentListAdapter(Context context, List<Batiment> entries) {
        super(context, 0, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_batiment,parent, false);
        }

        BatimentViewHolder viewHolder = (BatimentViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new BatimentViewHolder();
            viewHolder.nom = convertView.findViewById(R.id.nom);
            viewHolder.adresse = convertView.findViewById(R.id.adresse);
            viewHolder.latitude = convertView.findViewById(R.id.latitude);
            viewHolder.longitude = convertView.findViewById(R.id.longitude);
            viewHolder.telephone = convertView.findViewById(R.id.telephone);
            viewHolder.type = convertView.findViewById(R.id.type);
            viewHolder.ville = convertView.findViewById(R.id.ville);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Batiment batiment = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nom.setText(batiment.getNom());
        viewHolder.adresse.setText(batiment.getAdresse());
        viewHolder.latitude.setText(((Double)batiment.getLatitude()).toString());
        viewHolder.longitude.setText(((Double)batiment.getLongitude()).toString());
        viewHolder.telephone.setText(batiment.getTelephone());
        viewHolder.type.setText(batiment.getType());
        viewHolder.ville.setText(batiment.getVille());

        return convertView;
    }

    private class BatimentViewHolder {
        public TextView latitude;
        public TextView longitude;
        public TextView telephone;
        public TextView nom;
        public TextView adresse;
        public TextView type;
        public TextView ville;
    }

}