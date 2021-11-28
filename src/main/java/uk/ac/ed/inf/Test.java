package uk.ac.ed.inf;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class Test
{
    public static void main(String[] args) throws ParseException, SQLException 
	{
        DerbyIO test = new DerbyIO("localhost", "9876", "derbyDB");
        ArrayList<Order> menuTest = test.readDerbyOrderNo("2022-04-11");

        Menus t = new Menus("localhost", "9898");
        for (int i = 0; i < menuTest.size();i++)
        {
            System.out.println(t.getDeliveryCost(menuTest.get(i).getVararg()));
        }

		System.out.println("-------------");

		ArrayList<String> locList = t.getShopLoc();
		for (String loc : locList)	
		{
			Location x = New Location(loc, "localhost", "9898");
			System.out.println(x.detail.coordinates);
		}
    }
}
