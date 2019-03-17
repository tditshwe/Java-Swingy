package main.java.swingy.controller;

import main.java.swingy.view.View;
import main.java.swingy.GameConsole;
import main.java.swingy.Board;
import main.java.swingy.model.Enemy;
import main.java.swingy.model.Hero;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsoleController extends Controller
{
    private View view;
    private GameConsole cli;
    private Board board;
      
    public ConsoleController(Hero hero, View view)
    {
        super(hero);
        
        this.view = view;
    }
    
    public void printHeroStats()
    {
        view.printHeroStats(hero);
    }
    
    public void printHeroOptions()
    {
        view.printHeroOptions();
    }
    
    public Hero gameHero(int option) throws SQLException
    {     
        if (option == 1)
        {       
           view.println("Creating hero ...");
           Hero newHero = cli.createHero();
           view.println("Hero created.");
           hero = newHero;
           createHero();

           return hero;
        }
        else if (option == 2)
        {
            view.println("Selecting hero ...");
            ArrayList<Hero> heroes = db.getHeroes();
            hero = cli.selectHero(heroes);

            return hero;
        }

        return null;
    }
    
    public int heroOption()
    {
        cli = new GameConsole();
        return cli.getOption();
    }

    public void initBoard()
    {
        int size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
        board = new Board(size);
        board.placeHero(hero,  board.getSize() / 2);
        hero.setMaxHp(hero.getHitPoints());
        board.spreadVilians();
        view.printHeroStats(hero);
    }

    public void drawBoard()
    {
        board.printBoard();
    }

    public void navigateHero() throws SQLException
    {
        String dir = cli.direction();

        while (!dir.equals("q") && hero.getHitPoints() > 0 && !heroAtBorder())
        {
            switch (dir)
            {
                case "w":
                    board.changeHeroPos(hero.getXPos(), hero.getYPos() - 1, false);
                break;
                case "d":
                    board.changeHeroPos(hero.getXPos() + 1, hero.getYPos(), false);
                break;
                case "s":
                    board.changeHeroPos(hero.getXPos(), hero.getYPos() + 1, false);
                break;
                case "a":
                    board.changeHeroPos(hero.getXPos() - 1, hero.getYPos(), false);
                break;
            }

            Enemy enemy = board.getEnemy(hero.getXPos(), hero.getYPos());

            cli.enemyEncounter(board, enemy);
            updateLog();
            view.printHeroStats(hero);
            view.println("---------------");
            drawBoard();
            view.println("---------------");

            if (hero.getHitPoints() > 0 )
            {
                if (heroAtBorder())
                    view.println("You have reached the border, you win.");
                else
                    dir = cli.direction();
            }
            else
                view.println("Our hero has been killed.");                         
        }

        if (dir.equals("q"))
            view.println("Exiting game ...");
        updateHero();
    }

    public void updateLog()
    {
        ArrayList<String> log;      
        log = hero.getLog();

        for (String L: log)
        {
            view.print(L);
        }

        log.clear();
    }
    
    private boolean heroAtBorder()
    {
        if (hero.getXPos() == board.getSize() - 1 || hero.getXPos() == 0  ||
            hero.getYPos() == board.getSize() - 1 || hero.getYPos() == 0)
        {
            return true;          
        }

        return false;
    }
}
