package uk.ac.ed.inf;
import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

public class Order
{
	private final String order;
	private final String customer;
	private final String deliverTo;
	private final ArrayList<String> foodDetail;
	public Order(String order,String customer, String deliverTo, ArrayList<String> foodDetail)
	{
		this.order = order;
		this.customer = customer;
		this.deliverTo = deliverTo;
		this.foodDetail = foodDetail;
	}

	public String[] getFood()
	{
		return foodDetail.toArray(new String[foodDetail.size()]);
	}
}
