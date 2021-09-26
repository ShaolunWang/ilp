package uk.ac.ed.inf;

import com.google.gson.JsonArray;

import com.google.gson.JsonObject;

public class Parser
{
    JsonArray jsonEntry;
    public Parser(JsonArray jsonEntry)
    {
        this.jsonEntry = jsonEntry;
    }

    /**
     * This is a helper function for parsing the total price of the food ordered
     * It only returns the price of the food
     * that's currently being parsed
     * @param menu Entry of the current menu of the shop, form: jsonArray
     * @param name the name of the current food
	 * @param index the name of the entry, e.g. "pence"
     * @return the price of the current food
     */

    public Integer menuParser(String name,String index)
    {
        for (int j = 0; j < jsonEntry.size();j++)
        {
            JsonObject entries = jsonEntry.get(j).getAsJsonObject();
            if (entries.get("item").getAsString().equals(name))
            {
                //if found then return the result;
                return entries.get(index).getAsInt();
            }

        }
        return -1;
    }
}

