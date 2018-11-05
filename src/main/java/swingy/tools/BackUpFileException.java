package swingy.tools;

public class BackUpFileException extends Exception {

    BackUpFileException(String file, String message) {
        super("Error with the backup file (" + file + ") : " + message);
    }
}
