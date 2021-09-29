package uk.ac.ed.inf;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  This is the module for menu access.
 *  It uses URL and Gson for request and parsing.
 */
public class Menus
{
	private final String hostname;
	private final String port;
	private int cost;

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
	 * @param order arbitrary amount of food ordered
	 * @return the cost of all the food
	 */
	public int getDeliveryCost(String ...order)
	{
		Request getMenus = new Request(hostname, port, "/menus/menus.json");
		JsonArray shopArray = getMenus.requestAccess();
		ArrayList<String> foods = new ArrayList<>(Arrays.asList(order));
		ArrayList<Integer> removed;

		try
		{
			for (int i = 0; i < shopArray.size();i++)
			{

				JsonObject curr_shop = shopArray.get(i).getAsJsonObject();
				Parser menu = new Parser(curr_shop.get("menu").getAsJsonArray());
				//cost for a single food in the order

				cost += menu.menuParser(foods);

				//clean up the foods

				removed = menu.getRemoved();
				if (foods.size() != 0)
				{
					for (Integer j : removed)
						foods.remove(j);
				}
				if (removed.size() != 0)
					removed.clear();
			}


		}
		catch (NullPointerException e)
        {
            System.out.println("Exception thrown: " + e);
        }
		return cost;
	}

}
