package uk.ac.ed.inf;

import com.mapbox.geojson.*;

import java.util.List;

public class GeoJsonRW
{
	private final String hostname;
	private final String port;
	private final String filename;
	public List<Feature> fc;

	public GeoJsonRW(String hostname, String port, String filename)
	{
		this.hostname = hostname;
		this.port     = port;
		this.filename = filename;
		this.fc		  = readGeoJson();

	}
	/*
	 * f source is a GeoJSON string then
	 * FeatureCollection.fromJson(source) returns a FeatureCollection.
	 *     • If fc is a FeatureCollection then fc.features() is a list ofFeature objects.
	 *	   • If f is a Feature then f.geometry() is a Geometry object.
     *	   • If g is a Geometry object, it may also be a Polygon.
	 *     • If g is an instanceof Polygon then (Polygon)g is a Polygon.
	 */

	public List<Feature> readGeoJson()
	{
		String loc = "/buildings/"+ this.filename;

		Request getGeoJson = new Request(hostname, port, loc);
		try
		{
			System.out.println(getGeoJson.requestAccess());
			FeatureCollection fc  = FeatureCollection.fromJson(getGeoJson.requestAccess());

			return fc.features();
		}
		catch(Exception e)
		{
			System.err.println("Not Feature Objects!");
		}
		return null;
	}
	public Geometry getGeometry(int i)
	{
		if (fc.get(i) instanceof Feature)
		{
			return fc.get(i).geometry();
		}
		return null;
	}

	/*
	public Polygon getPolygon(Geometry g)
	{
		if (g instanceof Polygon)
			return (Polygon)g;


			return null;
	}
	public LineString getLineString(Geometry g)
	{
		if (g instanceof LineString)
			return (LineString)g;
		return null;
	}
	public Point getPoint(Geometry g)
	{
		if (g instanceof Point)
			return (Point)g;
		return null;
	}
	*/
}
