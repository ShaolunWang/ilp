package uk.ac.ed.inf;

import com.mapbox.geojson.*;
import com.mapbox.turf.TurfMeta;
import java.util.ArrayList;


public class GeoJson
{
	private final String hostname;
	private final String port;
	private final String filename;
	public FeatureCollection fc;

	public GeoJson(String hostname, String port, String filename)
	{
		this.hostname = hostname;
		this.port     = port;
		this.filename = filename;

	}
	/**
	 * f source is a GeoJSON string then
	 * FeatureCollection.fromJson(source) returns a FeatureCollection.
	 *     • If fc is a FeatureCollection then fc.features() is a list ofFeature objects.
	 *	   • If f is a Feature then f.geometry() is a Geometry object.
     *	   • If g is a Geometry object, it may also be a Polygon.
	 *     • If g is an instanceof Polygon then (Polygon)g is a Polygon.
	 **/
	public void readGeoJson()
	{ 
		String loc = "/buildings/"+ this.filename;

		Request getGeoJson = new Request(hostname, port, loc);
		try
		{
			fc  = FeatureCollection.fromJson(getGeoJson.requestAccessHttp());

		}
		catch(Exception e)
		{
			System.err.println("Not Feature Objects!");
		}
	}

	/**
	 * Convert an arrayList of coordinates in LongLat to FeatureCollections
	 * @param x A list of coordinates in LongLat form
	 * @return an ArrayList of FeatureCollection.
	 */
	public FeatureCollection mkFeatureCollection(ArrayList<LongLat> x)
	{
		ArrayList<Point> points = new ArrayList<>();
		for (LongLat items : x)
		{
			Point p = Point.fromLngLat(items.longitude, items.latitude);
			points.add(p);
		}
		LineString ls = LineString.fromLngLats(points);
		Feature f     = Feature.fromGeometry(ls);
		return FeatureCollection.fromFeature(f);
	}

	/**
	 * get no fly zone's corners in the form of 2d arraylist
	 * @return an arraylist of arraylist of no fly zones' corner
	 **/
 	public ArrayList<ArrayList<LongLat>> getNoFlyZonePoints()
	{
		ArrayList<ArrayList<LongLat>> noFlyZonePoints = new ArrayList<>();
		for (Feature f: this.fc.features())
		{
			ArrayList<LongLat> singleNoFlyZone = new ArrayList<>();
			for (Point p: TurfMeta.coordAll(f, false))
			{
				singleNoFlyZone.add(toLongLat(p));
			}
			
			noFlyZonePoints.add(singleNoFlyZone);
		}
		return noFlyZonePoints;
	}

	/**
	 * convert a Point to LongLat object
	 * @param p a Point object
	 * @return a LongLat object
	 */
	public LongLat toLongLat(Point p)
	{
		LongLat pos = new LongLat(p.longitude(), p.latitude());
		return pos;
	}


}






