package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class NoFlyZone
{
	private ArrayList<ArrayList<LongLat>> corners;
	private ArrayList<Line2D> edges;
	public NoFlyZone(ArrayList<ArrayList<LongLat>> corners)
	{
		this.corners = corners;
	}

	public void toLine2D()
	{
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
					this.edges.add(e);
				}
			}
		}
	}
	public boolean isIntersect(Line2D path)
	{
		for (Line2D e : edges)	
		{
			if (path.intersectsLine(e))
				return true;
		}
		return false;
	}

}
