package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoFlyZone
{
	private ArrayList<ArrayList<LongLat>> corners;
	private ArrayList<Line2D> edges;
	public NoFlyZone(ArrayList<ArrayList<LongLat>> corners)
	{
		this.corners = corners;
		this.edges = toLine2D();
	}

	public ArrayList<Line2D> toLine2D()
	{
		ArrayList<Line2D> edges = new ArrayList<>();
		for (ArrayList<LongLat> zones : corners)
		{
			for (int i = 0; i < zones.size();i++)
			{
				if (i == zones.size() -1)
					break;
				else
				{
					Line2D e = new Line2D.Double(zones.get(i).longitude, zones.get(i).latitude,
								  zones.get(i+1).longitude, zones.get(i).latitude);
					edges.add(e);
				}
			}
		}
		return edges;
	}

	public ArrayList<Line2D> getEdgeNoFly()
	{
		return this.edges;
	}


}
