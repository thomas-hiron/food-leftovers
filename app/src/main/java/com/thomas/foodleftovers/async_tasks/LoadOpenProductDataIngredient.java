package com.thomas.foodleftovers.async_tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.thomas.foodleftovers.MainActivity;
import com.thomas.foodleftovers.interfaces.listeners.OnIngredientRequestComplete;
import com.thomas.foodleftovers.json_parsers.OpenProductDataParser;
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
 * Effectue une requête API chez Open Product Data
 * https://pod.opendatasoft.com/explore/
 */
public class LoadOpenProductDataIngredient extends AsyncTask<Long, Integer, String>
{
    private final static String URL = "https://pod.opendatasoft.com/api/records/1.0/search/?dataset=%s&q=%s";
    /* Infos sur les produits */
    private final static String GTIN_PRODUCT = "pod_gtin";
    /* Infos sur la nutrition */
    private final static String GTIN_NUTRITION_INFO = "pod_nutrition_us";

    private final OnIngredientRequestComplete mListener;
    private final Ingredient mIngredient;

    public LoadOpenProductDataIngredient(OnIngredientRequestComplete listener, Ingredient ingredient)
    {
        mListener = listener;
        mIngredient = ingredient;
    }

    @Override
    protected String doInBackground(Long... longs)
    {
        HttpsURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();
        try
        {
            java.net.URL url = new URL(String.format(URL, GTIN_PRODUCT, longs[0]));
            urlConnection = (HttpsURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null)
                    result.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e(MainActivity.APP_TAG, "Une erreur s'est produite lors de la requête à Open Food Facts");
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

        /* Parse de la réponse */
        String ingredientName = OpenProductDataParser.PARSE_NAME(s);

        /* Ajout du nom */
        mIngredient.setName(ingredientName);

        mListener.onIngredientRequestComplete(mIngredient);
    }
}
