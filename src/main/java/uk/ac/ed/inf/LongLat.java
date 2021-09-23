package uk.ac.ed.inf;

public class LongLat()
{
    public double longitude;
    public double latitude;
    private int angle;
	private final double move = 0.00015
    public LongLat(String longitude, String latitude)
    {
        this.longitude = longitude;
        this.latitude  = latitude;
        angle          = -1000;
    }

	public bool isConfined()
	{
		bool confined = false;

		return  confined;
	}
	public double distanceTo(LongLat pos)
	{
		x_diff = pos.longitude - longitude;
		y_diff = pos.latitude  - latitude;
		return Math.sqrt(x_diff*x_diff, y_diff*y_diff);
	}
	public bool closeTo(LongLat coord)
	{
		
		bool isClose = false;

		return isConfined;
		
	}
	public LongLat nextPosition(int angle)
	{
		this.angle = angle;
		x_move = Math.cos(angle)*move;
		y_move = Math.sin(angle)*move; 

		LongLat new_pos = new LongLat(longitude.x_move, latitude.y_move);
		return new_pos;

	}



}

