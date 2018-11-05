package swingy.model.map;

import swingy.tools.SwRandom;
import swingy.model.character.SwCharacter;
import swingy.model.character.vilain.VilainFactory;
import swingy.tools.SwCoord;

public abstract class SwMapFactory
{
    /*
     * Public Methods
     */
    static public SwMap newMap(int heroLevel)
    {
        int             mapSize = (heroLevel - 1) * 5 + 10 - (heroLevel % 2);
        int             nbCharacters = mapSize * heroLevel;
        SwCharacter[]   characters = new SwCharacter[nbCharacters];

        for (int i = 0; i < nbCharacters; i++) {
            characters[i] = VilainFactory.newVilain(heroLevel);
            characters[i].setPosition(_generateRandomPosition(mapSize));
        }
        return SwMapFactory.newMap(mapSize, characters);
    }

    /*
     * Private Methods
     */
    static private SwMap newMap(int size, SwCharacter[] characters) {
        return new SwMap(size, characters);
    }

    static private SwCoord _generateRandomPosition(int mapSize)
    {
        int     x = SwRandom.rand(0, mapSize - 1);
        int     y = SwRandom.rand(0, mapSize - 1);

        if (x == mapSize / 2 && y == mapSize / 2)
            x--;
        return new SwCoord(x, y);
    }
}
