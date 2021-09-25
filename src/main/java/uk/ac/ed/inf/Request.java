package uk.ac.ed.inf;


import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
/* The idea of this module is to handle file fetching from server to client
 * 1. send a "GET /x/x.json" to the server
 * 2. Get Json Array; 
*/

public class Request
{
	// hostname and port
	//
	String hostname;
	String port;
	String location;

	public Request(String hostname, String port, String location)
	{
		this.hostname = hostname;
		this.port     = port;
		this.location = location;
	}

	/**
	 *	@return JsonArray if successfully fetched.
	 * 	@throws IOException if failed to read file.
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
			System.out.println("Connected.\nParsing file...");

			//
			JsonParser parser = new JsonParser(); //from gson
			JsonElement elem = parser.parse(
					new InputStreamReader((InputStream) fileRequest.getContent()));

			return elem.getAsJsonArray();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}

		return null;
	}

}
