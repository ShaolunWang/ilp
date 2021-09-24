package uk.ac.ed.inf

public Menus
{
	String hostname;
	int port;
	int cost;

	public Menus(String hostname, String port)
	{
		this.hostname = hostname;
		this.port     = port.toString();
		this.cost     = 50; // only do += on this
	}

	public int getDeliveryCost(List<string> food)
	{
		return cost;		
	}
	
}
