package uk.ac.ed.inf;
public class main
{	
	public static void main(String[] args)
	{
		Request try_menus = new Request("localhost", 80, "/menu/menus.json");		
		System.out.println(try_menus.socketOut());
	}

}
