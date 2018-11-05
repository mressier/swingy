package swingy.view.map;

import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.model.character.vilain.Vilain;
import swingy.tools.SwCoord;
import swingy.model.map.SwMap;
import swingy.view.swView.console.SwViewConsole;

import java.util.ArrayList;

public class SwMapViewConsole extends SwViewConsole {

    /*
     * Public Methods
     */
    public void displayMap(int size, SwCharacter[] characters)
    {
        StringBuilder firstLine = new StringBuilder(size + 2); // add two char
        firstLine.append("+");
        for (int i = 0; i < size; i++) {
            firstLine.append("-");
        }
        firstLine.append("+");
        SwViewConsole.basicPrint(firstLine.toString());

        StringBuffer line;
        SwCoord pos = new SwCoord(0, 0);
        while (pos.getY() < size)
        {
            line = new StringBuffer(size + 2); // add two extra char

            pos.setX(0);
            line.append("|");
            while (pos.getX() < size)
            {
                line = _positionDisplay(line, characters, pos);
                pos.setX(pos.getX() + 1);
            }
            line.append("|");
            SwViewConsole.basicPrint(line.toString());
            pos.setY(pos.getY() + 1);
        }
        SwViewConsole.basicPrint(firstLine.toString());
    }

    private StringBuffer    _positionDisplay(StringBuffer line, SwCharacter[] allCharacters, SwCoord position)
    {
        ArrayList<SwCharacter> charactersOnPosition = SwMap.getCharactersOnPosition(allCharacters, position);

        if (charactersOnPosition.size() == 0) {
            line.append(" ");
            return line;
        }

        SwCharacter character = charactersOnPosition.get(0);
        if (character instanceof Vilain)
        {
            if (((Vilain)character).isVisible())
            {
                if (character.getLife() > 0)
                    line.append("O");
                else
                    line.append("X");
            }
            else
                line.append(" ");
        }
        else if (character instanceof Hero)
            line.append("H");
        else
            line.append(" ");

        return (line);
    }
}
