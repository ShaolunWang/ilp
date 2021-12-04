package uk.ac.ed.inf;

import org.jetbrains.annotations.NotNull;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;

import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * A class that calculate the path of a single order
 */
public class SingleOrder
{
    private final ArrayList<LongLat> currentOrder;
    private final LongLat destination;
    private final LongLat start;
    private final ArrayList<ArrayList<Line2D>> noFly;

    public SingleOrder(ArrayList<LongLat> currentOrder, LongLat destination, LongLat start,ArrayList<ArrayList<Line2D>> noFly)
    {
        this.currentOrder = currentOrder;
        this.start = start;
        this.destination = destination;
        this.noFly = noFly;
    }

    /**
     * This function gets a path (not moves) calculated by a*
     * @param close all the points that's close to no-fly zone corners
     * @return a list of vertices being accessed
     */
    public ArrayList<LongLat> getPath(ArrayList<LongLat> close)
    {
        ArrayList<LongLat> currentPath = new ArrayList<>();
        Graph<LongLat, DefaultEdge> delivery =
                new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);
        constructAStarGraph(close,this.currentOrder, delivery, this.destination,this.start);

        Heuristic<LongLat> h = new Heuristic<>(noFly);
        AStarShortestPath<LongLat, DefaultEdge> astar = new AStarShortestPath<>(delivery, h);
        compareCurrentPaths(currentPath, astar);

        return currentPath;
    }

    /**
     * This function constructs a graph for a* to calculate
     * @param close all the points that's close to no-fly zone corners
     * @param currentOrder an arrayList of  all the shops will be visited in current order
     * @param delivery a Graph object that describes the delivery
     * @param destination the LongLat point that will be visited
     * @param start the Longlat point that we start traversing from
     */
    private void constructAStarGraph(ArrayList<LongLat> close, @NotNull ArrayList<LongLat> currentOrder,
                                     @NotNull Graph<LongLat, DefaultEdge> delivery,
                                     LongLat destination, LongLat start)
    {
        /*
         * Process:
         * Add vertices
         * check whether edges between vertices intersects the no-fly zone
         * add edges, and it's weight
         */
        delivery.addVertex(destination);
        delivery.addVertex(start);
        addEdgeToGraph(delivery, start , destination);

        for (int i = 0; i < currentOrder.size();i++)
        {
            LongLat shops = currentOrder.get(i);
            delivery.addVertex(shops);
            addEdgeToGraph(delivery, start,  shops );
            addEdgeToGraph(delivery, shops,  destination);
            if (i == 1)
            {
                addEdgeToGraph(delivery, shops, currentOrder.get(0));
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
                addEdgeToGraph(delivery,  x, p);
            }
            addEdgeToGraph(delivery, start, p);
            addEdgeToGraph(delivery, p, destination);

        }


    }

    /**
     * add edges to graph if they do not intersect with no-fly zones
     * @param delivery Graph of all the vertices
     * @param p LongLat vertex one
     * @param q LongLat vertex two
     */
    private void addEdgeToGraph(Graph<LongLat, DefaultEdge> delivery, LongLat p, LongLat q)
    {
        if (noIntersect(p, q))
        {
            DefaultEdge toShop = delivery.addEdge(p, q);
            delivery.setEdgeWeight(toShop, p.noFlyDistanceTo(q, noFly));
        }
    }


    /**
     * get the best path for current order
     * @param currentPath an arrayList of all possible paths
     * @param astar AStarShortestPath object for getting weight and paths
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

            val_1 = getPathWeight(astar, 0, 1);
            val_2 = getPathWeight(astar, 1, 0);
            if (val_1 <= val_2)
                getShopPaths(currentPath, astar, 0, 1);
            else
                getShopPaths(currentPath, astar, 1, 0);
        }
    }

    /**
     * utility function to get path weights for comparison
     * @param astar AStarShortestPath object for getting path weight
     * @param i representing shop_i to be visited
     * @param j representing shop_j to be visited
     * @return double value of weight
     */
    private double getPathWeight(@NotNull AStarShortestPath<LongLat, DefaultEdge> astar, int i, int j)
    {
        double val;
        val = astar.getPathWeight(start, currentOrder.get(i))
                + astar.getPathWeight(currentOrder.get(i), currentOrder.get(j))
                + astar.getPathWeight(currentOrder.get(j), destination);
        return val;
    }

    /**
     * concatenate all the paths in an order together
     * @param currentPath an arraylist of all paths in the current order
     * @param astar  AStarShortestPath object for getting the paths
     * @param i representing shop_i
     * @param j representing shop_j
     */
    private void getShopPaths(@NotNull ArrayList<LongLat> currentPath, @NotNull AStarShortestPath<LongLat, DefaultEdge> astar, int i, int j) {
        currentPath.addAll(astar.getPath(start, currentOrder.get(i))
                .getVertexList());

        currentPath.addAll(astar.getPath(currentOrder.get(i), currentOrder.get(j))
                .getVertexList());

        currentPath.addAll(astar.getPath(currentOrder.get(j), destination)
                .getVertexList());
    }

    /**
     * A function that let the drone walks on the path generated by a*
     * @param destinations LongLat object representing the vertex that's being visiting
     * @param start LongLat object representing current position
     * @return An arraylist of all the moves being made
     */
    public ArrayList<LongLat> posToDestination(@NotNull ArrayList<LongLat> destinations, LongLat start)
    {
        LongLat startPoint = start;
        ArrayList<LongLat> toDes = new ArrayList<>();

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
    
    /**
     * utility function that checks whether the line between two points intersects
     * @param p LongLat point one
     * @param x LongLat point two
     * @return boolean value of intersection
     */
    private boolean noIntersect(@NotNull LongLat p, @NotNull LongLat x)
    {
        Line2D e = new Line2D.Double(p.longitude, p.latitude, x.longitude, x.latitude);
        for (ArrayList<Line2D> f: this.noFly)
        {
            for (Line2D l : f)
            {
                if (l.intersectsLine(e))
                    return false;
            }
        }
        return true;
    }

    /**
     * get the path from current point to Appleton Tower
     * @param start LongLat object representing current position
     * @param end LongLat object for appleton tower
     * @param close An arraylist of points that's close to the corners of no-fly zone
     * @return an arrayList of all th vertices being visited in a*
     */
    public ArrayList<LongLat> toAT(
            LongLat start, LongLat end,
            ArrayList<LongLat> close)
    {
        ArrayList<LongLat> placeHolder = new ArrayList<>();
        Graph<LongLat, DefaultEdge> home =
                new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);
        Heuristic<LongLat> h = new Heuristic<>(noFly);
        AStarShortestPath<LongLat, DefaultEdge> astar = new AStarShortestPath<>(home, h);
        if (end != null)
        {

            constructAStarGraph(close, placeHolder,  home, end, start);
            return (ArrayList<LongLat>) astar.getPath(start, end).getVertexList();
        }
        return placeHolder;
    }

}