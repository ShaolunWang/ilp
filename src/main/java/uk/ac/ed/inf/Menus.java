package uk.ac.ed.inf;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	 * Then use Parsing module to parse the content
	 * @param food arbitrary amount of food names
	 * @return the cost of all the food
	 */
	public int getDeliveryCost(String ...food)
	{

		Request getMenus = new Request(hostname, port, "/menus/menus.json");
		JsonArray shopArray = getMenus.requestAccess();
		//fetch their price
		try
		{
			for (String name : food)
			{
				for (int i = 0; i < shopArray.size();i++)
				{
					JsonObject curr_shop = shopArray.get(i).getAsJsonObject();

					Parser menu = new Parser(curr_shop.get("menu").getAsJsonArray());
					//cost for a single food in the order

					int foodCost = menu.menuParser(name, "pence");
					if (foodCost != -1)
						cost += foodCost; 

				}
			}

		}
		catch (Exception e)
        {
            System.out.println(e);
        }
				return cost;
	}
}
