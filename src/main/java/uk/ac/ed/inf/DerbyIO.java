package uk.ac.ed.inf;


import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;

public class DerbyIO
{

    public final Connection conn;
    public DerbyIO(String hostname, String port, String location) throws SQLException
    {
        String jbdcString = "jdbc:derby://" + hostname + ":" + port + "/" + location;
        this.conn = DriverManager.getConnection(jbdcString);
    }

    /**
     * return an arraylist of orders on a given day
     * @param day String representing the day of order being calculated
     * @return an arrayList of orders
     */
    public ArrayList<Order> readDerbyOrderNo(String day)
    {
        ArrayList<Order> orderList = new ArrayList<>();

        Date d = Date.valueOf(day);
        try
        {
            final String orderQuery =
                    "select * from orders where deliveryDate=(?)";
            PreparedStatement psOrderQuery =
                    conn.prepareStatement(orderQuery);
            psOrderQuery.setDate(1, d);
            // Search for the student’s courses and add them to a list
            ResultSet rs = psOrderQuery.executeQuery();
            while (rs.next())
            {
                String num = rs.getString("orderNo");
                String dt  = rs.getString("deliverTo");
                String customer = rs.getString("customer");
                Order temp = new Order(num, dt, getFood(num));
                orderList.add(temp);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return orderList;
    }

    /**
     * Takes an orderNo and gives an arraylist of ordered food
     * @param orderNo A string representing which order is being fetched from database
     * @return an arrayList of food
     * @throws SQLException throws SQLException if having issues accessing the database
     */

    private @NotNull ArrayList<String> getFood(String orderNo) throws SQLException
    {
        ArrayList<String> food  = new ArrayList<>();
        final String orderDetailQuery =
                "select * from orderDetails where orderNo=(?)";
        PreparedStatement psOrderDetailQuery =
                conn.prepareStatement(orderDetailQuery);

        psOrderDetailQuery.setString(1, orderNo);
        ResultSet r = psOrderDetailQuery.executeQuery();
        while (r.next())
        {
            String temp;
            temp = r.getString("item");
            food.add(temp);
        }
        return food;
    }

    /**
     * Write paths to sql
     * @param flightPath an ArrayList of all the moves
     * @param orderNo current orderNo
     * @throws SQLException throws it when having sql errors
     */
    public void writeToSQL(@NotNull ArrayList<LongLat> flightPath, String orderNo) throws SQLException
    {
        PreparedStatement psFlightpath =
                conn.prepareStatement("insert into flightpath values" +
                        "(?, ?, ?, ?, ?, ?)");
        for (int i =0; i < flightPath.size()-1;i++)
        {
            psFlightpath.setString(1, String.valueOf(orderNo));
            psFlightpath.setString(2, String.valueOf(flightPath.get(i).longitude));
            psFlightpath.setString(3, String.valueOf(flightPath.get(i).latitude));
            psFlightpath.setString(3, String.valueOf(flightPath.get(i).angle));
            psFlightpath.setString(5, String.valueOf(flightPath.get(i + 1).longitude));
            psFlightpath.setString(6, String.valueOf(flightPath.get(i + 1).latitude));
        }
    }

    /**
     * set up writing sql table for the moves
     * @throws SQLException Exception when SQL fails
     */
    public void setupWriteToDatabase() throws SQLException
    {
        Statement statement = conn.createStatement();
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null,"FLIGHTPATH", null);
        if (resultSet.next())
            statement.execute("drop table flightpath");
        statement.execute(
                "create table flightpath(orderNo char(8)," +
                        "fromLongitude double,fromLatitude double," +
                        "angle integer,toLongitude double," +
                        "toLatitude double)");
    }
}
