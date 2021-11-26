package uk.ac.ed.inf;


import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public class Location
{
	private final String loc;
	private final Details detail;
	private final String hostname;
	private final String port ;
	public Location(String input, String hostname, String port)
	{
		this.loc = concatLoc(input);
		this.port = "9898";
		this.hostname = "localhost";;
		this.detail = getLoc();
	}
	
	/**
	 *  Connect to the server and return longlat obj
	 *  @return LongLat obj 
 	 **/
	public Details getLoc()
	{

		Request getLocation = new Request(hostname, port, loc);
		return toDetailLoc(getLocation);
	}
	
	private Details toDetailLoc(@NotNull Request getLocation)
	{
		Gson gson = new Gson();
		//Type listType = new TypeToken<List<Details>>(){}.getType();
		return gson.fromJson(getLocation.requestAccessHttp(), Details.class);
	}

	private @NotNull String concatLoc(@NotNull String input)
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

}
