import java.io.PrintStream;
import java.sql.*;
import java.util.*;
//test
public class DatabaseManager 
{
    Connection conn = null;
    Statement st = null;
    boolean isConnected;
    ResultSet rs;

    public DatabaseManager() 
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Connection to driver done");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "admin");
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
            this.st.execute("insert into test2.orders(customerId, productId, quantity, orderStatus) values ('" + custId + "', '" + prodId + "', '" + quantity + "', '" + stat + "'); ");
            return true;
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean changeStatus(int n, String status)
    {
        try 
        {
            this.st.execute("UPDATE test2.orders SET orderStatus = " + status + " WHERE orderNumber = " + n + "'); ");
            return true;
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Order getOrder(int n) 
    {
        Order newOrder = new Order("Status", 0);
        try
        {
            this.rs = this.st.executeQuery("SELECT * FROM test2.orders WHERE orderNumber = " + n);
            //this.rs = this.st.executeQuery("SELECT * FROM test2.orders");
            while ( rs.next() ) {
                String orderNumber = rs.getString("orderNumber");
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
            this.rs = this.st.executeQuery("SELECT * FROM test2.orders");
            while ( rs.next() ) {
                String orderNumber = rs.getString("orderNumber");
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
        DatabaseManager db = new DatabaseManager();
        //System.out.print(db.addOrder(1, 2, 10, "Stolen"));
        db.getOrder(1);
    }
}