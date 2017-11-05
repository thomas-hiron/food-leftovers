package com.thomas.foodleftovers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.popo.Ingredient;
import com.thomas.foodleftovers.ui.IngredientsListView;

/**
 * L'adapter de la liste des ingrédients
 */
public class IngredientsAdapter extends ArrayAdapter<Ingredient> implements View.OnClickListener
{
    private Button mSearchReceipesButton;
    private LayoutInflater mInflater;

    public IngredientsAdapter(Context context, Button searchReceipesButton)
    {
        super(context, R.layout.ingredients_list);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mSearchReceipesButton = searchReceipesButton;
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
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();

        if(getCount() > 0)
            mSearchReceipesButton.setVisibility(View.VISIBLE);
        else
            mSearchReceipesButton.setVisibility(View.GONE);
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

            /* Suppression */
            remove(getItem(position));
            notifyDataSetChanged();
        }
    }

    /**
     * Si l'adapter contient un ingrédient
     *
     * @param ingredientName Le nom de l'ingrédient à tester
     * @return Si l'ingrédient est déjà dans la liste
     */
    public boolean containsName(String ingredientName)
    {
        for (int i = 0, l = getCount(); i < l; ++i)
        {
            Ingredient ingredient = getItem(i);
            if (ingredient != null && ingredient.getName() != null && ingredient.getName().equalsIgnoreCase(ingredientName))
                return true;
        }

        return false;
    }

    /**
     * Si l'adapter contient un code barre
     *
     * @param barcode Le code barre à tester
     * @return Si le code barre a été trouvé
     */
    public boolean containsBarcode(long barcode)
    {
        for (int i = 0, l = getCount(); i < l; ++i)
        {
            Ingredient ingredient = getItem(i);
            if (ingredient != null && ingredient.getBarcode() != 0 && ingredient.getBarcode() == barcode)
                    return true;
        }

        return false;
    }
}
