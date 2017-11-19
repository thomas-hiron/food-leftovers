package com.thomas.foodleftovers.parsers.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Parse la réponse de Outpan
 */
public class OutpanParser
{
    public static String PARSE_NAME(String string)
    {
        String result = null;
        try {
            JSONObject jsonObject = new JSONObject(string);
            result = jsonObject.getString("name");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
