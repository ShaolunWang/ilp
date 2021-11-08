public class Details
{
	String country;
	public List<SquareItems> square;
	public class SquareItems
	{
		public List<SouthwestItems> southwest;
		public List<NorthwestItems> northwest;

		public class SouthwestItems
		{
			double lng;
			double lat;
		}
		public class NorthwestItems
		{
			double lng;
			double lat;
		}
		String nearesPlace;
	}
	public List<CoordItems> coordinates;
	public class CoordItems
	{
		double lng;
		double lat;
	}
	
	String words;
	String language;
	String map;
	
}
