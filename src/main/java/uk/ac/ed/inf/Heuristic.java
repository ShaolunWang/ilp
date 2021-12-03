package uk.ac.ed.inf;

import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;

import java.awt.geom.Line2D;
import java.util.ArrayList;

class Heuristic<V> implements AStarAdmissibleHeuristic<V>
{
    private final ArrayList<ArrayList<Line2D>> noFly;
    public Heuristic(ArrayList<ArrayList<Line2D>> noFly)
    {
        this.noFly = noFly;
    }

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
                        return 1500000.0;
                }
            }
            return (Math.abs(p1.longitude - p2.longitude) + Math.abs(p1.latitude - p2.latitude));
        }
        return 0;
    }
}
