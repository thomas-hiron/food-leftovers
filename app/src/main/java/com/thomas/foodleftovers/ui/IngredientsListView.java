package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.Toast;

import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.adapters.IngredientsAdapter;
import com.thomas.foodleftovers.async_tasks.LoadOutpanIngredient;
import com.thomas.foodleftovers.interfaces.listeners.OnIngredientRequestComplete;
import com.thomas.foodleftovers.popo.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère la liste des ingrédients
 */
public class IngredientsListView extends ListView implements OnIngredientRequestComplete
{
    private IngredientsAdapter mAdapter;
    private List<String> mTextList;

    public IngredientsListView(Context context)
    {
        super(context);
    }

    public IngredientsListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public IngredientsListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        /* Init list */
        mTextList = new ArrayList<>();

        /* Ajout de l'adapter */
        mAdapter = new IngredientsAdapter(getContext());
        setAdapter(mAdapter);
    }

    /**
     * Ajoute un ingrédient dans la liste en premier depuis le EditText
     *
     * @param text Le nom de l'ingrédient
     */
    public void addIngredientFromInput(String text)
    {
        text = text.trim();
        if (!text.equals("") && !mTextList.contains(text.toLowerCase()))
        {
            /* Instanciation de l'ingrédient */
            Ingredient ingredient = new Ingredient();
            ingredient.setText(text);

            mAdapter.insert(ingredient, 0);
            mAdapter.notifyDataSetChanged();

            /* Ajout à la liste */
            mTextList.add(text.toLowerCase());
        }
    }

    /**
     * Ajoute un ingrédient récupéré avec le code barre
     *
     * @param barcode Le code barre
     */
    public void addIngredientFromBarcode(String barcode)
    {
        if (!mTextList.contains(barcode))
        {
            /* Instanciation de l'ingrédient */
            Ingredient ingredient = new Ingredient();
            ingredient.setText(getResources().getString(R.string.adding_ingredient));
            ingredient.setBarcode(barcode);

            mAdapter.insert(ingredient, 0);
            mAdapter.notifyDataSetChanged();

            /* Ajout à la liste */
            mTextList.add(barcode);

            /* Chargement en tâche de fond */
            new LoadOutpanIngredient(this, ingredient).execute(barcode);
        }
    }

    @Override
    public void onIngredientRequestComplete(Ingredient ingredient)
    {
        if (ingredient.getText() != null)
        {
            /* Modification du texte */
            ingredient.setText(ingredient.getText());

            /* Update view */
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            /* Affichage d'un toast */
            String error = getContext().getResources().getString(R.string.ingredient_not_found);
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();

            /* Suppression de l'ingrédient de l'adapter */
            mAdapter.remove(ingredient);
            mAdapter.notifyDataSetChanged();
        }
    }
}
