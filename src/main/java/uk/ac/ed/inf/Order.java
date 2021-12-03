package uk.ac.ed.inf;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Order
{
	private final String customer;
	private final String deliverTo;
	private final ArrayList<String> foodDetail;
	public Order(String order,String customer, String deliverTo, ArrayList<String> foodDetail)
	{
		this.customer = customer;
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
		return foodDetail.toArray(new String[foodDetail.size()]);
	}
	public LongLat getDeliverTo()
	{
		Location x = new Location(this.deliverTo, "localhost", "9898");
		return new LongLat(x.getDetails().coordinates.lng, x.getDetails().coordinates.lat);
	}

	public static final Comparator<Order> Order_COMPARATOR = new OrderComparator();

	static class OrderComparator implements Comparator<Order>
	{
		@Override
		public int compare(Order a, Order b)
		{
			Menus t = new Menus("localhost", "9898");

			return t.getDeliveryCost(a.toVararg()) < t.getDeliveryCost(b.toVararg())  ?
					-1 : t.getDeliveryCost(a.toVararg())  == t.getDeliveryCost(b.toVararg()) ? 0 : 1;
		}
	}

	public static HashMap<LongLat, ArrayList<LongLat>>  hashOrder(ArrayList<Order> menuTest)
	{
		Menus t = new Menus("localhost", "9898");

		HashMap<LongLat, ArrayList<LongLat>> hashedVertices = new HashMap<>();
		//get deliverTo
		for (Order o : menuTest)
		{
			ArrayList<LongLat> shopLocList = new ArrayList<>();
			for (String food : o.toVararg())
			{
				for (String www : t.getFoodLoc(food))
				{
					Location x = new Location(www, "localhost", "9898");
					LongLat shop = new LongLat(x.getDetails().coordinates.lng, x.getDetails().coordinates.lat);
					shopLocList.add(shop);
					System.out.println(www);
				}
			}
			hashedVertices.put(o.getDeliverTo(), shopLocList);
		}
		return hashedVertices;

	}



}
