package com.sig.etu.sig.vues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.modeles.Ville;

import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class VilleListAdapter extends ArrayAdapter<Ville> {
    public VilleListAdapter(Context context, List<Ville> entries) {
        super(context, 0, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_ville,parent, false);
        }

        VilleViewHolder viewHolder = (VilleViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new VilleViewHolder();
            viewHolder.nom = convertView.findViewById(R.id.nom);
            viewHolder.codepostale = convertView.findViewById(R.id.codepostale);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Ville ville = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nom.setText(ville.getNom());
        viewHolder.codepostale.setText(ville.getCode_postale());

        return convertView;
    }

    private class VilleViewHolder {
        public TextView nom;
        public TextView codepostale;
    }
}
