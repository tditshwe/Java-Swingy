package main.java.swingy.view;

import main.java.swingy.controller.Controller;
import main.java.swingy.DBConnection;
import main.java.swingy.model.Hero;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

//import java.util.Set;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import javax.validation.ConstraintViolation;

public class MainFrame extends JFrame
{
	private JButton startButton;
    private JTextArea statsTextArea, logTextArea;
    private JLabel statsLabel, logLabel;
    private Box leftBox;
    private BoardPanel boardPanel;
    private JScrollPane scrollPane;

    private MainFrame instance;
    private Hero hero;
    private Controller ctr;

    public MainFrame()
    {
    	super("Swingy");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	instance = this;
    	statsTextArea = new JTextArea();
    	logTextArea = new JTextArea();
    	statsLabel = new JLabel("Hero stats:");
    	logLabel = new JLabel("Game log:");
    	leftBox = Box.createVerticalBox();
    	startButton = new JButton("Start game");
        boardPanel = new BoardPanel(this);

    	statsTextArea.setPreferredSize(new Dimension(270, 150));
    	statsTextArea.setEditable(false);
    	logTextArea.setEditable(false);
        logTextArea.setRows(28);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(logTextArea);

    	leftBox.add(statsLabel);
    	leftBox.add(statsTextArea);
    	leftBox.add(Box.createVerticalStrut(10));
    	leftBox.add(logLabel);
    	leftBox.add(scrollPane);

    	startButton.addActionListener(startButtonListener());

    	getContentPane().add(leftBox, BorderLayout.WEST);
    	getContentPane().add(startButton, BorderLayout.SOUTH);
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        addWindowListener(windowAdapter());
    }

    private ActionListener startButtonListener()
    {
    	return new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			SelectHeroDialog select = new SelectHeroDialog(instance);
                select.pack();
                select.setLocationRelativeTo(null);
                select.setVisible(true);
                hero = select.getHero();
                //Validator validator = Validation.buildDefaultValidatorFactory()
                    //.getValidator();

                if (hero != null)
                {
                    //Set<ConstraintViolation<Hero>> constraintViolations = validator.validate(hero);
                    ctr = new Controller(hero);
                    ctr.updateHeroStats(statsTextArea);
                    boardPanel.setHero(hero);
                    boardPanel.createBoard();
                    boardPanel.setGameStarted(true);
                    logTextArea.setText("");
                    boardPanel.placeHero();                 
                    boardPanel.requestFocus();
                    startButton.setEnabled(false);
                    hero.setMaxHp(hero.getHitPoints());
                }
    		}
    	};
    }

    private WindowAdapter windowAdapter() {
        return new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                DBConnection dbConnection = DBConnection.getConnection();

                try
                {
                    if (ctr != null)
                        ctr.updateHero();
                    dbConnection.closeConnection();
                }
                catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        };
    }

    public void updateStats()
    {
        ctr.updateHeroStats(statsTextArea);
    }

    public void enableStartButton()
    {
        startButton.setEnabled(true);
    }

    public void updateLog(String s)
    {
        logTextArea.append(s);
    }
}
