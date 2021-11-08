package uk.ac.ed.inf;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;

public class Location
{
	private String loc;
	private final String hostname = "localhost";
	private final String port     = "9898";
	public Location(String input)
	{
		this.loc = concatLoc(input);
	}
	
	/**
	 *  Connect to the server and return longlat obj
	 *  @return LongLat obj 
 	 **/
	public ArrayList<Details> getLoc()
	{
		Request getLocation = new Request(hostname, port, loc);	
		ArrayList<Details> details = toListLoc(getLocation);
		return details;
	}
	
	private ArrayList<Details> toListLoc(Request getLocation)
	{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Shop>>(){}.getType();
		ArrayList<Details> detail = gson.fromJson(getLocation.requestAccess(), listType);

		
		return detail;
	}

	private String concatLoc(String input)
	{
		String result = "/words";
		String[] temp = input.split("\\.");
		for (int i = 0;i < temp.length;i++)
		{
			result += "/";
			result += temp[i];
		}
		return result;
		
	}
}
