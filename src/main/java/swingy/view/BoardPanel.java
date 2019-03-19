package main.java.swingy.view;

import main.java.swingy.controller.Controller;
import main.java.swingy.model.Enemy;
import main.java.swingy.model.Hero;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.sql.SQLException;
import main.java.swingy.Board;

public class BoardPanel extends JPanel
{
    private MainFrame frame;
    private Hero hero;
    private boolean gameStarted;
    private Board board;
    private int[][] gameBoard;
    private Controller ctr;

    public BoardPanel(MainFrame frame)
    {
        this.frame = frame;
        gameStarted = false;

        addKeyListener(heroKeyAdapter());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameStarted)
    	{
    		int size = getHeight();
        	Image img;
        	int sqrSize = size / board.getSize();

	        for (int i = 0; i < board.getSize(); i++)
	        {
	        	for (int j = 0; j < board.getSize(); j++)
	        	{
		        	g.drawRect(i * sqrSize, j * sqrSize, sqrSize, sqrSize);

		        	if (gameBoard[i][j] == 1) {
		        		img = Toolkit.getDefaultToolkit().getImage("characters/hero.png");
	        			g.drawImage(img, i * sqrSize, j * sqrSize, sqrSize, sqrSize, this);
		        	}
		        	else if (gameBoard[i][j] == 2)
		        	{
		        		Enemy enemy = board.getEnemy(i, j);
		        		String imgSrc;

		        		switch(enemy.getName())
		        		{
		        			case "Ghost":
		        				imgSrc = "ghost.png";
		        			break;
		        			case "Superbat":
		        				imgSrc = "superbat.png";
		        			break;
		        			default:
		        				imgSrc = "villian_1.png";
		        			break;
		        		}

		        		img = Toolkit.getDefaultToolkit().getImage("characters/" + imgSrc);
	        			g.drawImage(img, i * sqrSize, j * sqrSize, sqrSize, sqrSize, this);
		        	}
		        	else if (gameBoard[i][j] == 3)
		        	{
		        		img = Toolkit.getDefaultToolkit().getImage("characters/fight.png");
	        			g.drawImage(img, i * sqrSize, j * sqrSize, sqrSize, sqrSize, this);
		        	}
		        }
	        }
		}
    }

    public KeyAdapter heroKeyAdapter()
    {
    	return new KeyAdapter() {
	    	public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
		    
		        if (key == e.VK_DOWN)
		        {
		        	board.changeHeroPos(hero.getXPos(), hero.getYPos() + 1, false);
		        }
		        else if (key == e.VK_UP)
		        {
		        	board.changeHeroPos(hero.getXPos(), hero.getYPos() - 1, false);
		        }
		        else if (key == e.VK_LEFT)
		        {
		        	board.changeHeroPos(hero.getXPos() - 1, hero.getYPos(), false);
		        }
		        else if (key == e.VK_RIGHT)
		        {
		        	board.changeHeroPos(hero.getXPos() + 1, hero.getYPos(), false);
		        }
                        
		        repaint();
		        Enemy enemy = board.getEnemy(hero.getXPos(), hero.getYPos());

		        if (enemy != null)
                {
                    Object stringArray[] = { "Fight", "Run" };

                    int option = JOptionPane.showOptionDialog(frame, "Fight or run?", null, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, stringArray, frame);
                
                    if (option == JOptionPane.YES_OPTION)
                    {
                        hero.fight(board, enemy);
                    }
                    else
                      hero.run(board, enemy);

                  	repaint();
                  	frame.updateStats();
                  	ctr.updateLog(frame);


                  	if (!hero.hasWon())
                  	{
                            JOptionPane.showMessageDialog(frame, "You lose");
                            frame.enableStartButton();
                            setFocusable(false);
                            updateHero();
                            frame.clearLog();
                            frame.updateLog("Click Start game button to begin.");
                  	}
                }

                if (enemy == null || hero.hasWon())
                    checkBorders();
	        }
	    };
    }

    public void setHero(Hero hero)
    {
    	this.hero = hero;
    	ctr = new Controller(hero);
    }

    public void setGameStarted(boolean started)
    {
    	gameStarted = started;
    }

    public void createBoard()
    {
    	int size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
        board = new Board(size);
        gameBoard = board.initBoard();
        setFocusable(true);
    }
    
    public void placeHero()
    {
        board.placeHero(hero, board.getSize() / 2);
        board.spreadVilians();
        repaint();
    }

    private void checkBorders()
    {
    	if (hero.getXPos() == board.getSize() - 1 || hero.getXPos() == 0  ||
            hero.getYPos() == board.getSize() - 1 || hero.getYPos() == 0)
        {
            JOptionPane.showMessageDialog(frame, "You win");
            frame.enableStartButton();
            setFocusable(false);
            updateHero();
            frame.clearLog();
            frame.updateLog("Click Start game button to begin.");
        }
    }

    private void updateHero()
    {
    	try
        {
            ctr.updateHero();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
