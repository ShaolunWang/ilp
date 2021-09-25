package uk.ac.ed.inf;
public class Main 
{	
	public static void main(String[] args)
	{
		Request try_menus = new Request("localhost", "80","/menus/menus.json");		
		System.out.println(try_menus.requestAccess());
		JsonParsing test = new JsonParsing(try_menus.requestAccess());
		System.out.println(test.parseJsonArrayMenu("Flaming tiger latte", "pence"));

	}

}
