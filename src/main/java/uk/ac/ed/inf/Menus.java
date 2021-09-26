package uk.ac.ed.inf;
import com.google.gson.JsonArray;

public class Menus
{
	String hostname;
	String port;
	int cost;

	public Menus(String hostname, String port)
	{
		this.hostname = hostname;
		this.port     = port;
		this.cost     = 50; // only do += on this
	}

	/**
	 * Find the price of given food order
	 * First send requesting access to the webserver
	 * Then use JsonParsing module to parse the content
	 * @param food arbitrary amount of food names
	 * @return the cost of all the food
	 */
	public int getDeliveryCost(String ...food)
	{

		Request getMenus = new Request(hostname, port, "/menus/menus.json");
		JsonArray shopArray = getMenus.requestAccess();
		JsonParsing parserArray = new JsonParsing(shopArray);

		for (String s : food)
		{
			//fetch their price
			cost = cost + parserArray.parseJsonArrayMenu(s, "pence");
		}
		return cost;
	}
}
