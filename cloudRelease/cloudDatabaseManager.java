import java.io.PrintStream;
import java.sql.*;
import java.util.*;

public class cloudDatabaseManager 
{
    Connection conn = null;
    Statement st = null;
    boolean isConnected;
    ResultSet rs;

    public cloudDatabaseManager() 
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Connection to driver done");
            this.conn = DriverManager.getConnection("jdbc:mysql://ec2-54-173-84-63.compute-1.amazonaws.com:3306/", "java", "csc440");
            this.st = this.conn.createStatement();
        }
        catch (Exception ex) 
        {
            System.out.println("sqlException: " + ex.getMessage());
        }
    }

    public boolean addOrder(int custId, int prodId, int quantity, String stat) 
    {
        try 
        {
            this.st.execute("insert into database.orders(customerId, productId, quantity, orderStatus) values ('" + custId + "', '" + prodId + "', '" + quantity + "', '" + stat + "'); ");
            return true;
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean changeOrder(String column, int n, String status)
    {
        try 
        {
            this.st.execute("UPDATE database.orders SET " + column + " = '" + status + "' WHERE id = " + n);
            return true;
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    //public boolean removeOrder(int id)
    //{
        
    //}

    public Order getOrder(int n) 
    {
        Order newOrder = new Order("Status", 0);
        try
        {
            this.rs = this.st.executeQuery("SELECT * FROM database.orders WHERE id = " + n);
            //this.rs = this.st.executeQuery("SELECT * FROM database.orders");
            while ( rs.next() ) {
                String orderNumber = rs.getString("id");
                String customerId = rs.getString("customerId");
                String productId = rs.getString("productId");
                String quantity = rs.getString("quantity");
                String orderStatus = rs.getString("orderStatus");
                /*System.out.println(customerId);
                System.out.println(productId);
                System.out.println(quantity);
                System.out.println(orderStatus);*/

                newOrder.setStatus(orderStatus);
                newOrder.setOrderNum(Integer.parseInt(orderNumber));

            }
        }
        catch(Exception ex)
        {
            //throw new Error("Unresolved compilation problems: \n\tCannot make a static reference to the non-static field st\n\tSyntax error on token \"catch\", finally expected\n");
            System.out.println("Can't get the order.");
        }
        return newOrder;
    }

    public ArrayList<Order> getAllOrders() 
    {
        //THIS IS FOR POPULATING THE LIST OF ORDERS, NOT GETTING ANY SPECIFIC ORDER
        ArrayList<Order> allOrders = new ArrayList<Order>();
        try
        {
            this.rs = this.st.executeQuery("SELECT * FROM database.orders");
            while ( rs.next() ) {
                String orderNumber = rs.getString("id");
                String customerId = rs.getString("customerId");
                String productId = rs.getString("productId");
                String quantity = rs.getString("quantity");
                String orderStatus = rs.getString("orderStatus");

                //FOR TESTING
                /*System.out.println(customerId);
                System.out.println(productId);
                System.out.println(quantity);
                System.out.println(orderStatus);*/

                Order newOrder = new Order(orderStatus, Integer.parseInt(orderNumber));
                allOrders.add(newOrder);
            }
        }
        catch(Exception ex)
        {
            //throw new Error("Unresolved compilation problems: \n\tCannot make a static reference to the non-static field st\n\tSyntax error on token \"catch\", finally expected\n");
            System.out.println("Can't get the orders.");
        }
        return allOrders;
    }

    public static void main(String[] args) 
    {
        cloudDatabaseManager db = new cloudDatabaseManager();
        //System.out.print(db.addOrder(2, 1, 5, "Placed"));
        db.getAllOrders();
        //db.getOrder(1);
        //db.changeStatus(4, "Exploded");
    }
}
