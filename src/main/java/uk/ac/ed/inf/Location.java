package uk.ac.ed.inf;


public class Location
{
	private String loc;
	private String hostname = "localhost"
	private Strnig port     = "9898" 
	public Location(String input)
	{
		String loc = concatLoc(input);	
	}
	
	/**
	 *  Connect to the server and return longlat obj
	 *  @return LongLat obj 
 	 **/
	public String getLoc()
	{
		Request getLocation = new Request(hostname, port, loc);	
		ArrayList<Details> details = toListLoc(getLocation);

	}
	
	private ArrayList<Details> toListLoc(Request getLocation)
	{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Shop>>(){}.getType();
		ArrayList<Details> details = gson.fromJson(getLocation.requestAccess(), listType);

		for ()
		
		
		return details;
	}

	private String concatLoc(String input)
	{
		String result = "/words";
		String temp = input.split("[.]", 0);
		for (word : temp)
		{
			result += "/" + word;
		}
		return result;
		
	}
}
