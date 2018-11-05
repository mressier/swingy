package swingy.model.swGame;

import lombok.Getter;
import swingy.model.artifact.Artifact;
import swingy.model.artifact.ArtifactFactory;
import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.model.character.vilain.Vilain;
import swingy.tools.SwCoord;
import swingy.model.map.SwMap;
import swingy.model.map.SwMapFactory;
import swingy.tools.SwMove;

import java.util.ArrayList;

public class SwGame
{
    @Getter private Hero    hero;
    @Getter private SwMap   map;

    /*
     * Constructor
     */
    public SwGame(Hero hero)
    {
        this.hero = hero;
        this.newLevel();
    }

    /*
     * Public Method
     */
    public void             newLevel()
    {
        this.map = SwMapFactory.newMap(hero.getLevel());
        hero.setPosition(new SwCoord(this.map.getSize() / 2, this.map.getSize() / 2));
        hero.heal();
    }

    public void             moveHero(SwMove move)
    {
        SwCoord position = this.hero.getPosition();
        position = SwMove.applyMove(move, position);
        this.hero.setPosition(position);
    }

    public boolean          isConfrontation()
    {
        SwCharacter[]   onPosition = getCharactersOnHeroPos();

        for (SwCharacter character: onPosition) {
            if (character.getLife() > 0)
                return (true);
        }
        return (false);
    }

    public void             setCharactersOnHeroVisible()
    {
        SwCharacter[]   onPosition = getCharactersOnHeroPos();

        for (SwCharacter character: onPosition) {

            if (character instanceof Vilain)
                ((Vilain)character).setVisible(true);
        }
    }

    public int              increaseHeroExperience()
    {
        int         vilainsLevel = _getCharacterOnHeroPosLevel();
        int         earn = vilainsLevel * 450;

        this.hero.increaseExperience(earn);
        return (earn);
    }

    public Artifact         generateArtifact()
    {
        return ArtifactFactory.newArtifact(this.hero.getLevel(), _getCharacterOnHeroPosLevel());
    }

    public SwCharacter[]    getCharactersOnHeroPos()
    {
        ArrayList<SwCharacter> characters = SwMap.getCharactersOnPosition(map.getCharacters(), hero.getPosition());
        SwCharacter[]          charactersArray = new SwCharacter[characters.size()];

        charactersArray = characters.toArray(charactersArray);
        return (charactersArray);
    }

    public boolean          heroIsOutOfMap()
    {
        SwCoord position = hero.getPosition();

        return (position.getX() < 0 || position.getY() < 0
                || position.getX() >= map.getSize() || position.getY() >= map.getSize());
    }

    /*
     * Private Method
     */
    private int             _getCharacterOnHeroPosLevel()
    {
        int vilainsLevel = 0;

        for (SwCharacter character: getCharactersOnHeroPos()) {
            vilainsLevel += character.getLevel();
        }
        return (vilainsLevel);
    }
}
