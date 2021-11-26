package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
		cost = 50;
		Request getMenus = new Request(hostname, port,"/menus/menus.json");
		ArrayList<HashMap<String, Integer>> hashShops = toHashShops(toListShops(getMenus, order));
		cost += calculateCost(hashShops, order);
		
		return cost;
	}

	public ArrayList<Shop> toListShops(@NotNull Request getMenus, String ...order)
	{
		// might need to retrieve all the values using this method
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Shop>>(){}.getType();
		ArrayList<Shop> shops = gson.fromJson(getMenus.requestAccessHttp(), listType);

		return shops;
	}
	private @NotNull ArrayList<HashMap<String, Integer>> toHashShops(ArrayList<Shop> shops)
	{
		ArrayList<HashMap<String, Integer>> hashShops = hashPence(shops);
		return hashShops;
	}
	private @NotNull ArrayList<HashMap<String, Integer>> hashPence(@NotNull ArrayList<Shop> shops)
	{
		ArrayList<HashMap<String, Integer>> temp = new ArrayList<>();
		for (Shop items: shops)
		{
			for (int j = 0; j < items.menu.size(); j++)
			{
				HashMap<String, Integer> itemPence = new HashMap<>();
				itemPence.put(items.menu.get(j).item, items.menu.get(j).pence);
				temp.add(itemPence);
			}
		}
		return temp;
	}

	private Integer calculateCost(ArrayList<HashMap<String, Integer>> hashShops, String ...foods)
	{
		int temp = 0;
		try
		{
			//iterate through all the shops
			for (String food : foods)
			{
				for (HashMap<String, Integer> items: hashShops)
				{
				//iterate through all the foods being ordered

					if (items.containsKey(food))
					{
						System.out.println(items);
						temp += items.get(food);
					}
				}

			}
		}
		catch (NullPointerException e)
        {
            System.out.println("Exception thrown: " + e);
        }
		return temp;
	}

}
