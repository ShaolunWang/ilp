package uk.ac.ed.inf;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;

import java.awt.geom.Line2D;
import java.util.*;


public class OrderWrapper
{
    private final ArrayList<LongLat> currentOrder;
    private final LongLat destination;
    private final LongLat start;

    public OrderWrapper(ArrayList<LongLat> currentOrder, LongLat destination, LongLat start)
    {
        this.currentOrder = currentOrder;
        this.start = start;
        this.destination = destination;
    }

    public ArrayList<LongLat> getPath(ArrayList<LongLat> close, ArrayList<ArrayList<Line2D>> noFly)
    {
        ArrayList<LongLat> currentPath = new ArrayList<>();
        Graph<LongLat, DefaultEdge> delivery =
                new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);
        delivery.addVertex(destination);
        delivery.addVertex(start);
        DefaultEdge b = delivery.addEdge(start, destination);
        delivery.setEdgeWeight(b, start.noFlyDistanceTo(destination, noFly));
        for (LongLat x : currentOrder)
        {
            delivery.addVertex(x);
            DefaultEdge temp2 = delivery.addEdge(x, destination);
            delivery.setEdgeWeight(temp2, destination.noFlyDistanceTo(x, noFly));
        }

        for (LongLat p : close)
        {
            delivery.addVertex(p);
            for (LongLat q: close)
            {
                if (p.noFlyDistanceTo(q, noFly) != 0)
                {
                    delivery.addVertex(q);

                    DefaultEdge temp = delivery.addEdge(p, q);
                    //if (temp != null)
                    //    delivery.setEdgeWeight(temp, p.noFlyDistanceTo(p, noFly));
                }
            }
            for (LongLat x : currentOrder)
            {
                DefaultEdge temp = delivery.addEdge(p, x);
                delivery.setEdgeWeight(temp, p.noFlyDistanceTo(x, noFly));

            }
            DefaultEdge toShop = delivery.addEdge(start, p);
            delivery.setEdgeWeight(toShop, start.noFlyDistanceTo(p, noFly));
            toShop = delivery.addEdge(p, destination);
            delivery.setEdgeWeight(toShop, start.noFlyDistanceTo(p, noFly));

        }

        AStarAdmissibleHeuristic<LongLat> a = (v1, v2) -> getDistance(v1, v2, noFly);


        AStarShortestPath<LongLat, DefaultEdge> astar = new AStarShortestPath<>(delivery, a);
        //DijkstraShortestPath<LongLat, DefaultEdge> astar = new DijkstraShortestPath<>(delivery);
        if (currentOrder.size() == 1)
        {
            System.out.println(astar.getPathWeight(start, currentOrder.get(0))
                    + astar.getPathWeight(currentOrder.get(0), destination));
            currentPath.addAll(astar.getPath(start, currentOrder.get(0))
                    .getVertexList());
            currentPath.addAll(astar.getPath(currentOrder.get(0), destination)
                    .getVertexList());
        }
        else
        {
            double val_1;
            double val_2;

            val_1 = astar.getPathWeight(start, currentOrder.get(0))
                    + astar.getPathWeight(currentOrder.get(0), currentOrder.get(1))
                    + astar.getPathWeight(currentOrder.get(1), destination);
            val_2 = astar.getPathWeight(start, currentOrder.get(1))
                    + astar.getPathWeight(currentOrder.get(1), currentOrder.get(0))
                    + astar.getPathWeight(currentOrder.get(0), destination);
            if (val_1 < val_2)
            {

                currentPath.addAll(astar.getPath(start, currentOrder.get(0))
                            .getVertexList());

                currentPath.addAll(astar.getPath(currentOrder.get(0), currentOrder.get(1))
                            .getVertexList());

                currentPath.addAll(astar.getPath(currentOrder.get(1), destination)
                        .getVertexList());
            }
            else
            {
                currentPath.addAll(astar.getPath(start, currentOrder.get(1))
                        .getVertexList());
                currentPath.addAll(astar.getPath(currentOrder.get(1), currentOrder.get(0))
                        .getVertexList());
                currentPath.addAll(astar.getPath(currentOrder.get(0), destination)
                        .getVertexList());
            }


        }

        return currentPath;
    }
    private Double getDistance(LongLat v1, LongLat v2, ArrayList<ArrayList<Line2D>> noFly)
    {
        Line2D e = new Line2D.Double(v1.longitude, v1.latitude, v2.longitude, v2.latitude);
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
        return (Math.abs(v1.longitude - v2.longitude)+ Math.abs(v1.latitude-v2.latitude));
    }

    public ArrayList<LongLat> posToDestination(ArrayList<LongLat> destinations)
    {
        LongLat startPoint = new LongLat(-3.186874, 55.944494);
        ArrayList<LongLat> toDes = new ArrayList<>();
        Collections.reverse(destinations);
        GeoJsonRW test = new GeoJsonRW("localhost", "9898", "no-fly-zones.geojson");

        for (LongLat nextDestination: destinations)
        {
            toDes.add(startPoint);
            while (!startPoint.closeTo(nextDestination))
            {


                int temp = (int) Math.toDegrees(Math.atan2(
                        nextDestination.latitude - startPoint.latitude,
                        nextDestination.longitude - startPoint.longitude));
                double t = ((temp+360)%360)/10.0;
                int angle = (int) (Math.round(t)*10);


                startPoint = startPoint.nextPosition(angle);
                toDes.add(startPoint);
                //System.out.println(angle);

                //System.out.println(test.mkFeatureCollection(toDes).toJson());
            }
            //System.out.println(nextDestination.latitude + " " + nextDestination.longitude);
        }
        return toDes;
    }


    public ArrayList<LongLat> format(ArrayList<LongLat> points)
    {

        ArrayList<LongLat> fpoints = (ArrayList<LongLat>) points.clone();

        for (LongLat p : points)
            fpoints.removeIf(i -> i.closeTo(p)
                    && i.latitude != p.latitude
                    && i.longitude != p.longitude);
        return fpoints;
    }




}
