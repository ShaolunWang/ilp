package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

		Gson gson = new Gson();
		Request getMenus = new Request(hostname, port,"/menus/menus.json");
		Type listType = new TypeToken<List<Shop>>(){}.getType();
		ArrayList<Shop> shops = gson.fromJson(getMenus.requestAccess(), listType);

		ArrayList<Integer> removed = new ArrayList<>();
		ArrayList<String> foods = new ArrayList<>(Arrays.asList(order));
		//TODO:hashmapping the item-pence

		try
		{
			//iterate through all the shops
			for (Shop items: shops)
			{
				//iterate through all the foods being ordered
				for (int i = 0;i < foods.size();i++)
				{
					//iterate through the menus
					for (int j = 0; j < items.menu.size();j++)
					{
						if ((items.menu.get(item)).equals(foods.get(i)))
						{
							//adding the iterated items through the removing list
							removed.add(i);
							cost += items.menu.get(item);
						}
					}
				}
			}
			//reducing the search counts by removing the items that's being searched
			if (foods.size() != 0)
			{
				for (Integer index : removed)
					foods.remove(index);
			}
			if (removed.size() != 0)
				removed.clear();
		}
		catch (NullPointerException e)
        {
            System.out.println("Exception thrown: " + e);
        }
		return cost;
	}

}
