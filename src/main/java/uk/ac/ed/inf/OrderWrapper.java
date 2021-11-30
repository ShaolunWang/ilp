package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.util.ArrayList;


public class OrderWrapper
{
    private LongLat[] currentOrder;
    private LongLat destination;
    private LongLat start;

    public OrderWrapper(LongLat[] currentOrder, LongLat destination, LongLat start)
    {
        this.currentOrder = currentOrder;
        this.start        = start;
        this.destination  = destination;
    }

    public double getEdgeWeight(ArrayList<LongLat> landmarks,ArrayList<Line2D> noFlyZone)
    {
        double moves = 0;
        if (currentOrder.length == 1)
        {
             Line2D temp1 = new Line2D.Double(start.longitude, start.latitude,
                        currentOrder[0].longitude,currentOrder[0].latitude);
        }
        return 1.0;
    }
    // BFS search
    private double getCrossedCost(Line2D path, ArrayList<ArrayList<Line2D>> noFlyZone,ArrayList<ArrayList<LongLat>> corners)
    {
        //calculating the cost if it's path would cross the no fly zone
        for (ArrayList<Line2D> zone : noFlyZone)
        {
            for (Line2D l : zone)
            {
                if (path.intersectsLine(l))
                {
                    //work on this noflyzone's corners
                    noFlyZoneCost(corners.get(noFlyZone.indexOf(zone)), zone);

                }
            }

        }
        return 1.0;
    }
    private ArrayList<Line2D> noFlyZoneCost(ArrayList<LongLat> corners, ArrayList<Line2D> zone)
    {
        //1 form lines from start to each corner
        //2 discard all of them that crosses no fly zone
        //3 bfs
        //4 corner updates.
        ArrayList<Line2D> edge = new ArrayList<>();
        for (LongLat corner : corners)
        {
            Line2D s = new Line2D.Double(start.longitude,start.latitude, corner.longitude, corner.latitude);
            Line2D d = new Line2D.Double(corner.longitude, corner.latitude,
                    destination.longitude,destination.latitude);

        }
        return edge;
    }


}

