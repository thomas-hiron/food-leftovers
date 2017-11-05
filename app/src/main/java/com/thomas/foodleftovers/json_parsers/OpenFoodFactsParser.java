package com.thomas.foodleftovers.json_parsers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Parse la réponse de Open Food Facts
 */
public class OpenFoodFactsParser
{
    public static String PARSE_NAME(String string)
    {
        String result = null;
        try
        {
            /* Parse complet */
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.getInt("status") == 1)
            {
                String product = jsonObject.getString("product");

                /* Parse du produit */
                JSONObject productJsonObject = new JSONObject(product);
                result = productJsonObject.getString("product_name_fr").replace("\\", "");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return result;
    }
}
