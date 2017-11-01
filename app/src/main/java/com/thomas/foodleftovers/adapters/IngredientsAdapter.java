package com.thomas.foodleftovers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thomas.foodleftovers.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * L'adapter de la liste des ingrédients
 */
public class IngredientsAdapter extends ArrayAdapter<String>
{
    private LayoutInflater mInflater;

    public IngredientsAdapter(Context context)
    {
        super(context, R.layout.ingredients_list);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View view;

        /* Création de la vue */
        if(convertView == null)
            view = mInflater.inflate(R.layout.ingredients_list, parent, false);
        /* On garde la vue transmise */
        else
            view = convertView;

        /* Ajout du nom de l'album */
        ((TextView) view.findViewById(R.id.ingredient_text)).setText(getItem(position));

        /* Retour de la vue */
        return view;
    }
}
