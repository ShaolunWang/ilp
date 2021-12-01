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
	private final ArrayList<HashMap<String, Integer>> hashShopsItems;
	private final ArrayList<String> orderLoc; // all shops getting orders

	public Menus(String hostname, String port)
	{
		Request getMenus = new Request(hostname, port, "/menus/menus.json");
		this.orderLoc = new ArrayList<>();
		this.hashShopsItems = toHashShops(toListShops(getMenus));


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
		int cost = 50;
		cost += calculateCost(hashShopsItems, order);
		return cost;
	}


	/**
	 * Creates an arrayList of HashMaps that
	 * stores all the (food, price) pair in all the stores
	 * @param shops
	 * @return
	 */
	private @NotNull ArrayList<HashMap<String, Integer>> hashPence(@NotNull ArrayList<Shop> shops)
	{
		ArrayList<HashMap<String, Integer>> temp = new ArrayList<>();
		for (Shop items: shops)
		{

			for (int j = 0; j < items.menu.size(); j++)
			{
				HashMap<String, Integer> itemPence = new HashMap<>();
				itemPence.put(items.menu.get(j).item, items.menu.get(j).pence);
				orderLoc.add(items.location);
				temp.add(itemPence);
			}
		}
		return temp;
	}

	/**
	 * Calculate costs from the Hashmap and the food order
	 * @param hashShops a HashMap of all the (food, pence) pair in all the shops
	 * @param foods a list of ordered food
	 * @return price of the current order
	 */
	private Integer calculateCost(ArrayList<HashMap<String, Integer>> hashShops, String ...foods)
	{
		int temp = 0;
		try
		{
			//iterate through all the shops
			for (String food : foods)
			{
				for (HashMap<String, Integer> hashShop : hashShops)
				{
					//iterate through all the foods being ordered

					if (hashShop.containsKey(food))
					{
						temp += hashShop.get(food);
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

	/**
	 * Convert menus into an arraylist
	 * @param getMenus a request object that connects to the server
	 * @return an arrayList of shops object
	 */
	private ArrayList<Shop> toListShops(@NotNull Request getMenus)
	{
		// might need to retrieve all the values using this method
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Shop>>(){}.getType();

		return gson.fromJson(getMenus.requestAccessHttp(), listType);
	}
	private @NotNull ArrayList<HashMap<String, Integer>> toHashShops(ArrayList<Shop> shops)
	{
		return hashPence(shops);
	}
	public ArrayList<String> getFoodLoc(String ...foods)
	{
		final ArrayList<String> foodLoc = new ArrayList<>();

		for (String food : foods)
		{
			for (int i = 0; i < hashShopsItems.size(); i++)
			{
				//iterate through all the foods being ordered

				if (hashShopsItems.get(i).containsKey(food))
				{
					if (!foodLoc.contains(orderLoc.get(i)))
						foodLoc.add(this.orderLoc.get(i));
				}
			}

		}
		return foodLoc;
	}

}
