package uk.ac.ed.inf;

import java.sql.SQLException;
import java.util.ArrayList;


public class App
{
	public static void main(String[] args) throws SQLException
	{

		String geoJsonDate = args[0] +"-"+ args[1]+"-" + args[2];
		String date = args[2] + "-"+ args[1] +"-" +args[0];

		String webPort = args[3];
		String dbPort  = args[4];

		LongLat start = new LongLat(-3.186874, 55.944494);
	    //get order
		int money;
		ArrayList<LongLat> flightPath = new ArrayList<>();
		ArrayList<LongLat> beenTo = new ArrayList<>();
		AllOrders sql = new AllOrders(date,webPort,dbPort);

		int optimalMoney = -1;
		ArrayList<LongLat> optimalPath = new ArrayList<>();

		// getting the optimal solution
		for (int i = 0; i < 15;i++)
		{
			AllOrders delivery = new AllOrders(date,webPort,dbPort);
			money = delivery.totalOrder(flightPath,start, beenTo);

			//flightpath size as a sanity check
			if (money >= optimalMoney && flightPath.size() <= 1500)
			{
				optimalMoney = money;
				if (optimalPath.size()!=0)
					optimalPath.clear();
				optimalPath.addAll(flightPath);

			}

		}

		// output to console
		GeoJson.toGeoJsonFile(GeoJson.mkFeatureCollection(optimalPath),geoJsonDate);
		sql.writeSQL(optimalPath, beenTo);
		System.out.println("ok.");
		System.out.println("money :" + optimalMoney);
		System.out.println("All money: "+ AllOrders.orderValues());

		System.out.println("percentage: "
				+ (double) optimalMoney /(double) AllOrders.orderValues() + "%.");
		System.out.println("moves taken:" +  optimalPath.size());
	}
}
