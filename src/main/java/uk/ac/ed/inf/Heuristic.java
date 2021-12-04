package uk.ac.ed.inf;

import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;

import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * This module implements the a* heuristic by using manhattan distance
 * @param <V> Vertices type
 */
class Heuristic<V> implements AStarAdmissibleHeuristic<V>
{
    private final ArrayList<ArrayList<Line2D>> noFly;
    public Heuristic(ArrayList<ArrayList<Line2D>> noFly)
    {
        this.noFly = noFly;
    }

    /**
     * Implementation of getCostEstimate function
     * By using manhattan distance
     * if the path crosses the no fly zone, it will return a high value
     * otherwise just manhattan distance
     * @param v1 LongLat v1
     * @param v2 LongLat v2
     * @return distance
     */
    @Override
    public double getCostEstimate(Object v1, Object v2)
    {
        if (v1 instanceof LongLat && v2 instanceof LongLat)
        {
            var p1 = (LongLat) v1;
            var p2 = (LongLat) v2;
            Line2D e = new Line2D.Double(p1.longitude, p1.latitude, p2.longitude, p2.latitude);
            for (ArrayList<Line2D> z : noFly)
            {
                for (Line2D l : z)
                {
                    if (e.intersectsLine(l))
                        return 15000.0;
                }
            }
            return ((LongLat) v1).manhattan((LongLat) v2);
        }
        return 0;
    }
}
