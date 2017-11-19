package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.thomas.foodleftovers.adapters.ReceipesAdapter;

/**
 * Gère la liste des résultats
 */
public class ReceipesListView extends ListView
{
    private ReceipesAdapter mAdapter;

    public ReceipesListView(Context context)
    {
        super(context);
    }

    public ReceipesListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ReceipesListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        /* Ajout de l'adapter */
        mAdapter = new ReceipesAdapter(getContext());
        setAdapter(mAdapter);
    }
}
