package uk.ac.ed.inf;
import java.util.TreeMap;
import com.google.gson.Gson;

protected final class JsonData
{
	private TreeMap<String,String> data = new TreeMap<String,String>();
	private String rawData;
	public JsonData (String rawData)
	{
		this.rawData = rawData;
		data = gson.fromJson(json, type);
	}

	private void convert()
	{
		Type listType = new TypeToken<List<Shop>>(){}.getType();
		TreeMap<String, SmallGuys> data= gson.fromJson(rawData, type);
	}
	public void printTree()
	{
		System.out.println(data);
	}
}

public class MenuData extends JsonData
{
	public MenuData(String data)
	{
		super(rawData);
	}
}
