package uk.ac.ed.inf;


import java.sql.*;
import java.util.ArrayList;

public class DerbyIO
{
    private final String hostname;
    private final String port;

    private final String location;
    private final String jbdcString;
    private final Connection conn;
    public DerbyIO(String hostname, String port, String location) throws SQLException
    {
        this.hostname = hostname;
        this.port = port;
        this.location = location;
        jbdcString = "jdbc:derby://" + hostname + ":" + port + "/" + location;
        this.conn = DriverManager.getConnection(jbdcString);
    }

    public ArrayList<Order> readDerbyOrderNo(String day)
    {
        ArrayList<Order> orderList = new ArrayList<>();

        Date d = Date.valueOf(day);
        ArrayList<String> foodOrders = new ArrayList<>();
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
                Order temp = new Order(num, customer, dt, getFood(num));
                orderList.add(temp);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return orderList;
    }
    private ArrayList<String> getFood(String orderNo) throws SQLException
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
}
