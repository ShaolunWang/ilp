package uk.ac.ed.inf;


import org.jgrapht.Graph;


import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.tour.ChristofidesThreeHalvesApproxMetricTSP;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.awt.geom.Line2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DroneGraph
{
    private final HashMap<LongLat, ArrayList<LongLat>> hashedVertices;
    private Graph<LongLat, DefaultEdge> deliverOrder;

    public DroneGraph(HashMap<LongLat, ArrayList<LongLat>> hashedVertices)
    {
        this.hashedVertices = hashedVertices;
    }

}
