package com.thomas.foodleftovers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.popo.Ingredient;
import com.thomas.foodleftovers.ui.IngredientsListView;

/**
 * L'adapter de la liste des ingrédients
 */
public class IngredientsAdapter extends ArrayAdapter<Ingredient> implements View.OnClickListener
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
        if (convertView == null)
            view = mInflater.inflate(R.layout.ingredients_list, parent, false);
        /* On garde la vue transmise */
        else
            view = convertView;

        /* Ajout du nom de l'ingrédient */
        Ingredient ingredient = getItem(position);
        if (ingredient != null)
            ((TextView) view.findViewById(R.id.ingredient_text)).setText(ingredient.getName());

        /* Ajout du listener */
        view.findViewById(R.id.delete_ingredient).setOnClickListener(this);

        /* Retour de la vue */
        return view;
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.delete_ingredient)
        {
            /* Récupération de la position */
            View parentRow = (View) view.getParent();
            IngredientsListView listView = (IngredientsListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);

            /* Suppression dans la liste */
            listView.removeIngredient(getItem(position));

            /* Suppression */
            remove(getItem(position));
            notifyDataSetChanged();
        }
    }
}
