package com.thomas.foodleftovers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thomas.foodleftovers.R;

/**
 * L'adapter de la liste des ingrédients
 */
public class ResultsAdapter extends ArrayAdapter<String>
{
    private LayoutInflater mInflater;

    public ResultsAdapter(Context context)
    {
        super(context, R.layout.results_list);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View view;

        /* Création de la vue */
        if (convertView == null)
            view = mInflater.inflate(R.layout.results_list, parent, false);
        /* On garde la vue transmise */
        else
            view = convertView;

        /* Ajout du nom de l'ingrédient */
        String result = getItem(position);
        if (result != null)
            ((TextView) view.findViewById(R.id.ingredient_text)).setText(result);

        /* Retour de la vue */
        return view;
    }
}
