package main.java.swingy.model;

import java.util.ArrayList;
import java.util.Random;

import main.java.swingy.Board;

public class Hero extends Character
{
	private String type;
	private int level;

    int experience, prevX, prevY, maxHp;
    private boolean hasWon;

	public Hero(String name)
	{
        log = new ArrayList<String>();
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public int getLevel()
	{
		return level;
	}

	public int getXp()
	{
		return experience;
	}
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public void setAttack(int attack)
    {
        this.attack = attack;
    }
    
    public void setDefense(int defense)
    {
        this.defense = defense;
    }

    public void setXp(int xp)
    {
    	experience = xp;
    }

    public void setMaxHp(int hp)
    {
        maxHp = hp;
    }
    
    public void setPrevPos(int x, int y)
    {
        prevX = x;
        prevY = y;
    }
    
    public int getPrevX()
    {
        return prevX;
    }
    
    public int getPrevY()
    {
        return prevY;
    }

    public boolean hasWon()
    {
        return hasWon;
    }

    public int getMaxHp()
    {
        return maxHp;
    }
    
    public void fight(Board board, Enemy enemy)
    {
        enemy.setLog(log);       
        log.add("---Battle begins---\n");
        log.add("Hero is fighting " + enemy.getName() + " villian on pos: ");
        log.add(enemy.getXPos() + "," + enemy.getYPos() + "\n");
        
        while (hitPoints > 0 && enemy.getHitPoints() > 0)
        {
            attack(enemy);
            log.add("Enemy hp: " + enemy.getHitPoints() + "\n");
            
            if (enemy.getHitPoints() > 0)
            {
                enemy.attack(this);
                log.add("Hero hp: " + hitPoints + "\n");
            }
            else
                break;
        }
        
        if (hitPoints > 0)
        {
            log.add("You win. ");
            log.add(enemy.getName() + " dies\n");
            board.killEnemy(enemy);
            gainXp(enemy);
            levelUp();
            hasWon = true;
        }
        else
        {
            board.markBattle(xPosition, yPosition);
            log.add("You are a loser.\n");
            hasWon = false;
        }
        
        log.add("---End of battle---\n");
    }
    
    public void run(Board board, Enemy enemy)
    {
        Random rnd = new Random();
        int odds = rnd.nextInt(2);
        
        if (odds == 1)
        {
            hasWon = true;
            board.changeHeroPos(prevX, prevY, true);
            log.add("You chose to run from battle.\n");
        }
        else
            fight(board, enemy);
    }
    
    private void gainXp(Enemy e)
    {
        int xpGain;
        
        switch (e.getName())
        {
            case "Ghost":
                xpGain = 500;
            break;
            case "Demon":
                xpGain = 450;
            break;
            default:
                xpGain = 600;
            break;
        }
        
        experience += (double)level / 2 + xpGain;
    }

    private void levelUp()
    {
        int lev = level + 1;

        if (experience >= (lev * 1000 + Math.pow(lev - 1, 2) * 450))
        {
            level = lev;
            hitPoints += level * 20;
            maxHp += level * 20;
            attack += 4 * level;
            defense += 1;
            log.add("You have leveled up\n");
        }
    }
    
    @Override
    public void attack(Character opp)
    {
        int bonus;
        Random rnd = new Random();
        int randInt = rnd.nextInt(4);

        if (randInt == 3)
            bonus = attack;
        else
            bonus = 0;

        int hpLoss = attack + bonus - opp.getDefense();
        opp.setHitPoints(opp.getHitPoints() - hpLoss);
        
        log.add(name + "(" + hitPoints + ") attacks enemy ");
        log.add("taking " + hpLoss + " of enemy HP\n");
        
        if (bonus > 0)
            log.add("OMG Double damage!!!\n");
    }
}
