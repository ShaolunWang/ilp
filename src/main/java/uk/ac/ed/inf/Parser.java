package uk.ac.ed.inf;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 * This is the helper class that parses Json files.
 * It uses Gson's `JsonArray` and `JsonObject` for parsing.
 */
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
     * @param foods the ArrayList of foods ordered
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

    /**
     * public method to get list of items that's being searched already
     * used for optimizing by not searching items
     * that's already been added to cost
     * @return list of index of the items that's being removed
     */
    public ArrayList<Integer> getRemoved()
    {
        return removed;
    }


}

