package uk.ac.ed.inf;
import java.util.ArrayList;

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
}
