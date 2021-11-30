package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class NoFlyZone
{
	private ArrayList<ArrayList<LongLat>> corners;
	private ArrayList<ArrayList<Line2D>> edges;
	public NoFlyZone(ArrayList<ArrayList<LongLat>> corners)
	{
		this.corners = corners;
		this.edges = toLine2D();
	}

	public ArrayList<ArrayList<Line2D>> toLine2D()
	{
		ArrayList<ArrayList<Line2D>> edges = new ArrayList<>();
		for (int j = 0; j < corners.size();j++)
		{
			ArrayList<Line2D> temp = new ArrayList<>();
			for (int i = 0; i < corners.get(j).size();i++)
			{
				if (i == corners.get(j).size() - 1)
					break;
				else
				{
					Line2D e = new Line2D.Double(
							corners.get(j).get(i).longitude,
							corners.get(j).get(i).latitude,
							corners.get(j).get(i+1).longitude,
							corners.get(j).get(i).latitude);
					temp.add(e);
				}
			}
			edges.add(temp);
		}
		return edges;
	}

	public ArrayList<ArrayList<Line2D>> getEdgeNoFly()
	{
		return this.edges;
	}


}
