
package uk.ac.ed.inf;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the module for sending request to the web server
 * and returning a `JsonArray` for any other module that calls it.
 */
public class Request
{
	// hostname and port
	//
	private final String hostname;
	private final String port;
	private final String location;

	public Request(String hostname, String port, String location)
	{
		this.hostname = hostname;
		this.port     = port;
		this.location = location;
	}

	/**
	 *  The idea of this module is to separate request with JsonParsing
	 *  using URL for access and fetch the input stream
	 *  return the result in JsonArray form
	 *	@return JsonArray if successfully fetched.
	 */
	public Reader requestAccess()
	{
		InputStream file;
		String loc = "http://"+ hostname + ":" + port + location;
		try
		{
			// Url request;
			URL url = new URL(loc);
			URLConnection fileRequest = url.openConnection();
			fileRequest.connect(); // fetch the file as InputStream;
			//System.out.println("Connected.\nParsing file...");

			JsonParser parser = new JsonParser(); //from gson
			//JsonElement elem = parser.parse(
			//		new InputStreamReader((InputStream) fileRequest.getContent()));
			InputStreamReader parsed = new InputStreamReader((InputStream) fileRequest.getContent());

			return parsed;
		}
		catch(IOException e)
		{
			System.out.println("Exception caught: " + e);
		}

		return null;
	}

}