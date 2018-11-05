package swingy.view.swView.console;

import lombok.Getter;
import swingy.tools.SwGameAction;

public class SwConsoleCmd {
    @Getter private String cmdValue;
    @Getter private String retValue;
    @Getter private String description;

    /*
     * Constructor
     */
    public SwConsoleCmd(String cmdValue, String retValue, String description) {
        this.cmdValue = cmdValue;
        this.retValue = retValue;
        this.description = description;
    }

    public SwConsoleCmd(String cmdValue, Object retValue, String description) {
        this(cmdValue, retValue.toString(), description);
    }

    /*
     * Public Methods
     */
    public static SwConsoleCmd[] addBackCmd(SwConsoleCmd[] commands, int index)
    {
        commands[index] = new SwConsoleCmd("B", SwGameAction.BACK, "Back to the main menu");
        return commands;
    }

    /*
     * Package Method
     */
    static void print(SwConsoleCmd[] commands) {
        for (SwConsoleCmd command : commands) {
            SwViewConsole.basicPrint("(" + command.cmdValue + ") " + command.description);
        }
    }
}
