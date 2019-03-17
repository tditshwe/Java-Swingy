package main.java.swingy;

import main.java.swingy.model.Enemy;
import main.java.swingy.model.Hero;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class GameConsole
{
	private Scanner sc;
    private Hero hero;

	public GameConsole()
	{
		sc = new Scanner(System.in);
	}

	public int getOption()
	{
		return takeInt(2);
	}

	public Hero createHero()
	{
		String choice;

		System.out.print("Enter hero name: ");
		hero = new Hero(sc.next());
		System.out.println("Choose hero type");
		System.out.println("1. Wizard");
		System.out.println("2. Worrior");
		System.out.println("3. Alchemist");
		System.out.print("Your choice: ");

		switch (takeInt(3))
		{
			case 1:
				choice = "Wizard";
			break;
			case 2:
				choice = "Worrior";
			break;
			default: 
				choice = "Alchemist";
			break;
		}

		hero.setType(choice);
                
        return hero;
	}
        
    public Hero selectHero(ArrayList<Hero> heroes)
    {
        int i;

        for (i = 0; i < heroes.size(); i++)
        {
            System.out.println((i + 1) + ". " + heroes.get(i).getName());
        }
        
        System.out.print("Your choice: ");        
        hero =  heroes.get(takeInt(i) - 1);
        System.out.println("Hero selected.");
        return hero;
    }

    public String direction()
    {
    	System.out.println("Choose a direction");
    	System.out.println("w: North");
    	System.out.println("d: East");
    	System.out.println("s: South");
    	System.out.println("a: West");
    	System.out.print("Your direction: ");

    	return takeDir();
    }

    public void enemyEncounter(Board board, Enemy e)
    {
    	if (e != null)
        {
        	System.out.println("Enemy encountered, fight or run?");
        	System.out.println("1. Fight");
        	System.out.println("2. Run");
        	System.out.print("Your response: ");

        	switch (takeInt(2))
        	{
        		case 1: hero.fight(board, e);
        		break;
        		case 2: hero.run(board, e);
        		break;
        	}
        }
    }
    
    private int takeInt(int cap)
    {
        try
        {
            int choice = sc.nextInt();
            
            if (choice < 1 || choice > cap)
            {
                System.out.print("Invalid choice, try again: ");
                return takeInt(cap);
            }
            else
                return choice;
        }
        catch (InputMismatchException e)
        {
            System.out.print("Invalid choice, try again: ");
            sc.nextLine();
            return takeInt(cap);
        }
    }

    private String takeDir()
    {
        String dir = sc.next();

        switch (dir)
        {
            case "w": case "d": case "s": case "a": case "q":
            return dir;
            default:
                System.out.print("Invalid direction, choose again: ");
                return takeDir();
        }
    }
}
