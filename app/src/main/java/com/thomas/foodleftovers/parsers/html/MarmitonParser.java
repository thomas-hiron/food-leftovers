package com.thomas.foodleftovers.parsers.html;

import android.util.Log;

import com.thomas.foodleftovers.MainActivity;
import com.thomas.foodleftovers.popo.Recipe;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Parse les résultats de Marmition
 */
public class MarmitonParser
{
    private static final String URL = "http://www.marmiton.org";

    public static ArrayList<Recipe> PARSE_HTML(Document doc)
    {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            Elements recipesElem = doc.select(".recipe-card");
            for (Element recipeElem : recipesElem) {
                Recipe recipe = new Recipe();

                /* Titre */
                recipe.setTitle(recipeElem.selectFirst(".recipe-card__title").html());

                /* Description */
                recipe.setDescription(recipeElem.selectFirst(".recipe-card__description").html());

                /* Image */
                recipe.setPictureUrl(recipeElem.selectFirst(".recipe-card__picture img").attr("src"));

                /* Durée */
                recipe.setDuration(recipeElem.selectFirst(".recipe-card__duration__value").html());

                /* Lien */
                recipe.setLink(URL + recipeElem.attr("href"));

                recipes.add(recipe);
            }
        }
        catch (Exception e) {
            Log.e(MainActivity.APP_TAG, "Une erreur s'est produite lors du parse de marmiton: " + e.getMessage());
        }

        return recipes;
    }
}
