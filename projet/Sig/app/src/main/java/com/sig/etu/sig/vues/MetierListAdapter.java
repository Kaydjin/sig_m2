package com.sig.etu.sig.vues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.modeles.Metier;

import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class MetierListAdapter extends ArrayAdapter<Metier> {

    public MetierListAdapter(Context context, List<Metier> entries) {
        super(context, 0, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_metier,parent, false);
        }

        MetierViewHolder viewHolder = (MetierViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MetierViewHolder();
            viewHolder.nom = convertView.findViewById(R.id.nom);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Metier metier = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nom.setText(metier.getNom());

        return convertView;
    }

    private class MetierViewHolder {
        public TextView nom;
    }

}
