package uk.ac.ed.inf;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;


public class SingleOrder
{
    private final ArrayList<LongLat> currentOrder;
    private final LongLat destination;
    private final LongLat start;

    public SingleOrder(ArrayList<LongLat> currentOrder, LongLat destination, LongLat start)
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
        constructAStarGraph(close,this.currentOrder, noFly,  delivery, this.destination,this.start);

        Heuristic<LongLat> a = new Heuristic<>(noFly);
        AStarShortestPath<LongLat, DefaultEdge> astar = new AStarShortestPath<>(delivery, a);
        compareCurrentPaths(currentPath, astar);

        return currentPath;
    }


    private void constructAStarGraph(ArrayList<LongLat> close, ArrayList<LongLat> currentOrder,
                                     ArrayList<ArrayList<Line2D>> noFly,
                                     Graph<LongLat, DefaultEdge> delivery,
                                     LongLat destination,LongLat start)
    {
        delivery.addVertex(destination);
        delivery.addVertex(start);
        if (noIntersect(start, destination, noFly))
        {
            DefaultEdge b = delivery.addEdge(start, destination);
            delivery.setEdgeWeight(b, start.noFlyDistanceTo(destination, noFly));
        }
        for (int i = 0; i < currentOrder.size();i++)
        {
            LongLat shops = currentOrder.get(i);
            delivery.addVertex(shops);
            if (noIntersect(start, shops, noFly))
            {
                DefaultEdge b = delivery.addEdge(start, shops);
                delivery.setEdgeWeight(b, start.noFlyDistanceTo(shops, noFly));
            }
            if (noIntersect(shops, destination, noFly))
            {
                DefaultEdge b = delivery.addEdge(shops, destination);
                delivery.setEdgeWeight(b, shops.noFlyDistanceTo(destination, noFly));
            }
            if (i == 1)
            {
                if (noIntersect(shops, destination, noFly))
                {
                    DefaultEdge b = delivery.addEdge(shops, currentOrder.get(0));
                    delivery.setEdgeWeight(b, currentOrder.get(0).noFlyDistanceTo(shops, noFly));
                }
            }
        }

        for (LongLat p : close)
        {
            delivery.addVertex(p);
            for (LongLat q: close)
            {
                if (!delivery.containsVertex(q) && delivery.containsEdge(p, q))
                {
                    delivery.setEdgeWeight(p, q, p.noFlyDistanceTo(q, noFly));
                }
            }

            for (LongLat x : currentOrder)
            {
                if (noIntersect(p, x, noFly))
                {
                    DefaultEdge temp = delivery.addEdge(p, x);
                    delivery.setEdgeWeight(temp, p.noFlyDistanceTo(x, noFly));
                }

            }
            if (noIntersect(start, p, noFly))
            {
                DefaultEdge toShop = delivery.addEdge(start, p);
                delivery.setEdgeWeight(toShop, start.noFlyDistanceTo(p, noFly));
            }
           if (noIntersect(p, destination, noFly))
           {
               DefaultEdge toShop = delivery.addEdge(p, destination);
               delivery.setEdgeWeight(toShop, start.noFlyDistanceTo(p, noFly));
           }

        }


    }

    /**
     * get current path, from given d
     * @param currentPath
     * @param astar
     */
    private void compareCurrentPaths(ArrayList<LongLat> currentPath,
                                     AStarShortestPath<LongLat, DefaultEdge> astar)
    {
        if (currentOrder.size() == 1)
        {

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
            if (val_1 <= val_2)
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
    }

    public ArrayList<LongLat> posToDestination(ArrayList<LongLat> destinations, LongLat start)
    {
        LongLat startPoint = start;
        ArrayList<LongLat> toDes = new ArrayList<>();
        Collections.reverse(destinations);
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

            }
        }
        return toDes;
    }
    

    private boolean noIntersect(LongLat p, LongLat x, ArrayList<ArrayList<Line2D>> noFly)
    {
        Line2D e = new Line2D.Double(p.longitude, p.latitude, x.longitude, x.latitude);
        for (ArrayList<Line2D> f: noFly)
        {
            for (Line2D l : f)
            {
                if (l.intersectsLine(e))
                    return false;
            }

        }
        return true;

    }
    public ArrayList<LongLat> toAT(ArrayList<ArrayList<Line2D>> noFly,
                                   LongLat start, LongLat end,
                                    ArrayList<LongLat> close)
    {
        ArrayList<LongLat> placeHolder = new ArrayList<>();
        Graph<LongLat, DefaultEdge> home =
                new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);
        Heuristic<LongLat> h = new Heuristic<>(noFly);
        AStarShortestPath<LongLat, DefaultEdge> astar = new AStarShortestPath<>(home, h);

        constructAStarGraph(close, placeHolder, noFly, home, end, start);
        return (ArrayList<LongLat>) astar.getPath(start, end).getVertexList();
    }



}
