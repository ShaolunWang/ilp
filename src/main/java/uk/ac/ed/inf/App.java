package uk.ac.ed.inf;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class Test
{
    public static void main(String[] args) throws ParseException, SQLException 
	{
        DerbyIO test = new DerbyIO("localhost", "1527", "derbyDB");
        ArrayList<Order> menuTest = test.readDerbyOrderNo("2022-04-11");

		System.out.println(menuTest.get(0).deliverTo);
        Menus t = new Menus("localhost", "9898");
        for (int i = 0; i < menuTest.size();i++)
        {
            System.out.println(t.getDeliveryCost(menuTest.get(i).getFood()));
        }

		System.out.println("-------------");

		ArrayList<String> detailList = t.getShopLoc();
		ArrayList<LongLat> shopLocList  = new ArrayList<>();
		for (String loc : detailList)	
		{
			Location x = new Location(loc, "localhost", "9898");
            LongLat shop = new LongLat(x.getDetails().coordinates.lat, x.getDetails().coordinates.lng);
            shopLocList.add(shop);
        }
    }
}
