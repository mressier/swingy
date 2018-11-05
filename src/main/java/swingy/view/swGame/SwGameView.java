package swingy.view.swGame;

import swingy.model.artifact.Artifact;
import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.model.map.SwMap;
import swingy.view.fight.SwFightView;
import swingy.view.swView.SwViewObserver;

public interface SwGameView
{
    void         setObservers(SwViewObserver observers[]);
    void         success(String message);
    void         error(String message);

    SwFightView  getFightView();

    void         displayGame(Hero hero, SwMap map);
    void         quitGameView();

    void         displayHero(Hero hero);
    void         displayMap(Hero hero, SwMap map);
    void         displayHeroMove(Hero hero);
    void         displayStartConfrontation(Hero hero, SwCharacter[] characters);
    void         displayHeroRun(Hero hero);
    void         displayFightOrRun(boolean tryRun, boolean tryRunSuccess);
    void         displayArtifact(Artifact artifact);

    void         displayChangeLevel(int mapNumber, int mapLevel);
}
