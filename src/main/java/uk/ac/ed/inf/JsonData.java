package uk.ac.ed.inf;

import java.util.HashMap;
import java.util.TreeMap;

public abstract class JsonData
{
    public HashMap<String,String> data = new HashMap<String,String>();
    public String rawData;

    public JsonData(String rawData)
    {
        this.rawData = rawData;
    }
    abstract void printTree();

}