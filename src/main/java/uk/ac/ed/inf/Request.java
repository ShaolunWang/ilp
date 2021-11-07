package uk.ac.ed.inf;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.IOException;

/**
 * This is the module for sending request to the web server
 * and returning a `JsonArray` for any other module that calls it.
 */

//TODO: use httpclient
public class Request
{
	// hostname and port, with location
	private final String hostname;
	private final String port;
	private final String location;
	private static final HttpClient client = HttpClient.newHttpClient();

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
	public String requestAccess()
	{
		String loc = "http://"+ hostname + ":" + port + location;
		try
		{
			HttpRequest request = HttpRequest.newBuilder()
									.uri(URI.create(loc))
									.build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return response.body();
		}
		catch(IOException | InterruptedException e)
		{
			System.out.println("Exception caught: " + e);
		}

		return null;
	}

}
