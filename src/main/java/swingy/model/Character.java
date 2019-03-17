package main.java.swingy.model;

import java.util.ArrayList;

public abstract class Character
{

    protected String name;
    
	protected int attack;
	protected int defense;
	protected int hitPoints;
	protected int xPosition;
	protected int yPosition;
    protected ArrayList<String> log;


	public int getXPos()
    {
        return xPosition;
    }
        
    public String getName()
    {
        return name;
    }

    public int getYPos()
    {
        return yPosition;
    }

    public int getAttack()
    {
        return attack;
    }

    public int getDefense()
    {
        return defense;
    }

    public int getHitPoints()
    {
        return hitPoints;
    }

    public ArrayList<String> getLog()
    {
        return log;
    }

    public void setAttack(int attack)
    {
        this.attack = attack;
    }

    public void setDefense(int defense)
    {
        this.defense = defense;
    }

    public void setHitPoints(int hp)
    {
        if (hp < 0)
            hitPoints = 0;
        else
            hitPoints = hp;
    }

    public void setPos(int xPos, int yPos)
    {
        xPosition = xPos;
        yPosition = yPos;
    }

	public abstract void attack(Character opp);
}
