package uk.ac.ed.inf;

public class Main
{
    public static void main(String args[])
    {
        Request getMenus = new Request("localhost", "9898","/menus/menus.json");
        MenuData test = new MenuData(getMenus.requestAccess());
        test.buildTree();
        test.printTree();
    }
}
