package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
public class DerbyIO {
    private final String hostname;
    private final String port;
    private final String location;

    public DerbyIO(String hostname, String port, String location) {
        this.hostname = hostname;
        this.port = port;
        this.location = location;
    }

    public ArrayList<String> readDerbyOrderNo(String day) {
        String jdbcString = "jdbc:derby://" + hostname + ":" + port + "/" + location;
        Date d = Date.valueOf(day);
        ArrayList<String> foodOrders = new ArrayList<>();
        try
        {
            ArrayList<String> orderNoList = new ArrayList<>();
            Connection conn = DriverManager.getConnection(jdbcString);
            final String orderQuery =
                    "select * from orders where deliveryDate=(?)";
            PreparedStatement psOrderQuery =
                    conn.prepareStatement(orderQuery);
            psOrderQuery.setDate(1, d);
            // Search for the student’s courses and add them to a list
            ResultSet rs = psOrderQuery.executeQuery();
            while (rs.next()) {
                String num = rs.getString("orderNo");
                orderNoList.add(num);
            }

            final String orderDetailQuery =
                    "select * from orderDetails where orderNo=(?)";
            PreparedStatement psOrderDetailQuery =
                    conn.prepareStatement(orderDetailQuery);
            for (String orderNo : orderNoList)
            {
                psOrderDetailQuery.setString(1, orderNo);
                ResultSet r = psOrderDetailQuery.executeQuery();
                while (r.next())
                {
                    String food = r.getString("item");
                    foodOrders.add(food);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return foodOrders;
    }

}
