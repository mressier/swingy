package swingy.view.swView.console;

import swingy.view.swView.SwView;

import java.io.*;

public abstract class SwViewConsole extends SwView {

    private static final PrintStream        consoleOut = System.out;
    private static final PrintStream        consoleErr = System.err;

    /*
     * Public methods
     */
    public void success(String message) {
        SwViewConsole.printSuccess(message);
    }

    public void error(String message) {
        SwViewConsole.printError(message);
    }

    /*
     * Protected Methods
     */
    protected static void printError(String message) {
        SwViewConsole.consoleErr.println(SwConsoleTools.ANSI_RED + "Error : " + message + SwConsoleTools.ANSI_RESET);
    }

    protected static void printWarning(String message) {
        SwViewConsole.consoleErr.println(SwConsoleTools.ANSI_YELLOW + "Warning : " + message + SwConsoleTools.ANSI_RESET);
    }

    protected static void printSuccess(String message) {
        SwViewConsole.consoleErr.println(SwConsoleTools.ANSI_GREEN + "Success : " + message + SwConsoleTools.ANSI_RESET);
    }

    protected static void basicPrint(String message) {
        SwViewConsole.consoleOut.println(message);
    }
}

