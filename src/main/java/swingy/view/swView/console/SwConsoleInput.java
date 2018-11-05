package swingy.view.swView.console;

import swingy.tools.SwLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class SwConsoleInput {

    private static final BufferedReader     consoleIn = new BufferedReader(new InputStreamReader(System.in));

    /*
     * Public method
     */
    public static String        readLineFromStdin() {
        return SwConsoleInput.readLine(SwConsoleInput.consoleIn);
    }

    public static String        _getUserInput(SwConsoleCmd commands[], String title)
    {
        String input;
        String command;

        while (true)
        {
            SwViewConsole.basicPrint(title);
            SwConsoleCmd.print(commands);
            input = readLineFromStdin();
            try {
                command = _getUserCommand(input, commands);
                break ;
            }
            catch (InvalidCommandException e) {
                SwViewConsole.printError(e.getMessage());
            }
            catch (Exception e) {
                SwLog.log.warning("You did a really big poo Mathilde ! : " + e.getMessage());
            }
        }
        return (command);
    }

    /*
     * Commands and Actions related
     */

    private static String      _getUserCommand(String command, SwConsoleCmd[] commands) throws InvalidCommandException
    {
        for (SwConsoleCmd command1 : commands) {
            if (command.compareToIgnoreCase(command1.getCmdValue()) == 0)
                return (command1.getRetValue());
        }
        throw new InvalidCommandException(command);
    }

    private static String        readLine(BufferedReader input)
    {
        try {
            return input.readLine();
        }
        catch (IOException e) {
            System.err.println("Error while reading : " + e.getMessage());
            System.exit(1);
        }
        catch (Exception e) {
            SwLog.log.warning("Ctrl+D catched");
        }
        return "";
    }

}

class InvalidCommandException extends Exception {
    InvalidCommandException(String command) {
        super("command \"" + command +"\" doesn't match anything :( try again");
    }
}
