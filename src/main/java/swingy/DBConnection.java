package main.java.swingy;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DBConnection
{
    private static DBConnection instance = new DBConnection();
    private Connection connection;
    private Statement query;
    private String database;
    
    private DBConnection() { }
    
    public static DBConnection getConnection()
    {
        return instance;
    }
    
    public void initConnection() throws SQLException, ClassNotFoundException
    {
        JSONObject config = MysqlConfig();
        
        //Database connection parameters
        String host = (String)config.get("host");
        database = (String)config.get("database");
        String user = (String)config.get("user");
        String password = (String)config.get("password");
        
        //Mysql connection string
        String connectionString = "jdbc:mysql://" + host +
                "?user=" + user + "&password=" + password;

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(connectionString);
        query = connection.createStatement();
    }
    
    public Connection getCurrenConnection()
    {
        return connection;
    }
    
    public void closeConnection() throws SQLException
    {
        connection.close();     
    }

    public Statement getQuery()
    {
        return query;
    }
    
    public String getDatabase()
    {
        return database;
    }
    
    private JSONObject MysqlConfig()
    {
        JSONParser parser = new JSONParser();
        JSONObject mysqlObj = null;
        
        try
        {
            mysqlObj = (JSONObject)parser.parse(new FileReader("config.json"));
        }
        catch (FileNotFoundException e) {
            System.out.println("Json file not found.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        } catch (ParseException e) {
            System.out.println("Error parsing Json: " + e.getMessage());
        }
        
        return mysqlObj;
    }
}
