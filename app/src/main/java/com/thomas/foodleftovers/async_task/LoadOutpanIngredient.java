package com.thomas.foodleftovers.async_task;

import android.os.AsyncTask;
import android.util.Log;

import com.thomas.foodleftovers.MainActivity;
import com.thomas.foodleftovers.adapters.IngredientsAdapter;
import com.thomas.foodleftovers.popo.Ingredient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Effectue une reqûete API
 */
public class LoadOutpanIngredient extends AsyncTask<String, Integer, String>
{
    private static final String OUTPAN_URL = "https://api.outpan.com/v2/products/%s?apikey=%s";
    private static final String OUTPAN_KEY = "3ab74e079cd619e61496b2ee0789face";

    private final IngredientsAdapter mAdapter;
    private final Ingredient mIngredient;

    public LoadOutpanIngredient(Ingredient ingredient, IngredientsAdapter adapter)
    {
        mIngredient = ingredient;
        mAdapter = adapter;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        HttpsURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();
        try
        {
            URL url = new URL(String.format(OUTPAN_URL, strings[0], OUTPAN_KEY));
            urlConnection = (HttpsURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e(MainActivity.APP_TAG, "Une erreur s'est produite lors de la requête à Outpan");
        }
        finally
        {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return result.toString();
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
