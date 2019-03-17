package main.java.swingy.view;


import main.java.swingy.Board;
import main.java.swingy.model.Hero;

public class View {
    private Board board;

    public void printHeroStats(Hero hero)
    {
        System.out.println("Name: " + hero.getName());
        System.out.println("Class: " + hero.getType());
        System.out.println("Level: " + hero.getLevel());
        System.out.println("Attack: " + hero.getAttack());
        System.out.println("Defense: " + hero.getDefense());
        System.out.println("Hitpoints: " + hero.getHitPoints());
        System.out.println("XP: " + hero.getXp());
    }

    public void printBoard()
    {
        board.printBoard();
    }

    public void printHeroOptions()
    {
        System.out.println("1. Create hero");
        System.out.println("2. Select hero");
        System.out.print("Your choice: ");
    }
    
    public void println(String str)
    {
        System.out.println(str);
    }

    public void print(String str)
    {
        System.out.print(str);
    }
}
