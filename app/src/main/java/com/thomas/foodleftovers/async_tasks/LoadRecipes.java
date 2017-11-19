package com.thomas.foodleftovers.async_tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.thomas.foodleftovers.parsers.html.MarmitonParser;
import com.thomas.foodleftovers.popo.Ingredient;
import com.thomas.foodleftovers.popo.Recipe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Charge les recettes
 */
public class LoadRecipes extends AsyncTaskLoader<ArrayList<Recipe>>
{
    private static final String URL = "http://www.marmiton.org/recettes/recherche.aspx?aqt=%s";
    private ArrayList<Ingredient> mIngredients;
    ArrayList<Recipe> mRecipes;

    public LoadRecipes(Context context, ArrayList<Ingredient> ingredients)
    {
        super(context);
        mIngredients = ingredients;
    }

    @Override
    protected void onStartLoading()
    {
        if (mRecipes != null) {
            deliverResult(mRecipes);
        }
        else {
            forceLoad();
        }
    }

    @Override
    public ArrayList<Recipe> loadInBackground()
    {
        StringBuilder query = new StringBuilder();
        for (Ingredient ingredient : mIngredients) {
            query.append(ingredient.getName()).append(" ");
        }

        String queryJoined = query.toString().trim();
        String queryEncoded = null;
        try {
            queryEncoded = URLEncoder.encode(queryJoined, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /* Requête */
        ArrayList<Recipe> recipes = new ArrayList<>();
        if(queryEncoded != null)
        {
            Document doc = null;
            try {
                doc = Jsoup.connect(String.format(URL, queryEncoded)).get();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            recipes = MarmitonParser.PARSE_HTML(doc);
        }

        return recipes;
    }
}
