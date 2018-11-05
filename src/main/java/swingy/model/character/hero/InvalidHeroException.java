package swingy.model.character.hero;

import lombok.Getter;

public class InvalidHeroException extends Exception
{
    @Getter private String property;
    @Getter private String description;

    public InvalidHeroException(String property, String description)
    {
        super("Invalid " + property + " about hero (" + description + ")");
        this.property = property;
        this.description = description;
    }

    public InvalidHeroException(String errorMessage)
    {
        super("Invalid data found when reading backupfile : " + errorMessage);
    }
}
