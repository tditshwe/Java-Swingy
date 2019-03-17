package main.java.swingy.model;

import java.util.ArrayList;

public class Enemy extends Character
{    
    public Enemy(String name)
    {
        this.name = name;
    }
    
    public void setLog(ArrayList<String> log)
    {
        this.log = log;
    }
    
    @Override
	public void attack(Character opp)
    {
        int hpLoss = attack - opp.getDefense();
        opp.setHitPoints(opp.getHitPoints() - hpLoss);
        log.add(name + "(" + hitPoints + ") attacks hero ");
        log.add("taking " + hpLoss + " of enemy HP\n");
    }
}