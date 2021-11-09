package uk.ac.ed.inf;

import com.mapbox.geojson;

import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Feature

public class GeoJsonRW
{
	private final String hostname;
	private final String port
	private final String filename;
	public ArrayList<Features> f;
		
	public GeoJsonRW(String hostname, String port, String filename)
	{
		this.hostname = hostname;
		this.port     = port;
		this.filename = filename;
		this.f 		  = readGeoJson(filename);
		
	}
	/*
	 * f source is a GeoJSON string then
	 * FeatureCollection.fromJson(source) returns a FeatureCollection.
	 *     • If fc is a FeatureCollection then fc.features() is a list ofFeature objects.
	 *	   • If f is a Feature then f.geometry() is a Geometry object.
     *	   • If g is a Geometry object, it may also be a Polygon.
	 *     • If g is an instanceof Polygon then (Polygon)g is a Polygon.
	 */

	public readGeoJson()
	{
		String loc = "/buildings/"+filename;

		Request getGeoJson = new Request(hostname, port, loc);
		typeOfGeoJson = getClass(getGeoJson.)
		try
		{
			FeatureCollection fc  = FeatureCollection.fromJson(getGeoJson.requestAcess());

			return fc.features();
		}
		catch(Exception e)
		{
			System.err.println("Not Feature Objects!");
		}
		
	}
	public getGeometry(Feature f, int i)
	{
		if (f isInstanceOf Geometry)
			return f.geometry();
		else
		{
			System.err.println("Not Geometry Object");
			return Null;
		}
	}
	public getShape(Geometry g, int i)
	{
		if (g isInstanceOf Polygon)
			return (Polygon)g;
		else if (g isInstanceOf LineString)
			return (LineString)g;
		else if (g isInstanceOf Point)
			return (Point)g;
		else
		{
			System.err.println("Not Geometry Object");
			return Null;
		}
	}
}
