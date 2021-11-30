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
        ArrayList<Order> menuTest = test.readDerbyOrderNo("2022-04-11");
		Menus t = new Menus("localhost", "9898");

		ArrayList<LongLat> deliverLoc = new ArrayList<>();
		HashMap<LongLat, ArrayList<LongLat>> hashedVertices = new HashMap<>();
		//get deliverTo
		for (Order o : menuTest)
		{
			ArrayList<LongLat> shopLocList = new ArrayList<>();
			for (String food : o.getFood())
			{
				for (String www : t.getFoodLoc(food)) {
					Location x = new Location(www, "localhost", "9898");
					LongLat shop = new LongLat(x.getDetails().coordinates.lat, x.getDetails().coordinates.lng);
					shopLocList.add(shop);
					System.out.println(www);
				}
			}
			hashedVertices.put(o.getDeliverTo(), shopLocList);
		}

		System.out.println(hashedVertices);

		ArrayList<LongLat> shopLocList  = new ArrayList<>();
		// get shop loc

		System.out.println(shopLocList.size());
		System.out.println(deliverLoc.size());

		if (shopLocList.size() == deliverLoc.size())
			System.out.println("size ok");
		//get noflyzone
        GeoJsonRW noFly = new GeoJsonRW("localhost", "9898", "no-fly-zones.geojson");
        noFly.readGeoJson();
		NoFlyZone zone = new NoFlyZone(noFly.getNoFlyZonePoints());

		System.out.println(zone.getEdgeNoFly().size());
		DroneGraph testGraph = new DroneGraph(hashedVertices);
		testGraph.getPath(zone.getEdgeNoFly());
    }
}
