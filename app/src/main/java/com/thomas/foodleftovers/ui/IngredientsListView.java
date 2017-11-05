package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.thomas.foodleftovers.adapters.IngredientsAdapter;
import com.thomas.foodleftovers.popo.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère la liste des ingrédients
 */
public class IngredientsListView extends ListView
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
     * Ajoute un ingrédient dans la liste en premier
     *
     * @param text Le nom de l'ingrédient
     */
    public void addIngredient(String text)
    {
        text = text.trim();
        if (!text.equals("") && !mTextList.contains(text.toLowerCase()))
        {
            /* Instanciation de l'ingrédient */
            Ingredient ingredient = new Ingredient();
            ingredient.setText(text);

            /* Instanciation de l'ingrédient */
            mAdapter.insert(ingredient, 0);
            mAdapter.notifyDataSetChanged();

            /* Ajout à la liste */
            mTextList.add(text.toLowerCase());
        }
    }
}
