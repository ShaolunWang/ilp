package uk.ac.ed.inf;


public class Details
{
	String country;

	SquareItems square;
	public static class SquareItems
	{
		SouthwestItems southwest;
		public class SouthwestItems
		{
			double lng;
			double lat;
		}
		NortheastItems northeast;
		public class NortheastItems
		{
			double lng;
			double lat;
		}
	}
	String nearestPlace;
	CoordItems coordinates;
	public static class CoordItems
	{
		double lng;
		double lat;
	}

	String words;
	String language;
	String map;
}
