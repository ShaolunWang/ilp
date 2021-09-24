package uk.ac.ed.inf;


import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.*;
import java.io.IOException;
import java.io.InputStream;
/* The idea of this module is to handle file fetching from server to client
 * 1. send a "GET /x/x.json" to the server
 * 2. recieve the string
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

	public JsonObject socketOut()
	{
		InputStream file;
		String loc = "http://"+ hostname + ":" + port + location;
		try
		{
			URL url = new URL(loc);
			URLConnection fileRequest = url.openConnection();
		    fileRequest.connect();
			System.out.println("Connected.");

			JsonParser parser = new JsonParser(); //from gson
			JsonElement elem = parser.parse(
					new InputStreamReader((InputStream) fileRequest.getContent()));


			return elem.getAsJsonObject();


			//InputStreamReader	new InputStreamReader()
		}
		catch(Exception IOExceptions)
		{
			System.out.println("kjsdlkjafls;");
			System.out.println(IOExceptions);

			System.exit(0) ;
		}

		return null;
	}

}
