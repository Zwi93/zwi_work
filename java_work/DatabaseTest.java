import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class DatabaseTest 
{
    static final String URL = "jdbc:postgresql://localhost/grace_dp";

    public static void main ( String args[] )
    {
        Connection connection = null;

        try 
        {
            connection = DriverManager.getConnection(URL, "zwi", "Zwi");
            System.out.println("Connected");
            
            Map< String, String > mapCollection = new HashMap< String, String>();
            
            Scanner scanner = new Scanner( System.in );
            
            System.out.println("Enter your Name: ");
            String name = scanner.nextLine();
            mapCollection.put("Name", name);
            
            System.out.println("Enter your Surname: ");
            String surname = scanner.nextLine();
            mapCollection.put("Surname", surname);

            System.out.println("Enter your Email: ");
            String email = scanner.nextLine();
            mapCollection.put("Email", email);

            System.out.println("Enter your Password: ");
            String password = scanner.nextLine();
            mapCollection.put("Password", password);

            updateTable("tenants_details", connection, mapCollection);

            queryTable("tenants_details", connection);

            scanner.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Error : " + e);    
        }
        finally
        {
            try
            {
                connection.close();
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    private static void queryTable (String tableName, Connection connectedness)
    {
        Statement queryStatement;
        ResultSet resultSet;

        try 
        {
            queryStatement = connectedness.createStatement();
            resultSet = queryStatement.executeQuery(
                "SELECT * FROM " + tableName);

            while ( resultSet.next() )
            {
                for ( int i = 1; i <= 5; i++)
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                System.out.println();
            }

            queryStatement.close();
            resultSet.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }

    private static void updateTable (String tableName, Connection connectedness, Map< String, String > mapCollection)
    {
        PreparedStatement updateStatement;
        int result = 0;

        try
        {
            //updateStatement = connectedness.createStatement();
            updateStatement = connectedness.prepareStatement(
                "INSERT INTO " + tableName + " ( name, surname, email, password ) " + " VALUES (?, ?, ?, ?) ");
            
            updateStatement.setString(1, mapCollection.get("Name"));
            updateStatement.setString(2, mapCollection.get("Surname"));
            updateStatement.setString(3, mapCollection.get("Email"));
            updateStatement.setString(4, mapCollection.get("Password"));
            
            result = updateStatement.executeUpdate();
            
            updateStatement.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }   
    }
}