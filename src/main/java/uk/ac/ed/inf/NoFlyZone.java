package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class NoFlyZone
{
	private final ArrayList<ArrayList<LongLat>> corners;
	private final ArrayList<ArrayList<Line2D>> edges;
	public NoFlyZone(ArrayList<ArrayList<LongLat>> corners)
	{
		this.corners = corners;
		this.edges = toLine2D();
	}

	public ArrayList<ArrayList<Line2D>>toLine2D()
	{
		ArrayList<ArrayList<Line2D>> edges = new ArrayList<>();
		for (ArrayList<LongLat> corner : corners)
		{
			ArrayList<Line2D> temp = new ArrayList<>();
			System.out.println(corner.size());

			for (int i = 0; i < corner.size(); i++)
			{
				if (i + 1 >= corner.size()) break;

				Line2D e = new Line2D.Double
						(
							corner.get(i).longitude,
							corner.get(i).latitude,
							corner.get((i + 1)).longitude,
							corner.get((i + 1)).latitude
						);
				temp.add(e);

			}
				System.out.println("-------");
			edges.add(temp);

		}
		return edges;
	}

	public ArrayList<ArrayList<Line2D>> getEdgeNoFly()
	{
		return this.edges;
	}
	public ArrayList<LongLat> closeTo()
	{
		ArrayList<LongLat> close = new ArrayList<>();
		for (ArrayList<LongLat> zone : corners)
		{
			for (LongLat c : zone)
			{
				LongLat close1 = new LongLat(c.longitude - LongLat.UNITMOVE, c.latitude - LongLat.UNITMOVE);
				LongLat close2 = new LongLat(c.longitude - LongLat.UNITMOVE, c.latitude + LongLat.UNITMOVE);
				LongLat close3 = new LongLat(c.longitude + LongLat.UNITMOVE, c.latitude - LongLat.UNITMOVE);
				LongLat close4 = new LongLat(c.longitude + LongLat.UNITMOVE, c.latitude + LongLat.UNITMOVE);
				close.add(close1);
				close.add(close2);
				close.add(close3);
				close.add(close4);
			}
		}
		return close;
	}
}
