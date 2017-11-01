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

        IngredientsAdapter adapter = new IngredientsAdapter(getContext());
        setAdapter(adapter);
    }
}
