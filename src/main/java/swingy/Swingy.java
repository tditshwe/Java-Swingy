package main.java.swingy;

import main.java.swingy.controller.ConsoleController;
import main.java.swingy.view.View;
import main.java.swingy.view.MainFrame;
import java.sql.SQLException;

public class Swingy {
    public static void main(String[] args) {
        DBConnection dbConnection = null;
        boolean isCli = false;
        
        try
        {
            if (args.length == 1)
            {
                dbConnection = DBConnection.getConnection();
                
                dbConnection.initConnection();
                DBManager db = new DBManager();
                db.setup();

                System.out.println("Initiated");
                
                if (args[0].equals("gui"))
                {
                    MainFrame frame = new MainFrame();

                    frame.pack();
                    frame.setSize(1024, 735);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
                else if (args[0].equals("console"))
                {
                    View view = new View();
                    ConsoleController ctr = new ConsoleController(null, view);
                    int option;
                    isCli = true;

                    ctr.printHeroOptions();
                    option = ctr.heroOption();
                    ctr.gameHero(option);
                    ctr.initBoard();
                    ctr.drawBoard();
                    ctr.navigateHero();
                }
                else
                {
                    System.out.println("Usage: java -jar swingy.jar [console | gui]");
                }
            }
            else
            {
                System.out.println("Usage: java -jar swingy.jar [console | gui]");
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                if (dbConnection != null && isCli)
                    dbConnection.closeConnection();
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }    
}
