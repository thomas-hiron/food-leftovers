package com.thomas.foodleftovers.async_tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.thomas.foodleftovers.popo.Ingredient;

import java.util.ArrayList;

/**
 * Charge les recettes
 */
public class LoadReceipes extends AsyncTaskLoader<ArrayList<String>>
{
    private ArrayList<Ingredient> mIngredients;
    ArrayList<String> mReceipes;

    public LoadReceipes(Context context, ArrayList<Ingredient> ingredients)
    {
        super(context);
        mIngredients = ingredients;
    }

    @Override
    protected void onStartLoading()
    {
        if (mReceipes != null)
            deliverResult(mReceipes);
        else
            forceLoad();
    }

    @Override
    public ArrayList<String> loadInBackground()
    {
        mReceipes = new ArrayList<>();
        for (Ingredient ingredient : mIngredients)
        {
            mReceipes.add(ingredient.getName());
        }

        return mReceipes;
    }
}
