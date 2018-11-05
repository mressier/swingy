package swingy.model.character.vilain;

public class InvalidVilainTypeException extends Exception {
    InvalidVilainTypeException(String type) {
        super("Vilain type \"" + type + "\" doesn't exist.");
    }
}
