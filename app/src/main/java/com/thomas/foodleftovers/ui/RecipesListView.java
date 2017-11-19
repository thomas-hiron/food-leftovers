package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.thomas.foodleftovers.adapters.RecipesAdapter;

/**
 * Gère la liste des résultats
 */
public class RecipesListView extends ListView
{
    private RecipesAdapter mAdapter;

    public RecipesListView(Context context)
    {
        super(context);
    }

    public RecipesListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RecipesListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        /* Ajout de l'adapter */
        mAdapter = new RecipesAdapter(getContext());
        setAdapter(mAdapter);
    }
}
