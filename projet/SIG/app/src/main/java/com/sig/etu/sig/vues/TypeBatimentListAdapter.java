package com.sig.etu.sig.vues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sig.etu.sig.R;
import com.sig.etu.sig.modeles.TypeBatiment;

import java.util.List;

/**
 * Created by vogel on 23/11/17.
 */

public class TypeBatimentListAdapter extends ArrayAdapter<TypeBatiment> {

    public TypeBatimentListAdapter(Context context, List<TypeBatiment> entries) {
        super(context, 0, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_type_batiment,parent, false);
        }

        TypeBatimentListAdapter.TypeBatimentViewHolder viewHolder = (TypeBatimentListAdapter.TypeBatimentViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TypeBatimentListAdapter.TypeBatimentViewHolder();
            viewHolder.type = convertView.findViewById(R.id.type);
            viewHolder.description = convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        TypeBatiment typeBatiment = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.type.setText(typeBatiment.getType());
        viewHolder.description.setText(typeBatiment.getDescription());

        return convertView;
    }

    private class TypeBatimentViewHolder {
        public TextView type;
        public TextView description;
    }
}
