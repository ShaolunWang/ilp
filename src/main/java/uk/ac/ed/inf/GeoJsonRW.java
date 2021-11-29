package uk.ac.ed.inf;

import com.mapbox.geojson.*;
import com.mapbox.turf.TurfMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonRW
{
	private final String hostname;
	private final String port;
	private final String filename;
	public FeatureCollection fc;

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

	private FeatureCollection readGeoJson()
	{ 
		String loc = "/buildings/"+ this.filename;

		Request getGeoJson = new Request(hostname, port, loc);
		try
		{
			System.out.println(getGeoJson.requestAccessHttp());
			FeatureCollection fc  = FeatureCollection.fromJson(getGeoJson.requestAccessHttp());

			return fc;
		}
		catch(Exception e)
		{
			System.err.println("Not Feature Objects!");
		}
		return null;
	}
	public Geometry getGeometry(Feature f)
	{
		if (f instanceof Feature)
		{
			return f.geometry();
		}
		return null;
	}

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

	public FeatureCollection mkFC(LongLat @NotNull ... x)
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

	public String mkJson(@NotNull FeatureCollection fc)
	{
		return fc.toJson();
	}
	public ArrayList<ArrayList<LongLat>> getNoFlyZonePoints()
	{
		ArrayList<ArrayList<LongLat>> noFlyZonePoints = new ArrayList<ArrayList<LongLat>>();
		for (Feature f: this.fc.features())
		{
			ArrayList<LongLat> singleNoFlyZone = new ArrayList<LongLat>();
			for (Point p: TurfMeta.coordAll(getLineString(getGeometry(f))))
			{
				singleNoFlyZone.add(toLongLat(p));
			}
			
			noFlyZonePoints.add(singleNoFlyZone);
		}
		return noFlyZonePoints;
	}	
	public LongLat toLongLat(Point p)
	{
		LongLat pos = new LongLat(p.longitude(), p.latitude());
		return pos;
	}

}






