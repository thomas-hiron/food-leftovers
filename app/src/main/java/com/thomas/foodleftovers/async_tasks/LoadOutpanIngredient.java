package com.thomas.foodleftovers.async_tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.thomas.foodleftovers.MainActivity;
import com.thomas.foodleftovers.interfaces.listeners.OnIngredientRequestComplete;
import com.thomas.foodleftovers.parsers.json.OutpanParser;
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
 * Effectue une reqûete API chez Outpan
 * https://outpan.mixnode.com/developers
 */
public class LoadOutpanIngredient extends AsyncTask<Long, Integer, String>
{
    private static final String URL = "https://api.outpan.com/v2/products/%s?apikey=%s";
    private static final String API_KEY = "3ab74e079cd619e61496b2ee0789face";

    private final OnIngredientRequestComplete mListener;
    private final Ingredient mIngredient;

    public LoadOutpanIngredient(OnIngredientRequestComplete listener, Ingredient ingredient)
    {
        mListener = listener;
        mIngredient = ingredient;
    }

    @Override
    protected String doInBackground(Long... longs)
    {
        HttpsURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(String.format(URL, longs[0], API_KEY));
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
        catch (IOException e) {
            e.printStackTrace();
            Log.e(MainActivity.APP_TAG, "Une erreur s'est produite lors de la requête à Outpan");
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        /* Parse de la réponse */
        String ingredientName = OutpanParser.PARSE_NAME(s);

        /* Clean name */
        if (ingredientName.equals("null")) {
            ingredientName = null;
        }

        /* Ajout du nom */
        mIngredient.setName(ingredientName);

        mListener.onIngredientRequestComplete(mIngredient);
    }
}
