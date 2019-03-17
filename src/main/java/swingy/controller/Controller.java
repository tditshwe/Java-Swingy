package main.java.swingy.controller;

import main.java.swingy.view.MainFrame;
import main.java.swingy.DBManager;
import main.java.swingy.model.Enemy;
import main.java.swingy.model.Hero;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class Controller {
   protected Hero hero;
   private Enemy enemy;
   protected DBManager db;
   
   public Controller(Hero hero)
   {
       this.hero = hero;
       db = new DBManager();
   }
   
   public Controller (Enemy enemy)
   {
       this.enemy = enemy;
       db = new DBManager();
   }
   
   public void updateHeroStats(JTextArea view)
   {
        view.setText("Name: " + hero.getName() + "\n");
        view.append("Class: " + hero.getType() + "\n");
        view.append("Level: " + hero.getLevel() + "\n");
        view.append("Attack: " + hero.getAttack() + "\n");
        view.append("Defense: " + hero.getDefense() + "\n");
        view.append("Hitpoints: " + hero.getHitPoints() + "\n");
        view.append("XP: " + hero.getXp() + "\n");
   }
   
   public void updateLog(MainFrame frame)
   {
       ArrayList<String> log;      
       log = hero.getLog();

        for (String L: log)
        {
            frame.updateLog(L);
        }
   }
   
   public void createHero() throws SQLException
   {
        int attack, defense, hitPoints;

        switch (hero.getType())
        {
          case "Wizard":
            attack = 13;
            defense = 4;
            hitPoints = 80;
          break;
          case "Alchemist":
            attack = 15;
            defense = 3;
            hitPoints = 90;
          break;
          default:
            attack = 25;
            defense = 2;
            hitPoints = 100;
          break;
        }

        hero.setAttack(attack);
        hero.setDefense(defense);
        hero.setHitPoints(hitPoints);

        db.createHero(hero);
   }
   
   public ArrayList<Hero> getHeroes() throws SQLException
   {
       return db.getHeroes();
   }
   
   public void createEnemy()
   {
       int attack, defense, hitPoints;

        switch (enemy.getName())
        {
          case "Demon":
            attack = 15;
            defense = 3;
            hitPoints = 50;
          break;
          case "Ghost":
            attack = 25;
            defense = 5;
            hitPoints = 100;
          break;
          default:
            attack = 40;
            defense = 10;
            hitPoints = 150;
          break;
        }

        enemy.setAttack(attack);
        enemy.setDefense(defense);
        enemy.setHitPoints(hitPoints);
   }

   public void updateHero() throws SQLException
   {
    db.updateHero(hero);
   }
}
