package uk.ac.ed.inf;

import java.util.HashMap;
import java.util.List;

// This is a class to set up gson parsing methods
public class Shop
{
	String name;
	String location;
	public List<MenuItems> menu;
	public static class MenuItems
	{
		String item;
		int pence;
	}
}
}
