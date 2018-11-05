package swingy.tools;

public enum SwGameAction {

    PLAY ("play"),
    QUIT ("quit"),
    BACK ("back"),
    INTERRUPT ("interrupt"),
    REPLAY ("looseContinue"),
    NO_REPLAY ("looseQuit"),
    CHANGE_DISPLAY ("changeDisplay"),
    HERO_RUN ("run"),
    HERO_FIGHT ("fight"),
    PICK_ARTIFACT ("pick"),
    LEAVE_ARTIFACT ("leave"),
    DISPLAY_SELECT ("displaySelect"),
    DISPLAY_CREATE ("displayCreate"),
    VALID_CREATE ("validCreate");

    /*
     * Private attributes
     */
    String action;

    /*
     * Constructor
     */
    SwGameAction(String action) {
        this.action = action;
    }

    /*
     * Public methods
     */

    @Override
    public String toString() {
        return (this.action);
    }

    public boolean equals(SwGameAction action) {
        return (action.action.equals(this.action));
    }

    public boolean equals(String strAction) {
        return this.action.equals(strAction);
    }
}
