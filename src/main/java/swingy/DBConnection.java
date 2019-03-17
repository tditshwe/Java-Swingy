package main.java.swingy;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection
{
    private static final DBConnection instance = new DBConnection();
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
        String host = "localhost";
        database = "swingy";
        String user = "root";
        String password = "dbpass";
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
}
