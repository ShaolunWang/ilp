package uk.ac.ed.inf;

import com.mapbox.geojson.*;
import com.mapbox.turf.TurfMeta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class GeoJson
{
	private final String hostname;
	private final String port;
	private final FeatureCollection fc;

	public GeoJson(String hostname, String port, String filename)
	{
		this.hostname = hostname;
		this.port     = port;
		this.fc       = readGeoJson(filename);
	}
	/**
	 * f source is a GeoJSON string then
	 * FeatureCollection.fromJson(source) returns a FeatureCollection.
	 *     • If fc is a FeatureCollection then fc.features() is a list ofFeature objects.
	 *	   • If f is a Feature then f.geometry() is a Geometry object.
     *	   • If g is a Geometry object, it may also be a Polygon.
	 *     • If g is an instanceof Polygon then (Polygon)g is a Polygon.
	 **/
	public FeatureCollection readGeoJson(String filename)
	{
		FeatureCollection collection = null;
		String loc = "/buildings/"+ filename;

		Request getGeoJson = new Request(hostname, port, loc);
		try
		{
			 collection= FeatureCollection.fromJson(getGeoJson.requestAccessHttp());

		}
		catch(Exception e)
		{
			System.err.println("Not Feature Objects!");
		}
		return collection;
	}

	/**
	 * Convert an arrayList of coordinates in LongLat to FeatureCollections
	 * @param x A list of coordinates in LongLat form
	 * @return an ArrayList of FeatureCollection.
	 */
	public static FeatureCollection mkFeatureCollection(ArrayList<LongLat> x)
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

	/**
	 * Convert featureCollection into a local file
	 * @param fc featureCollection to be converted
	 * @param date date of the current running day
	 */
	public static void  toGeoJsonFile(FeatureCollection fc, String date)
	{
		try
		{
			String path = "drone-" + date + ".geojson";

			File file = new File(path);

			// If file doesn't exists, then create it
			if (!file.exists())
			{
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			// Write in file
			bw.write(fc.toJson());

			// Close connection
			bw.close();
		}
		catch (Exception e)
		{
			System.err.println("error: " + e.toString());
		}
	}

}






