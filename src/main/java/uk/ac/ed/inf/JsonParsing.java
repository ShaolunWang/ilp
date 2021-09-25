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

    public Integer parseJsonArrayMenu(String name, String pence)
    {
        try
        {
            System.out.println(fileArray.size());
            for (int i = 0; i < fileArray.size();i++)
            {
                JsonObject curr_shop = fileArray.get(i).getAsJsonObject();
                System.out.println(curr_shop);

                //Class cls = fileArray.get(i).getClass();
                //System.out.println("The type of the object is: " + cls.getName());
                JsonArray menu = curr_shop.get("menu").getAsJsonArray();
                for (int j = 0; j < menu.size();j++)
                {
                    JsonObject entries = menu.get(j).getAsJsonObject();
                    if (entries.get("item").getAsString().equals(name))
                    {
                        return entries.get(pence).getAsInt();
                    }
                }
            }

        }
        catch (Exception NullPointerException)
        {
            System.out.println(NullPointerException);
        }


        return null;
    }
}

