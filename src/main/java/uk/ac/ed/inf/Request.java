package uk.ac.ed.inf;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStream;

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
	 *  using URL for access and fetch the inputstream
	 *  return the result in JsonArray form
	 *	@return JsonArray if successfully fetched.
	 */
	public JsonArray requestAccess()
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
			JsonElement elem = parser.parse(
					new InputStreamReader((InputStream) fileRequest.getContent()));

			return elem.getAsJsonArray();
		}
		catch(IOException e)
		{
			System.out.println("Exception catched: " + e);
		}

		return null;
	}

}
