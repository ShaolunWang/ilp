package uk.ac.ed.inf;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * a class that handles setting up orders
 * and order related utility functions
 */
public class Order
{
	private final String deliverTo;
	private final String orderNo;
	private final ArrayList<String> foodDetail;
	public Order(String orderNo, String deliverTo, ArrayList<String> foodDetail)
	{
		this.orderNo = orderNo;
		this.deliverTo = deliverTo;
		this.foodDetail = foodDetail;
	}

	/**
	 * Convert the arrayList of foods into an array,
	 * it will be the vararg of getFoodDelivery() function
	 * in Menus class
	 * @return an array of food being ordered
	 */
	public String[] toVararg()
	{
		return foodDetail.toArray(new String[0]);
	}

	/**
	 * getter function that gives where the order will be delivered to
	 * @return LongLat location of deliver point
	 */
	public LongLat getDeliverTo()
	{
		Location x = new Location(this.deliverTo, "localhost", "9898");
		return new LongLat(x.getDetails().coordinates.lng, x.getDetails().coordinates.lat);
	}

	/**
	 * Making a hashmap of delivery location and all shops
	 * @param orders all orders
	 * @param allMenus menus from all the shops
	 * @return a hashmap of (LongLat order, ArrayList<LongLat>> shops) objects
	 */
	public static HashMap<LongLat, ArrayList<LongLat>>  hashOrder(ArrayList<Order> orders, Menus allMenus)
	{
		HashMap<LongLat, ArrayList<LongLat>> hashedVertices = new HashMap<>();
		//get deliverTo
		for (Order o : orders)
		{
			ArrayList<LongLat> shopLocList = new ArrayList<>();
			for (String food : o.toVararg())
			{
				for (String www : allMenus.getFoodLoc(food))
				{
					Location x = new Location(www, "localhost", "9898");
					LongLat shop = new LongLat(x.getDetails().coordinates.lng, x.getDetails().coordinates.lat);
					shopLocList.add(shop);
				}
			}
			hashedVertices.put(o.getDeliverTo(), shopLocList);
		}
		return hashedVertices;

	}

	/**
	 * Get orderNo given the shop destination
	 * @param destination a shop location in LongLat
	 * @param orders all possible orders of current day
	 * @return a String of orderNo
	 */
	public static String getOrderNo(@NotNull LongLat destination, @NotNull ArrayList<Order> orders)
	{
		for (Order o : orders)
		{
			if (o.getDeliverTo().latitude == destination.latitude &&
				o.getDeliverTo().longitude == destination.longitude)
			{
				return o.orderNo;
			}

		}
		return "0";
	}
}
