package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.thomas.foodleftovers.adapters.IngredientsAdapter;

/**
 * Gère la liste des ingrédients
 */
public class IngredientsListView extends ListView
{
    private IngredientsAdapter mAdapter;

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

        /* Ajout de l'adapter */
        mAdapter = new IngredientsAdapter(getContext());
        setAdapter(mAdapter);
    }

    /**
     * Ajoute un ingrédient dans la liste en premier
     *
     * @param ingredient Le nom de l'ingrédient
     */
    public void addIngredient(String ingredient)
    {
        if (!ingredient.trim().equals(""))
        {
            mAdapter.insert(ingredient, 0);
            mAdapter.notifyDataSetChanged();
        }
    }
}
