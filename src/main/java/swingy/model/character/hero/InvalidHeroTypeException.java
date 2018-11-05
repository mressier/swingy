package swingy.model.character.hero;

public class InvalidHeroTypeException extends Exception {
    InvalidHeroTypeException(String type) {
        super("Hero type \"" + type + "\" doesn't exist.");
    }
}
