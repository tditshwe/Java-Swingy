package main.java.swingy.view;

import main.java.swingy.controller.Controller;
import main.java.swingy.model.Hero;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.JOptionPane;

import java.sql.SQLException;

public class NewHeroDialog extends JDialog
{
	private JLabel nameLabel, classLabel;
    private JTextField nameTextField;
    private JComboBox classComboBox;
    private JButton createButton, cancelButton;
    private Box mainBox, buttonBox;
    private NewHeroDialog instance;

    private Hero hero;

	public NewHeroDialog(MainFrame frame)
	{
		super(frame, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        instance = this;
        nameLabel = new JLabel("Name:");
        classLabel = new JLabel("Class:");
        createButton = new JButton("Create Hero");
        cancelButton = new JButton("Cancel");
        nameTextField = new JTextField();
        classComboBox = new JComboBox();
        mainBox = Box.createVerticalBox();
        buttonBox = Box.createHorizontalBox();

        classComboBox.addItem("<Select class>");
        classComboBox.addItem("Wizard");
        classComboBox.addItem("Worrior");
        classComboBox.addItem("Alchemist");
                
		mainBox.add(nameLabel);
        mainBox.add(nameTextField);
        mainBox.add(classLabel);
        mainBox.add(classComboBox);
        buttonBox.add(createButton);
        buttonBox.add(cancelButton);
        mainBox.add(buttonBox);
        
        cancelButton.addActionListener(actionListener());
        createButton.addActionListener(actionListener());
        
        getContentPane().add(mainBox, BorderLayout.CENTER);
	}
        
    private ActionListener actionListener()
    {
        return new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == cancelButton)
                    dispose();
                else if (e.getSource() == createButton)
                {
                    if (nameTextField.getText().equals(""))
                        JOptionPane.showMessageDialog(instance, "The hero must have a name");
                    else if (classComboBox.getSelectedItem().equals("<Select class>"))
                        JOptionPane.showMessageDialog(instance, "Please select hero class");
                    else
                    {
                        Hero newHero = new Hero(nameTextField.getText());
                        newHero.setType(classComboBox.getSelectedItem().toString());
                        newHero.setLevel(0);
                        newHero.setXp(0);
                        Controller ctr = new Controller(newHero);

                        try
                        {
                            ctr.createHero();
                            hero = newHero;
                        }
                        catch (SQLException ex)
                        {
                            System.out.println(ex.getMessage());
                        }

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
