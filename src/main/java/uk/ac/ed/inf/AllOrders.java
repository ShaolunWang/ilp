package uk.ac.ed.inf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AllOrders
{
    private static ArrayList<Order> allOrders;
    private static HashMap<LongLat, ArrayList<LongLat>> hashedVertices;
    private static Menus allMenus;
    private static NoFlyZone zone;

    /**
     * A class that set up and calculates the moves from all the orders and get the monetary value
     * @param date String representing the date of the orders being calculated
     * @param geoJsonDate String that's in the form of the GeoJson format
     * @param webPort String of the port for the webserver
     * @param dbPort String of the port of the derby database
     * @throws SQLException When sql access fails it would throw this exception
     */
    public AllOrders(String date,String geoJsonDate,String webPort,String dbPort) throws SQLException
    {
        DerbyIO database = new DerbyIO("localhost", dbPort, "derbyDB");
        allOrders = database.readDerbyOrderNo(date);
        allMenus = new Menus("localhost", webPort);
        hashedVertices = Order.hashOrder(allOrders,allMenus);

        GeoJson noFly = new GeoJson("localhost", webPort, "no-fly-zones.geojson");
        zone = new NoFlyZone(noFly.getNoFlyZonePoints());

    }

    /**
     * a function that handles the movement of th algorithm across all the ordrs
     * @param monetaryValue Integer value of the money earned by delivering the most possible ordeer
     * @param flightPath An arrayList representing all the movements made during a day
     * @param start LongLat object representing the starting point of the first order
     * @param beenTo ArrayList of all the visited nodes
     */
    public void totalOrder(int monetaryValue,
                           ArrayList<LongLat> flightPath, LongLat start,
                           ArrayList<LongLat> beenTo)
    {
        beenTo.clear();
        // if haven't been through all the deliveries
        // (default case)
        while (beenTo.size() != hashedVertices.size())
        {
            LongLat closestDestination = null;
            double dis = 100000;
            // greedy approach, get the closest order
            for (LongLat destination : hashedVertices.keySet())
            {
                if (destination.manhattan(start) < dis && !beenTo.contains(destination))
                    closestDestination = destination;
            }

            // get the closest order's drone path(moves)
            SingleOrder currentOrder = new SingleOrder(hashedVertices.get(closestDestination),
                    start, closestDestination, zone.getEdgeNoFly());
            ArrayList<LongLat> path = currentOrder.getPath(zone.closeTo());
            ArrayList<LongLat> orderMoves = currentOrder.posToDestination(path, start);

            // get the distance back to AT
            ArrayList<LongLat> ATPath = currentOrder.toAT(start, LongLat.AT, zone.closeTo());

            boolean noMoreOrders = flightPath.size() + ATPath.size() + orderMoves.size() > 1500;
            boolean finished     = beenTo.size() == hashedVertices.size();
            // go back to AT either everything finished
            // or drone is running out of moves
            if (finished || noMoreOrders)
            {
                int money = 0;
                for (Order o : allOrders)
                {
                    assert closestDestination != null;
                    if (o.getDeliverTo().longitude != closestDestination.longitude
                    && o.getDeliverTo().latitude != closestDestination.latitude)
                        money += allMenus.getDeliveryCost(o.toVararg());
                }
                monetaryValue = money;
                flightPath.addAll(ATPath);
                break;
            }

            start = orderMoves.get(orderMoves.size() - 1);
            beenTo.add(closestDestination);
            flightPath.addAll(orderMoves);
        }
    }
}
