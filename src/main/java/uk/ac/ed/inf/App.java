package uk.ac.ed.inf;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class App
{
    public static void main(String[] args) throws SQLException
	{
	    //get order
        DerbyIO test = new DerbyIO("localhost", "1527", "derbyDB");
        ArrayList<Order> menuTest = test.readDerbyOrderNo("2022-02-02");
		Menus t = new Menus("localhost", "9898");

		HashMap<LongLat, ArrayList<LongLat>> hashedVertices = new HashMap<>();
		//get deliverTo
		for (Order o : menuTest)
		{
			ArrayList<LongLat> shopLocList = new ArrayList<>();
			for (String food : o.getFood())
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
        GeoJsonRW noFly = new GeoJsonRW("localhost", "9898", "no-fly-zones.geojson");
        noFly.readGeoJson();
		NoFlyZone zone = new NoFlyZone(noFly.getNoFlyZonePoints());

		ArrayList<LongLat> testNoFly = new ArrayList<>();
		for (ArrayList<LongLat> a: noFly.getNoFlyZonePoints())
			testNoFly.addAll(a);


		//System.out.println(noFly.mkFeatureCollection(testNoFly).toJson());

		for(LongLat destination: hashedVertices.keySet())
		{
			LongLat start = new LongLat(-3.186874, 55.944494);
			OrderWrapper burrito = new OrderWrapper(hashedVertices.get(destination), start, destination);
			System.out.println(noFly.mkFeatureCollection
					(
					burrito.getPath(zone.closeTo(), zone.getEdgeNoFly())).toJson()
				);
			ArrayList<LongLat> testJson = burrito.getPath(zone.closeTo(), zone.getEdgeNoFly());
			System.out.println("--");
			System.out.println(testJson.get(1).latitude + " " + testJson.get(1).longitude);
			System.out.println(noFly.mkFeatureCollection
					(
							burrito.posToDestination(testJson)).toJson()
			);

			System.out.println("-------");

		}

    }
}
