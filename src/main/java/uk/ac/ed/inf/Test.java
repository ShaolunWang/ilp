package uk.ac.ed.inf;

import java.text.ParseException;
import java.util.ArrayList;

public class Test
{
    public static void main(String[] args) throws ParseException
    {
        DerbyIO test = new DerbyIO("localhost", "9876", "derbyDB");
        ArrayList<String> menuTest = test.readDerbyOrderNo("2022-04-11");
        Menus t = new Menus("localhost", "9898");
        System.out.println(menuTest);
        System.out.println(t.getDeliveryCost(menuTest.toArray(new String[menuTest.size()])));
    }
}
