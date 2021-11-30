package uk.ac.ed.inf;


import org.jgrapht.Graph;


import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.tour.ChristofidesThreeHalvesApproxMetricTSP;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

public class DroneGraph
{
    private Graph<LongLat, DefaultEdge> deliverOrder;
    private final HashMap<LongLat, ArrayList<LongLat>> hashedVertices;
    public DroneGraph(HashMap<LongLat, ArrayList<LongLat>> hashedVertices)
    {
        this.hashedVertices = hashedVertices;
    }

    public void getPath(ArrayList<Line2D> noFly)
    {
        //AT
        LongLat CurrentPos = new LongLat(3.186874,5.944494);


        for (LongLat deliveryPoints: hashedVertices.keySet())
        {
            deliverOrder = new DefaultDirectedWeightedGraph<>(DefaultEdge.class);
            deliverOrder.addVertex(CurrentPos);
            deliverOrder.addVertex(deliveryPoints);
            for (LongLat shop1 : hashedVertices.get(deliveryPoints))
            {
                deliverOrder.addVertex(shop1);
                DefaultEdge temp2 = deliverOrder.addEdge(CurrentPos, shop1);
                deliverOrder.setEdgeWeight(temp2, getDistance(CurrentPos, shop1, noFly));
                for (LongLat shop2 : hashedVertices.get(deliveryPoints))
                {
                    if (shop1.distanceTo(shop2) == 0)
                        continue;
                    else
                    {
                        deliverOrder.addVertex(shop2);
                        System.out.println(shop2);
                        DefaultEdge temp1 = deliverOrder.addEdge(shop1, shop2);
                        deliverOrder.setEdgeWeight(temp1, getDistance(shop1, shop2, noFly));
                    }
                }



                DefaultEdge temp3 = deliverOrder.addEdge(shop1, deliveryPoints);
                deliverOrder.setEdgeWeight(temp3, getDistance(shop1, deliveryPoints, noFly));
            }


            AStarAdmissibleHeuristic<LongLat> a = (v1, v2) -> getDistance(v1,v2,noFly);

            AStarShortestPath astar = new AStarShortestPath(deliverOrder, a);
            System.out.println(astar.getPath(CurrentPos, deliveryPoints));
            CurrentPos = deliveryPoints;
        }

    }
    private Double getDistance(LongLat v1, LongLat v2, ArrayList<Line2D> noFly)
    {
        Line2D e = new Line2D.Double(v1.longitude, v1.latitude, v2.longitude, v2.latitude);
        for (Line2D i : noFly)
        {
            if (e.intersectsLine(i))
                return 50000.0;
        }
        return v1.distanceTo(v2);
    }


}
