package uk.ac.ed.inf;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
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

	public String socketOut()
	{
		InputStream file;
		String loc = "http://"+ hostname + ":" + port + location;
		try
		{

			URL url = new URL(loc);
			URLConnection fileRequest = url.openConnection();
		    fileRequest.connect();
			System.out.println("Connected.");
			
			file = (InputStream) fileRequest.getContent();
			DataInputStream dataInputStream = new DataInputStream(file);
			System.out.println("kasdjf;sjd");
			while (file.read() != -1)
				System.out.println(file.);
			//InputStreamReader	new InputStreamReader()

		}

		catch(Exception IOExceptions)
		{
			System.out.println("kjsdlkjafls;");
			System.out.println(IOExceptions);
		}
		return "a";
	}

}
