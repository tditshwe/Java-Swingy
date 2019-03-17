package main.java.swingy.view;

import main.java.swingy.DBManager;
import main.java.swingy.model.Hero;
import main.java.swingy.controller.Controller;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.sql.SQLException;

public class SelectHeroDialog extends JDialog
{
	private JButton createButton, selectButton, cancelButton;
    private JList heroList;
    private DefaultListModel heroListModel;
    private ArrayList<Hero> heroes;

    private MainFrame instance;
    private Hero hero;
    private JScrollPane scrollPane;
    private SelectHeroDialog thisDialog;

	public SelectHeroDialog(MainFrame frame)
	{
		super(frame, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                
                thisDialog = this;
        Controller controller = new Controller(hero);
                
        instance = frame;
		createButton = new JButton("Create hero");
		selectButton = new JButton("Select hero");
		cancelButton = new JButton("Cancel");
        heroListModel = new DefaultListModel();
        heroList = new JList(heroListModel);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(heroList);
        
        try
        {
            heroes = controller.getHeroes();
            
            for (Hero H: heroes)
                heroListModel.addElement(H.getName());
        }
        catch (SQLException e)
        {
            System.out.println("Could not retrieve heroes");
        }
        
         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectButton, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        
        cancelButton.addActionListener(actionListener());
        createButton.addActionListener(actionListener());
        selectButton.addActionListener(actionListener());

        pack();
	}

	private ActionListener actionListener()
    {
    	return new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			if (e.getSource() == cancelButton)
    				dispose();
                if (e.getSource() == createButton)
                {
                    dispose();
                    NewHeroDialog create = new NewHeroDialog(instance);
                    create.pack();
                    create.setLocationRelativeTo(null);
                    create.setVisible(true);
                    hero = create.getHero();
                }
                else if (e.getSource() == selectButton)
                {
                    if (heroList.getSelectedValue() == null)
                        JOptionPane.showMessageDialog(thisDialog, "Please pick a hero");
                    else
                    {
                        hero = heroes.get(heroList.getSelectedIndex());
                        dispose();
                    }
                }
    		}
    	};
    }

    public Hero getHero()
    {
        return hero;
    }
}
