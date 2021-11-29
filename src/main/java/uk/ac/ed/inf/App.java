package uk.ac.ed.inf;

import java.sql.SQLException;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args) throws SQLException
	{
	    //get order
        DerbyIO test = new DerbyIO("localhost", "1527", "derbyDB");
        ArrayList<Order> menuTest = test.readDerbyOrderNo("2022-04-11");

		ArrayList<LongLat> deliverLoc = new ArrayList<>();

		//get deliverTo
		for (Order o : menuTest)
		{
			deliverLoc.add(o.getDeliverTo());
		}
        Menus t = new Menus("localhost", "9898");

		//get Cost
        for (Order order : menuTest)
        {
            System.out.println(t.getDeliveryCost(order.getFood()));
        }

		System.out.println("-------------");

		ArrayList<String> detailList = t.getShopLoc();
		ArrayList<LongLat> shopLocList  = new ArrayList<>();
		// get shop loc
		for (String loc : detailList)	
		{
			Location x = new Location(loc, "localhost", "9898");
            LongLat shop = new LongLat(x.getDetails().coordinates.lat, x.getDetails().coordinates.lng);
            shopLocList.add(shop);
        }
		//get noflyzone
        GeoJsonRW noFly = new GeoJsonRW("localhost", "9898", "no-fly-zones.geojson");

		System.out.println(noFly.getNoFlyZonePoints());
    }
}
