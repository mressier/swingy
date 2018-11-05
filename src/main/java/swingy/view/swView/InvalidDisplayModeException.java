package swingy.view.swView;

public class InvalidDisplayModeException extends Exception {

    public InvalidDisplayModeException(String mode) {
        super("Display mode \"" + mode + "\" is invalid");
    }
}
