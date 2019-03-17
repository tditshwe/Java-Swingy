package main.java.swingy;

import main.java.swingy.controller.Controller;
import main.java.swingy.model.Enemy;
import main.java.swingy.model.Hero;
import java.util.Random;
import java.util.ArrayList;

public class Board
{
	private int gameBoard[][];
	private int size;
	private Hero hero;
	private ArrayList<Enemy> enemies;

	public Board(int size)
	{
		gameBoard = new int[size][size];
		this.size = size;
	}

	public int[][] initBoard()
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				gameBoard[i][j] = 0;
			}
		}

		return gameBoard;
	}

	public void placeHero(Hero hero, int pos)
	{
        this.hero = hero;
		gameBoard[pos][pos] = 1;
        this.hero.setPos(pos, pos);
	}

	public void spreadVilians()
	{
		enemies = new ArrayList<Enemy>();
		Random rnd = new Random();
		int randInt = -1;
		Enemy enemy;
                String name;
		for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameBoard[i][j] != 1) {
                    randInt = rnd.nextInt(6);

                    if (randInt == 0) {
                        randInt = rnd.nextInt(10);
                        
                        switch (randInt)
                        {
                            case 0:
                                name = "Superbat";
                            break;
                            case 1: case 4:
                                name = "Ghost";
                            break;
                            default:
                                name = "Demon";
                            break;
                        }
                        
                        enemy = new Enemy(name);
                        enemy.setPos(i, j);
                        Controller ctr = new Controller(enemy);
                        ctr.createEnemy();
                    	enemies.add(enemy);
                        gameBoard[i][j] = 2;
                    }
                }
            }
        }
	}

	public void changeHeroPos(int x, int y, boolean rev)
    {
        if (rev)
            gameBoard[hero.getXPos()][hero.getYPos()] = 2;
        else
            gameBoard[hero.getXPos()][hero.getYPos()] = 0;
                
		gameBoard[x][y] = 1;
        hero.setPrevPos(hero.getXPos(), hero.getYPos());
		hero.setPos(x, y);
	}

	public void printBoard()
	{
        char icon;
            
		for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (gameBoard[j][i])
                {
                    case 1:
                        icon = 'H';
                    break;
                    case 2:
                        icon = enemyIcon(j, i);
                    break;
                    case 3:
                        icon = 'X';
                    break;
                    default:
                        icon = '.';
                    break;
                }
                
            	System.out.print(icon);
            }
            System.out.println();
        }
	}

	private char enemyIcon(int x, int y)
	{
		Enemy enemy = getEnemy(x, y);
		char eChar;

		switch(enemy.getName())
		{
			case "Ghost":
				eChar = 'G';
			break;
			case "Superbat":
				eChar = 'S';
			break;
			default:
				eChar = 'V';
			break;
		}

		return eChar;
	}

	public void markBattle(int xPos, int yPos)
	{
		gameBoard[xPos][yPos] = 3;
	}

	public int getSize()
	{
		return size;
	}

	public Enemy getEnemy(int x, int y)
	{
		for (Enemy E: enemies)
		{
			if (E.getXPos() == x && E.getYPos() == y)
				return E;
		}

		return null;
	}
        
        public void killEnemy(Enemy e)
        {
            enemies.remove(e);
        }
}
