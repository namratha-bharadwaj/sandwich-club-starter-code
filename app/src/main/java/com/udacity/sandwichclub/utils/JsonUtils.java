package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            //Get JsonObject from JsonString
            JSONObject jsonObjectFromString = new JSONObject(json);

            //Get the name from the main Object
            JSONObject nameObj = jsonObjectFromString.getJSONObject("name");

            //Get the mainName from nameObj
            String mainName = nameObj.getString("mainName");

            //Get the array of "also_known_as" into a Java ArrayList
            ArrayList<String> alsoKnownAsList = new ArrayList<String>();
            JSONArray alsoKnownAsJsonArray = nameObj.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownAsJsonArray.getString(i));
            }

            //Get the place of origin
            String placeOfOrigin = jsonObjectFromString.getString("placeOfOrigin");

            //Get the description
            String description = jsonObjectFromString.getString("description");

            //Get the URL to the image
            String imageUrl = jsonObjectFromString.getString("image");

            //Get the array of "Ingredients" into a Java ArrayList
            ArrayList<String> ingredientsList = new ArrayList<String>();
            JSONArray ingredientsListJsonArray = jsonObjectFromString.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsListJsonArray.length(); i++) {
                ingredientsList.add(ingredientsListJsonArray.getString(i));
            }

            Sandwich sandwichObj = new Sandwich(mainName, alsoKnownAsList,
                    placeOfOrigin, description, imageUrl, ingredientsList);

            return sandwichObj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
