package uk.ac.ed.inf;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;

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
	 * @param order arbitrary amount of food ordered
	 * @return the cost of all the food
	 */
	public int getDeliveryCost(String ...order)
	{
		Request getMenus = new Request(hostname, port, "/menus/menus.json");
		JsonArray shopArray = getMenus.requestAccess();
		ArrayList<String> foods = new ArrayList<>(Arrays.asList(order));


		try
		{
			for (int i = 0; i < shopArray.size();i++)
			{

				JsonObject curr_shop = shopArray.get(i).getAsJsonObject();
				Parser menu = new Parser(curr_shop.get("menu").getAsJsonArray());
				//cost for a single food in the order

				cost += menu.menuParser(foods);

				ArrayList<Integer> removed = menu.getRemoved();
				for (int j = 0; j< removed.size();j++)
					foods.remove(removed.get(j));
			}

		}
		catch (Exception e)
        {
            System.out.println(e);
        }
		return cost;
	}
}
