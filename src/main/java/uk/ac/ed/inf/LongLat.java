package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * This is the class LongLat, representing drone's position
 * by using attribute longitude, latitude and angle.
 */
public class LongLat
{
    public double longitude;
    public double latitude;
    public double angle;
	public static final double UNITMOVE = 0.00015;

    public LongLat(double longitude, double latitude)
    {
        this.longitude = longitude;
        this.latitude  = latitude;
        this.angle     = -1000;
    }

	public boolean isConfined()
	{
		boolean x_confined = false;
		boolean y_confined = false;

		if (longitude > -3.192473 && longitude < -3.184319)
			x_confined = true;

		if (latitude > 55.942617 && latitude < 55.946233)
			y_confined = true;

		return x_confined && y_confined;
	}

	/**
	 * Calculate the distance between two coordinates
	 * @param coord the position of the other point.
	 * @return distance between two points
	 */
	public double distanceTo(LongLat coord)
	{

		double x_diff = coord.longitude - longitude;
		double y_diff = coord.latitude  - latitude;

		return Math.sqrt(x_diff*x_diff+y_diff*y_diff);
	}
	public double noFlyDistanceTo(LongLat coord, ArrayList<ArrayList<Line2D>> noFly)
	{
		double x_diff = coord.longitude - longitude;
		double y_diff = coord.latitude  - latitude;
		Line2D e = new Line2D.Double(longitude, latitude, coord.longitude, coord.latitude);

		for (ArrayList<Line2D> z : noFly)
		{
			for (Line2D l : z)
			{
				if (e.intersectsLine(l))
				{
					return 150000.0;
				}

			}

		}
		return Math.sqrt(x_diff*x_diff+y_diff*y_diff);
	}

	/**
	 * check whether two points are close to each other
	 * @param coord coordinate of the other point
	 * @return True if close, else false
	 */
	public boolean closeTo(LongLat coord)
	{
		return (distanceTo(coord) <= UNITMOVE);
	}



	/**
	 * Calculate the next position based on the given angle
	 * @param angle the new angle of the drone
	 * @return the position after given the new angle and made a move
	 */
	public LongLat nextPosition(int angle)
	{
		this.angle = Math.toRadians(angle);
		double x_move = Math.cos(this.angle) * UNITMOVE;
		double y_move = Math.sin(this.angle) * UNITMOVE;
		if (angle == -999)
			return new LongLat(longitude,latitude);
		return new LongLat(longitude+x_move,latitude+y_move);
	}
}

