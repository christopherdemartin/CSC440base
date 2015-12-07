import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseStuff 
{
    public static String db() 
	{
        Connection conn = null;
        Statement st = null;
        try 
		{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Connection to driver done");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Matchman11");
            st = conn.createStatement();
            st.execute("insert into sakila.actor (first_name, last_name) values ('kris', 'pepper'); ");
            return "good";
        }
        catch (Exception ex) 
		{
            System.out.println("sqlException: " + ex.getMessage());
            return "bad";
        }
    }

    public static void main(String[] args) 
	{
        DatabaseStuff.db();
    }
}