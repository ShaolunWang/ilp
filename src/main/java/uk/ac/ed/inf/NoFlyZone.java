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

	/**
	 * Convert all the corners of no fly zone into an
	 * arrayList of line 2D boundaries.
	 * @return an arraylist of line2D objects
	 */
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

	/**
	 * getter function for all the no fly zone boundaries
	 * @return an arrayList of Line2D objects
	 */
	public ArrayList<ArrayList<Line2D>> getEdgeNoFly()
	{
		return this.edges;
	}

	/**
	 * generates an arraylist of coordinates that's
	 * close to the corners of the no fly zone.
	 * These would be used in A* shortest path search
	 * as vertices.
	 * @return an arraylist of LongLat corrdinates
	 */
	public ArrayList<LongLat> closeTo()
	{
		ArrayList<LongLat> close = new ArrayList<>();
		for (ArrayList<LongLat> zone : corners)
		{
			for (LongLat c : zone)
			{
				LongLat close1 = new LongLat(
						c.longitude - 2.75*LongLat.UNITMOVE*Math.cos(Math.PI/6),
						 c.latitude  - 2.75*LongLat.UNITMOVE*Math.sin(Math.PI/6));
				LongLat close2 = new LongLat(
						c.longitude - 2.75*LongLat.UNITMOVE*Math.cos(Math.PI/6),
						 c.latitude  + 2.75*LongLat.UNITMOVE*Math.sin(Math.PI/6));
				LongLat close3 = new LongLat(
						c.longitude + 2.75*LongLat.UNITMOVE*Math.cos(Math.PI/6),
						 c.latitude  - 2.75*LongLat.UNITMOVE*Math.sin(Math.PI/6));
				LongLat close4 = new LongLat(
						c.longitude + 2.75*LongLat.UNITMOVE*Math.cos(Math.PI/6),
						 c.latitude  + 2.75*LongLat.UNITMOVE*Math.sin(Math.PI/6));

//				LongLat close5 = new LongLat(
//						c.longitude + 2*LongLat.UNITMOVE,
//						c.latitude);
//
//				LongLat close7 = new LongLat(
//						c.longitude - 2*LongLat.UNITMOVE,
//						c.latitude);
//
//				LongLat close6 = new LongLat(
//						c.longitude,
//						c.latitude  - 2*LongLat.UNITMOVE);
//
//				LongLat close8 = new LongLat(
//						c.longitude,
//						c.latitude  + 2*LongLat.UNITMOVE);
				close.add(close1);
				close.add(close2);
				close.add(close3);
				close.add(close4);

//				close.add(close5);
//				close.add(close6);
//				close.add(close7);
//				close.add(close8);
			}
		}
		return close;
	}
}
