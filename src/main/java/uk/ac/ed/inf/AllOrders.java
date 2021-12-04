package uk.ac.ed.inf;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AllOrders
{
    private static ArrayList<Order> allOrders;
    private static HashMap<LongLat, ArrayList<LongLat>> hashedVertices;
    private static Menus allMenus;
    private static NoFlyZone zone;
    private static DerbyIO database;

    /**
     * A class that set up and calculates the moves from all the orders and get the monetary value
     * @param date String representing the date of the orders being calculated
     * @param webPort String of the port for the webserver
     * @param dbPort String of the port of the derby database
     * @throws SQLException When sql access fails it would throw this exception
     */
    public AllOrders(String date,String webPort,String dbPort) throws SQLException
    {
        database = new DerbyIO("localhost", dbPort, "derbyDB");
        allOrders = database.readDerbyOrderNo(date);
        allMenus = new Menus("localhost", webPort);
        hashedVertices = Order.hashOrder(allOrders,allMenus);

        GeoJson noFly = new GeoJson("localhost", webPort, "no-fly-zones.geojson");
        zone = new NoFlyZone(noFly.getNoFlyZonePoints());
        database.setupWriteToDatabase();
    }

    /**
     * a function that handles the movement of th algorithm across all the orders
     * @param flightPath An arrayList representing all the movements made during a day
     * @param start LongLat object representing the starting point of the first order
     * @param beenTo ArrayList of all the visited nodes
     */
    public int totalOrder(
            ArrayList<LongLat> flightPath, LongLat start,
            @NotNull ArrayList<LongLat> beenTo) throws SQLException
    {
        int money = 0;
        // if it hasn't been through all the deliveries
        // (default case)
        while (beenTo.size() != hashedVertices.size()+1)
        {
            LongLat closestDestination = null;
            double dis = 100000;
            // greedy approach, get the closest order
            for (LongLat destination : hashedVertices.keySet())
            {
                if (destination.manhattan(start) < dis && !beenTo.contains(destination))
                    closestDestination = destination;
            }

            boolean finished = beenTo.size() == hashedVertices.size();
            // delivered everything so we got all the money
            if (closestDestination == null || finished)
            {
                money = orderValues();
                break;
            }
            // get the closest order's drone path(moves)
            SingleOrder currentOrder = new SingleOrder(hashedVertices.get(closestDestination),
                    start, closestDestination, zone.getEdgeNoFly());
            ArrayList<LongLat> path = currentOrder.getPath(zone.closeTo());
            ArrayList<LongLat> orderMoves = currentOrder.posToDestination(path, start);
            // get the distance back to AT
            ArrayList<LongLat> ATPath = currentOrder.toAT(orderMoves.get(orderMoves.size() - 1), LongLat.AT, zone.closeTo());

            // go back to AT either everything finished
            // or drone is running out of moves
            boolean noMoreOrders = flightPath.size() + ATPath.size() + orderMoves.size() > 1500;

            if (noMoreOrders)
            {
                for (Order o : allOrders)
                {
                    if (o.getDeliverTo().longitude != closestDestination.longitude
                            && o.getDeliverTo().latitude != closestDestination.latitude)
                    {
                        money += allMenus.getDeliveryCost(o.toVararg());
                    }
                }

                flightPath.addAll(ATPath);
                break;
            }

            beenTo.add(closestDestination);
            start = orderMoves.get(orderMoves.size() - 1);
            flightPath.addAll(orderMoves);
        }

        return money;
    }


    /**
     * get all order values
     * @return Integer value of all values
     */
    public static int orderValues()
    {
        int v = 0;
        for (Order o : allOrders)
            v += allMenus.getDeliveryCost(o.toVararg());
        return v;
    }
    public void writeSQL(ArrayList<LongLat> flightPath, @NotNull ArrayList<LongLat> beenTo) throws SQLException
    {
        for (LongLat x : beenTo)
        {
            if (x.latitude != LongLat.AT.latitude &&
                    x.longitude != LongLat.AT.longitude)
                database.writeToSQL(flightPath, Order.getOrderNo(x, allOrders));

        }
    }

}
