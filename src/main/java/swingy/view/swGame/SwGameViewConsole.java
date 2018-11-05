package swingy.view.swGame;

import lombok.Getter;
import swingy.model.artifact.Artifact;
import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.model.map.SwMap;
import swingy.tools.SwGameAction;
import swingy.tools.SwMove;
import swingy.view.fight.SwFightViewConsole;
import swingy.view.hero.HeroViewConsole;
import swingy.view.map.SwMapViewConsole;
import swingy.view.swView.SwViewObserver;
import swingy.view.swView.console.SwConsoleCmd;
import swingy.view.swView.console.SwConsoleInput;
import swingy.view.swView.console.SwConsoleTools;
import swingy.view.swView.console.SwViewConsole;

public class SwGameViewConsole extends SwViewConsole implements SwGameView
{
    private SwMapViewConsole    mapView;
    private HeroViewConsole     heroView;

    @Getter
    private SwFightViewConsole  fightView;

    public SwGameViewConsole()
    {
        this.mapView = new SwMapViewConsole();
        this.heroView = new HeroViewConsole();
        this.fightView = new SwFightViewConsole();
    }

    /*
     * Public Method
     */
    public void setObservers(SwViewObserver observers[])
    {
        this.observers = observers;
        this.fightView.setObservers(this.observers);
    }

    public void displayGame(Hero hero, SwMap map) {
        this.displayMap(hero, map);
        this.displayHero(hero);
    }

    public void displayHero(Hero hero) {
        this.heroView.displayHero(hero);
    }

    public void displayMap(Hero hero, SwMap map)
    {
        SwCharacter[] onMap = map.getCharacters();
        SwCharacter[] enemyAndHero = new SwCharacter[onMap.length + 1];

        enemyAndHero[0] = hero;
        System.arraycopy(onMap, 0, enemyAndHero, 1, onMap.length);
        this.mapView.displayMap(map.getSize(), enemyAndHero);
    }

    public void quitGameView() {}

    public void displayHeroMove(Hero hero)
    {
        SwConsoleCmd[] commands = {
                new SwConsoleCmd("W", SwMove.NORTH, SwMove.NORTH.toString()),
                new SwConsoleCmd("A", SwMove.WEST, SwMove.WEST.toString()),
                new SwConsoleCmd("D", SwMove.EAST, SwMove.EAST.toString()),
                new SwConsoleCmd("S", SwMove.SOUTH, SwMove.SOUTH.toString()),
                new SwConsoleCmd("B", SwGameAction.INTERRUPT, "Back to the menu")
        };

        String action = SwConsoleInput._getUserInput(commands, "Where do you want to go ?");
        triggerActionPerformed(action);
    }

    public void displayStartConfrontation(Hero hero, SwCharacter[] characters)
    {
        this.fightView.displayStartConfrontation(hero, characters);
    }

    public void displayHeroRun(Hero hero) {
        SwConsoleCmd[] commands = {
                new SwConsoleCmd("1", SwGameAction.HERO_RUN, "Try to run !"),
                new SwConsoleCmd("2", SwGameAction.HERO_FIGHT, "Fight !")
        };

        String action = SwConsoleInput._getUserInput(commands, "What do you want to do ?");
        triggerActionPerformed(action);
    }

    public void displayFightOrRun(boolean tryRun, boolean tryRunSuccess) {
        String text;

        if (tryRun)
        {
            if (tryRunSuccess)
                text = "You go away...";
            else
                text = "Arg! You are trapped, you must fight !";
            SwViewConsole.basicPrint(SwConsoleTools.ANSI_CYAN + text + SwConsoleTools.ANSI_RESET);
        }
        else {
            this.fightView.displayFight();
        }
    }

    public void displayChangeLevel(int mapNumber, int mapLevel) {
        this.success("Map finish ! New map (" + mapNumber + ") level " + mapLevel);
    }

    public void displayArtifact(Artifact artifact) {
        SwConsoleCmd[] commands = {
                new SwConsoleCmd("1", SwGameAction.PICK_ARTIFACT, "Pick the artifact"),
                new SwConsoleCmd("2", SwGameAction.LEAVE_ARTIFACT, "Leave artifact and continue")
        };

        SwViewConsole.basicPrint(SwConsoleTools.ANSI_CYAN + "A new object have been found !" + SwConsoleTools.ANSI_RESET);
        SwViewConsole.basicPrint(Artifact.resumeArtifact(artifact));

        String action = SwConsoleInput._getUserInput(commands, "Pick the artifact or continue");
        triggerActionPerformed(action);
    }
}
