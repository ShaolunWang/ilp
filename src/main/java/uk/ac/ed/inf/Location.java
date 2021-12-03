package uk.ac.ed.inf;


import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

/**
 * A class that can get what 3 words locations
 */
public class Location
{
	private final String loc;
	private final Details detail;
	private final String hostname;
	private final String port ;
	public Location(String input, String hostname, String port)
	{
		this.loc = concatLoc(input);
		this.port = port;
		this.hostname = hostname;
		this.detail = getLoc();
	}
	
	/**
	 *  Constructing the this.detail object in the class
	 *  Connect to the server and returns a LongLat object
	 *  @return LongLat obj 
 	 **/
	public Details getLoc()
	{

		Request getLocation = new Request(hostname, port, loc);
		return toDetailLoc(getLocation);
	}

	/**
	 * Convert a JsonString to a Detail object
	 * @param getLocation a Request object that handles the request of getting location from the server
	 * @return a Detail object
	 */
	private Details toDetailLoc(@NotNull Request getLocation)
	{
		Gson gson = new Gson();
		return gson.fromJson(getLocation.requestAccessHttp(), Details.class);
	}

	/**
	 *	Concatenate input into a valid location string
	 * @param input location of the file being accessed
	 * @return a string that represents the location
	 */
	private @NotNull String concatLoc(@NotNull String input)
	{
		// construct request

		StringBuilder result = new StringBuilder("/words");
		String[] temp = input.split("\\.");
		for (String s : temp)
		{
			result.append("/");
			result.append(s);
		}
		result.append("/details.json");
		return result.toString();
	}

	/**
	 * getter function of Detail object
	 * @return a Detail object
	 */
	public Details getDetails()
	{
		return this.detail;
	}

}
