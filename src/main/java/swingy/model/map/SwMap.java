package swingy.model.map;

import lombok.Getter;
import swingy.model.character.SwCharacter;
import swingy.tools.SwCoord;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class SwMap
{
    @Min(0)
    @Getter
    private int size;

    @NotEmpty
    @Getter
    private SwCharacter[] characters;

    SwMap(int size, SwCharacter[] characters)
    {
        this.size = size;
        this.characters = characters;
    }

    /*
     * Public Static methods
     */
    public static ArrayList<SwCharacter> getCharactersOnPosition(SwCharacter allCharactersOnMap[], SwCoord position)
    {
        ArrayList<SwCharacter>  charactersOnPosition = new ArrayList<SwCharacter>();

        for (SwCharacter character : allCharactersOnMap)
        {
            if (character.getPosition().equals(position))
                charactersOnPosition.add(character);
        }
        return charactersOnPosition;
    }
}
