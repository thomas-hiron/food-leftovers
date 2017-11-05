package com.thomas.foodleftovers.async_task;

import android.os.AsyncTask;

import com.thomas.foodleftovers.adapters.IngredientsAdapter;
import com.thomas.foodleftovers.popo.Ingredient;

/**
 * Effectue une reqûete API
 */
public class LoadIngredient extends AsyncTask<String, Integer, String>
{
    private final IngredientsAdapter mAdapter;
    private final Ingredient mIngredient;

    public LoadIngredient(Ingredient ingredient, IngredientsAdapter adapter)
    {
        mIngredient = ingredient;
        mAdapter = adapter;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        return "ça doit marcher !";
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        /* Modification du texte */
        mIngredient.setText(s);

        /* Update view */
        mAdapter.notifyDataSetChanged();
    }
}
