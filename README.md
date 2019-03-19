# Java-Swingy

## Overview

This project is a second project in a series of Java projects as part of the [Wethinkcode](https://www.wethinkcode.co.za/) curriculum. It is a minimalistic text-based RPG game that can be launched in Console or GUI mode.

### Gameplay

A player can pick from multiple heroes of different types (Wizard, Warrior and Alchemist) with different starting stats among them:

- Wizard: attack = 13, defense = 7, hitPoints = 80
- Warrior: attack = 15, defense = 3, hitPoints = 110
- Alchemist: attack = 25, defense = 2, hitPoints = 90

When the player starts the game in console mode, there are 2 options:

- Create a hero
- Select a previously created hero.

In gui mode, a 'Start game' button needs to be clicked to launch a pop up dialog with hero creation and selection features.

After choosing a hero the actual game begins. The hero needs to navigate a square map with the size calculated by the formula (level - 1) * 5 + 10 - (level % 2). For example a hero of level 7 will be placed on a 39X39 map. The initial position of the hero is in the center of the map. He wins the game if he reaches one of the borders of the map. Each turn he can move one position in one of the 4 four directions:

- North
- East
- South
- West

When the map is generated, villains of varying power will be spread randomly over the smap:

- [Demon](characters/villian_1.png): attack = 15, defense = 3, hitPoints = 50, letter on the map `V`
- [Ghost](characters/ghost.png): attack = 25, defense = 5, hitPoints = 100, letter on the map `G`
- [Superbat](characters/superbat.png): attack = 40, defense = 10, hitPoints = 150, letter on the map `S`

It is always a good idea to target Demons first when the hero is still on low level as they are the weakest. When the hero moves to a position occupied by a villain, the hero has 2 options:

- Fight, which engages him in a battle with the villain
- Run, which gives him a 50% chance of returning to the previous position. If the odds aren’t on his side, he must fight the villain.

The battle between the hero and the villian is simulated and the outcome of the battle is presented to the player. If the hero looses a battle, he dies and looses the mission. If the battle is won, the hero gains experience points, based on the villain power and levels up if he reaches the next level experience. The experience gain is calculated by the formula: Level / 2 + xpGain. The xpGain is based on the villian as follows:

- Demon: 450
- Ghost: 500
- Superbat: 600

Leveling up is based on the following formula level * 1000 + Pow(level - 1, 2) * 450. So the necessary experience to level follows this pattern:

- Level 1 - 1000 XP
- Level 2 - 2450 XP
- Level 3 - 4800 XP
- Level 4 - 8050 XP
- Level 5 - 12200 XP

## Prerequisites

- Java 7 or later
- [Maven](https://maven.apache.org/)
- Mysql Server

## Building the project

Building the project, downloading and installing required dependencies is automated with [Maven](https://maven.apache.org/).The project can be built by running this command in the root of your project folder:

- mvn clean package

This generates a runnable .jar file that can launch the game. If one doesn't want to go through this trouble they can use Maven bundled with their favourite Java supported IDE.

## Launching the Game

Before launching the game, it is assumed that you have Mysql Server configured and running. The database is automatically created if it doesn't already exist when the game is launched. The connection parameters corresponding to your database configuration can be edited in `config.json`.

The game can be launched in 2 modes:

- java -jar target/swingy.jar console

- java -jar target/swingy.jar gui
