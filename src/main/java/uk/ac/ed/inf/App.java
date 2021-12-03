package uk.ac.ed.inf;


import com.google.gson.JsonArray;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class App
{
    public static void main(String[] args) throws SQLException
	{
	    //get order
        DerbyIO test = new DerbyIO("localhost", "1527", "derbyDB");
        ArrayList<Order> menuTest = test.readDerbyOrderNo("2023-12-27");
		Menus t = new Menus("localhost", "9898");

		HashMap<LongLat, ArrayList<LongLat>> hashedVertices = new HashMap<>();
		//get deliverTo
		for (Order o : menuTest)
		{
			ArrayList<LongLat> shopLocList = new ArrayList<>();
			for (String food : o.toVararg())
			{
				for (String www : t.getFoodLoc(food))
				{
					Location x = new Location(www, "localhost", "9898");
					LongLat shop = new LongLat(x.getDetails().coordinates.lng, x.getDetails().coordinates.lat);
					shopLocList.add(shop);
					System.out.println(www);
				}
			}
			hashedVertices.put(o.getDeliverTo(), shopLocList);
		}



		// get shop loc


		//get noflyzone
		int sdf = 0;
        GeoJson noFly = new GeoJson("localhost", "9898", "no-fly-zones.geojson");
        noFly.readGeoJson();
		NoFlyZone zone = new NoFlyZone(noFly.getNoFlyZonePoints());

		ArrayList<LongLat> testNoFly = new ArrayList<>();
		for (ArrayList<LongLat> a: noFly.getNoFlyZonePoints())
			testNoFly.addAll(a);

		ArrayList<LongLat> flightPath = new ArrayList<>();

		//System.out.println(noFly.mkFeatureCollection(testNoFly).toJson());
		LongLat start = new LongLat(-3.186874, 55.944494);
		LongLat AT = new LongLat(-3.186874, 55.944494);

		ArrayList<LongLat> beenTo = new ArrayList<>();
		while (beenTo.size() != hashedVertices.size())
		{
			LongLat closestDestination = null;
			double dis = 100000;

			for (LongLat destination : hashedVertices.keySet())
			{

				if (destination.manhattan(start) < dis && !beenTo.contains(destination))
					closestDestination = destination;

			}
			SingleOrder burrito = new SingleOrder(hashedVertices.get(closestDestination), start, closestDestination);

			ArrayList<LongLat> testJson = burrito.getPath(zone.closeTo(), zone.getEdgeNoFly());
			ArrayList<LongLat> oneOrder = burrito.posToDestination(testJson, start);
			start = oneOrder.get(oneOrder.size() - 1);
			beenTo.add(closestDestination);


			if (beenTo.size() == hashedVertices.size() || flightPath.size() + oneOrder.size()> 1500)
			{
				int money = 0;
				for (Order o : menuTest)
				{
					if (o.getDeliverTo().longitude != closestDestination.longitude
					&& o.getDeliverTo().latitude != closestDestination.latitude)
						money += t.getDeliveryCost(o.toVararg());
				}
				sdf = money;
				System.out.println(money);
				oneOrder = burrito.toAT(zone.getEdgeNoFly(), start, AT, zone.closeTo());
			}
			flightPath.addAll(oneOrder);
		}
		int tot = 0;
		for (int k = 0; k < menuTest.size();k++)
		{
			tot += t.getDeliveryCost(menuTest.get(k).toVararg());
		}
		System.out.println(tot);
		System.out.println(noFly.mkFeatureCollection(flightPath).toJson());
		System.out.println(flightPath.size());
		System.out.println((double) sdf/ (double) tot);


	}
}
