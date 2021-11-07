package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class MenuData extends JsonData
{
    public MenuData(String rawData)
    {
        super(rawData);
    }

    public void buildTree()
    {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Shop>>(){}.getType();
        data = gson.fromJson(rawData, listType);
    }

    @Override
    public void printTree()
    {
        System.out.println(data);
    }
}