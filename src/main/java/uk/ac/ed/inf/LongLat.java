package uk.ac.ed.inf;

import org.jetbrains.annotations.NotNull;

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
    public int angle;
	public static final double UNITMOVE = 0.00015;
	public static final LongLat AT = new LongLat(-3.186874, 55.944494);

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
	public double distanceTo(@NotNull LongLat coord)
	{

		double x_diff = coord.longitude - longitude;
		double y_diff = coord.latitude  - latitude;

		return Math.sqrt(x_diff*x_diff+y_diff*y_diff);
	}
	public double noFlyDistanceTo(@NotNull LongLat coord, @NotNull ArrayList<ArrayList<Line2D>> noFly)
	{
		Line2D e = new Line2D.Double(longitude, latitude, coord.longitude, coord.latitude);

		for (ArrayList<Line2D> z : noFly)
		{
			for (Line2D l : z)
			{
				if (e.intersectsLine(l))
				{
					return 15000.0;
				}
			}

		}
		//return (Math.abs(longitude - coord.longitude)+ Math.abs(latitude-coord.latitude));
		return distanceTo(coord);
	}

	/**
	 * check whether two points are close to each other
	 * @param coord coordinate of the other point
	 * @return True if close, else false
	 */
	public boolean closeTo(LongLat coord)
	{
		return (distanceTo(coord) < UNITMOVE);
	}



	/**
	 * Calculate the next position based on the given angle
	 * @param angle the new angle of the drone
	 * @return the position after given the new angle and made a move
	 */
	public LongLat nextPosition(int angle)
	{
		this.angle = angle;
		double theta = Math.toRadians(angle);
		double x_move = Math.cos(theta) * UNITMOVE;
		double y_move = Math.sin(theta) * UNITMOVE;
		if (angle == -999)
			return new LongLat(longitude,latitude);

		LongLat newLongLat = new LongLat(longitude + x_move,
				latitude+ y_move);
		newLongLat.angle = this.angle;
		return newLongLat;
	}
	public double manhattan(@NotNull LongLat coord)
	{
		return (Math.abs(longitude - coord.longitude)+ Math.abs(latitude-coord.latitude));
	}
}

