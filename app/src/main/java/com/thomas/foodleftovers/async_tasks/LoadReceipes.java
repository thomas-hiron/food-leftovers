package com.thomas.foodleftovers.async_tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Charge les recettes
 */
public class LoadReceipes extends AsyncTaskLoader<ArrayList<String>>
{
    ArrayList<String> mReceipes;

    public LoadReceipes(Context context)
    {
        super(context);
    }

    @Override
    protected void onStartLoading()
    {
        if(mReceipes != null)
            deliverResult(mReceipes);
        else
            forceLoad();
    }

    @Override
    public ArrayList<String> loadInBackground()
    {
        mReceipes = new ArrayList<>();
        mReceipes.add("Result 1");
        mReceipes.add("Result 2");
        mReceipes.add("Result 3");

        return mReceipes;
    }
}
