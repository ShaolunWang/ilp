package uk.ac.ed.inf;
import java.util.ArrayList;
import java.util.List;
public class Menus
{
	String hostname;
	int port;
	int cost;

	public Menus(String hostname, String port)
	{
		this.hostname = hostname;
		this.port     = Integer.parseInt(port);
		this.cost     = 50; // only do += on this
	}

	public int getDeliveryCost(List<String> food)
	{
		return cost;		
	}
	
}
