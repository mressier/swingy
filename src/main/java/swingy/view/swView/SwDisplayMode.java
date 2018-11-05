package swingy.view.swView;

public enum SwDisplayMode {

    GUI ("gui"),
    CONSOLE ("console");

    /*
     * Constructor
     */
    private String name;

    SwDisplayMode(String name) {
        this.name = name;
    }

    /*
     * Public Methods
     */
    public static Boolean isGUIMode(String mode) {
        if (mode.compareToIgnoreCase("gui") == 0)
            return (true);
        return (false);
    }
    public Boolean isGUIMode() {
        return (isGUIMode(this.toString()));
    }

    public static Boolean isConsoleMode(String mode) {
        if (mode.compareToIgnoreCase("console") == 0)
            return (true);
        return (false);
    }
    public Boolean isConsoleMode() {
        return (isConsoleMode(this.toString()));
    }

    @Override
    public String toString() {
        return this.name;
    }
}
