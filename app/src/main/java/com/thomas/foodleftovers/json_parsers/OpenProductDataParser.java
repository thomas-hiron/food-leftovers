package com.thomas.foodleftovers.json_parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Parse la réponse de Open Food Facts
 */
public class OpenProductDataParser
{
    public static String PARSE_NAME(String string)
    {
        String result = null;
        try {
            /* Parse complet */
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.getInt("nhits") > 0) {
                JSONArray recordsArray = new JSONArray(jsonObject.getString("records"));
                JSONObject record = (JSONObject) recordsArray.get(0);

                JSONObject fields = new JSONObject(record.getString("fields"));
                result = fields.getString("gtin_nm");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
