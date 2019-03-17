package main.java.swingy;

import main.java.swingy.model.Hero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;

public class DBManager {
    private final DBConnection dbConnection;
    private final Statement query;
    
    public DBManager()
    {
        dbConnection = DBConnection.getConnection();
        query = dbConnection.getQuery();
    }
    
    public void setup() throws SQLException
    {
        String database = dbConnection.getDatabase();
        
        query.executeUpdate("CREATE DATABASE IF NOT EXISTS " + database);
        query.executeUpdate("use " + database);
        query.executeUpdate("CREATE TABLE IF NOT EXISTS hero(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                    "name VARCHAR (60) NOT NULL," +
                    "type VARCHAR (60) NOT NULL," +
                    "level INT NOT NULL," +
                    "experience INT NOT NULL," +
                    "attack INT NOT NULL," +
                    "defense INT NOT NULL," +
                    "hitpoints INT NOT NULL)");
    }
    
    public Hero retrieveHero(int id) throws SQLException
    {
        ResultSet results = query.executeQuery("select * from hero where id=" + id);
        
        results.next();
        Hero hero = new Hero(results.getString("name"));
        hero.setType(results.getString("type"));
        hero.setLevel(Integer.parseInt(results.getString("level")));
    
        query.close();
        return hero;
    }
    
    public ArrayList<Hero> getHeroes() throws SQLException
    {
         ResultSet results = query.executeQuery("select * from hero");
         ArrayList<Hero> heroes = new ArrayList<Hero>();
         
         while (results.next())
         {
             Hero hero = new Hero(results.getString("name"));
             hero.setType(results.getString("type"));
             hero.setLevel(results.getInt("level"));
             hero.setAttack(results.getInt("attack"));
             hero.setDefense(results.getInt("defense"));
             hero.setHitPoints(results.getInt("hitpoints"));
             hero.setXp(results.getInt("experience"));
             
             heroes.add(hero);
         }
         
         return heroes;
    }
    
    public void createHero(Hero hero) throws SQLException
    {
        String sql = "insert into hero(name, type, level, experience, attack," +
                "defense, hitpoints) values(?, ?, ?, 0, ?, ?, ?)";
        PreparedStatement statement = dbConnection.getCurrenConnection().prepareStatement(sql);
        
        statement.setString(1, hero.getName());
        statement.setString(2, hero.getType());
        statement.setInt(3, hero.getLevel());
        statement.setInt(4, hero.getAttack());
        statement.setInt(5, hero.getDefense());
        statement.setInt(6, hero.getHitPoints());
        
        statement.executeUpdate();
    }

    public void updateHero(Hero hero) throws SQLException
    {
        String sql = "update hero set level=?, experience=?," +
            "attack=?, defense=?, hitpoints=? where name=?";
        PreparedStatement statement = dbConnection.getCurrenConnection().prepareStatement(sql);

        statement.setInt(1, hero.getLevel());
        statement.setInt(2, hero.getXp());
        statement.setInt(3, hero.getAttack());
        statement.setInt(4, hero.getDefense());
        statement.setInt(5, hero.getMaxHp());
        statement.setString(6, hero.getName());

        statement.executeUpdate();
    }
}
