package uk.ac.ed.inf;

import java.sql.SQLException;
import java.util.ArrayList;


public class App
{
	public static void main(String[] args) throws SQLException
	{
//
//		String date = args[0] + args[1] + args[2];
//		String geoJsonDate = args[3] + args[2] +args[1];
//
//		String webPort = args[3];
//		String dbPort  = args[4];
		String date = "2022-01-01";
		String geoJsonDate = "01-01-2022";
		String webPort = "9898";
		String dbPort  = "1527";
		LongLat start = new LongLat(-3.186874, 55.944494);

	    //get order
		AllOrders delivery = new AllOrders(date,geoJsonDate,webPort,dbPort);
		int money = 0;
		ArrayList<LongLat> flightPath = new ArrayList<>();
		ArrayList<LongLat> beenTo = new ArrayList<>();
		delivery.totalOrder(money,flightPath,start, beenTo);
	}
}
