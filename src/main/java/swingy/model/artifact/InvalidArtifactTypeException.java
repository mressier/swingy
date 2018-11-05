package swingy.model.artifact;

public class InvalidArtifactTypeException extends Exception {
    InvalidArtifactTypeException(String type) {
        super("Hero type \"" + type + "\" doesn't exist.");
    }
}
