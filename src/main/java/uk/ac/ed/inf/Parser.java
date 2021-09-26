package uk.ac.ed.inf;

import com.google.gson.JsonArray;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Parser
{
    private final JsonArray jsonEntry;
    private final ArrayList<Integer> removed;
    public Parser(JsonArray jsonEntry)
    {
        this.jsonEntry = jsonEntry;
        this.removed = new ArrayList<>();
    }

    /**
     * This is a helper function for parsing the total price of the food ordered
     * It only returns the price of the food
     * that's currently being parsed
     * @return the price of the current food
     */

    public Integer menuParser(ArrayList<String> foods)
    {
        int foodCost = 0;

        for (int i = 0;i < jsonEntry.size();i++)
        {
            JsonObject entries = jsonEntry.get(i).getAsJsonObject();

            for (int j = 0; j < foods.size();j++)
            {
                if (entries.get("item").getAsString().equals(foods.get(j)))
                {
                    //if found then add it up to the result;
                    removed.add(j);
                    foodCost += entries.get("pence").getAsInt();
                }
            }
        }
        return foodCost;
    }
    public ArrayList<Integer> getRemoved()
    {
        return removed;
    }


}

