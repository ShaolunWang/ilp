package uk.ac.ed.inf;

import com.google.gson.JsonArray;

import java.util.ArrayList;

import com.google.gson.JsonObject;

public class JsonParsing
{
    JsonArray fileArray;
    public JsonParsing(JsonArray fileArray)
    {
        this.fileArray = fileArray;
    }

    /**
     * This method is designed for parsing menus
     * structure of menus: JsonArray of JsonObjects
     * And the JsonObjects has JsonArrays mapped to its names
     * @param name Name of the food ordered
     * @param pence price of the food ordered in pence
     * @return pence of the food
     */
    public Integer parseJsonArrayMenu(String ...food)
    {
        try
        {
			for (name : food)
			{
				food_price = 0; 
				for (int i = 0; i < fileArray.size();i++)
            	{
        			JsonObject curr_shop = fileArray.get(i).getAsJsonObject();
	
    	       		JsonArray menu = curr_shop.get("menu").getAsJsonArray();
				}
                for (int j = 0; j < menu.size();j++)
                {
                    JsonObject entries = menu.get(j).getAsJsonObject();
                    if (entries.get("item").getAsString().equals(name))
                    {
						//if found then return the result;
                        food_price += entries.get("pence").getAsInt();
                    }
                }

			}	
			return food_price;

        }
        catch (Exception e)
        {
            System.out.println(e);
        }


        return null;
    }
}

