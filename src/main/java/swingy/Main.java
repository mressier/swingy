package swingy;

import org.apache.log4j.BasicConfigurator;
import swingy.tools.SwLog;
import swingy.view.swView.InvalidDisplayModeException;

import java.util.logging.Level;

public class Main {

    private static void usage()
    {
        System.err.println("Usage : java -jar swingy.jar [console|gui]");
    }

    public static void main(String args[])
    {
        SwingyGame game;

        // some initialisations
        BasicConfigurator.configure();
        SwLog.log.setLevel(Level.OFF);

        // Start
        if (args.length != 1) {
            System.err.println("Error : Invalid number of arguments.");
            usage();
            System.exit(1);
        }

        try
        {
            game = new SwingyGame("save.txt", args[0]);
            game.start();
        }
        catch (InvalidDisplayModeException e)
        {
            System.err.println("Error : parameters : " + e.getMessage());
            usage();
            System.exit(1);
        }
        catch (Exception e)
        {
            System.err.println("Error : " + e.getMessage());
        }
    }

}
