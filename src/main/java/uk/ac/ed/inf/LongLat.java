package uk.ac.ed.inf;

import java.util.Objects;

public class LongLat
{
    public double longitude;
    public double latitude;
    private double angle;
	private static final double move = 0.00015;
    public LongLat(double longitude, double latitude)
    {
        this.longitude = longitude;
        this.latitude  = latitude;
        this.angle     = -1000;
    }

	/**
	 * @return true if in the square confined by four shops, else false
	 */
	public boolean isConfined()
	{
		boolean x_confined = false;
		boolean y_confined = false;

		if (longitude >= -3.191594 && longitude <= -3.184319)
			x_confined = true;
		if (latitude >= 55.942617 && latitude <= 55.943658)
			y_confined = true;

		return x_confined && y_confined;
	}

	/**
	 *
	 * @param coord the position of the other point.
	 * @return distance between two points

	 */
	public double distanceTo(LongLat coord)
	{

		double x_diff = coord.longitude - longitude;
		double y_diff = coord.latitude  - latitude;
		return Math.sqrt(x_diff*x_diff+y_diff*y_diff);
	}

	/**
	 *
	 * @param coord coordinate of the other point
	 * @return True if close, else false
	 */
	public boolean closeTo(LongLat coord)
	{
		return distanceTo(coord) <= move;

	}

	/**
	 *
	 * @param angle the new angle of the drone
	 * @return the position after given the new angle and made a move
	 */
	public LongLat nextPosition(int angle)
	{
		this.angle = Math.toRadians(angle);
		double x_move = Math.cos(this.angle) * move;
		double y_move = Math.sin(this.angle) * move;
		if (angle == -999)
			return new LongLat(longitude,latitude);
		return new LongLat(longitude+x_move,latitude+y_move);
	}
}

