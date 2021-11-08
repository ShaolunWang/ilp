package uk.ac.ed.inf;


import com.google.gson.Gson;

public class Location
{
	private final String loc;
	private Details detail;
	public Location(String input)
	{
		this.loc = concatLoc(input);
		this.detail = getLoc();
	}
	
	/**
	 *  Connect to the server and return longlat obj
	 *  @return LongLat obj 
 	 **/
	public Details getLoc()
	{
		String hostname = "localhost";
		String port = "9898";
		Request getLocation = new Request(hostname, port, loc);
		return toDetailLoc(getLocation);
	}
	
	private Details toDetailLoc(Request getLocation)
	{
		Gson gson = new Gson();
		//Type listType = new TypeToken<List<Details>>(){}.getType();
		return gson.fromJson(getLocation.requestAccess(), Details.class);
	}

	private String concatLoc(String input)
	{

		StringBuilder result = new StringBuilder("/words");
		String[] temp = input.split("\\.");
		for (String s : temp)
		{
			result.append("/");
			result.append(s);
		}
		result.append("/details.json");
		System.out.println(result.toString());
		return result.toString();
		
	}

	public void testPrint()
	{
		System.out.println(detail.country);
	}

}
