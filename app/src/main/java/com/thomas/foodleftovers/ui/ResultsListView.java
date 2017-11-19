package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.thomas.foodleftovers.adapters.ResultsAdapter;

/**
 * Gère la liste des résultats
 */
public class ResultsListView extends ListView
{
    private ResultsAdapter mAdapter;

    public ResultsListView(Context context)
    {
        super(context);
    }

    public ResultsListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ResultsListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        /* Ajout de l'adapter */
        mAdapter = new ResultsAdapter(getContext());
        setAdapter(mAdapter);
    }
}
