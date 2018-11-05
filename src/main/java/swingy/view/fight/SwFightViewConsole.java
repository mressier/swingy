package swingy.view.fight;

import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.tools.SwGameAction;
import swingy.view.swView.SwViewObserver;
import swingy.view.swView.console.SwConsoleCmd;
import swingy.view.swView.console.SwConsoleInput;
import swingy.view.swView.console.SwConsoleTools;
import swingy.view.swView.console.SwViewConsole;

public class SwFightViewConsole extends SwViewConsole implements SwFightView
{
    public void setObservers(SwViewObserver observers[])
    {
        this.observers = observers;
    }

    public void displayFight()
    {
        SwViewConsole.basicPrint(SwConsoleTools.ANSI_CYAN + "Let's fiiiight" + SwConsoleTools.ANSI_RESET);
    }

    public void displayStartConfrontation(Hero hero, SwCharacter[] characters) {
        String text = hero.getName() + " meet something on the map!";
        SwViewConsole.basicPrint(text);

        for (SwCharacter character : characters) {
            text = character.getName() + " (" + character.getType() + " lvl " + character.getLevel() + ") ";
            SwViewConsole.basicPrint(SwConsoleTools.ANSI_CYAN + text + SwConsoleTools.ANSI_RESET);
        }
    }

    public void displayHit(SwCharacter hit, int damageValue) {
        String text = hit.getName() + " hit with <" + damageValue + "> damages";
        SwViewConsole.basicPrint(text);
    }

    public void displayTakeDamage(SwCharacter victim, int damageValue) {
        String text = victim.getName() + " takes <" + damageValue + "> damages";
        SwViewConsole.basicPrint(text);

        text = victim.getName() + " is " + victim.getLife() + " / " + victim.getHitPoint() + "PV";
        SwViewConsole.basicPrint(text);
    }

    public void displayWin(Hero hero) {
        SwViewConsole.basicPrint(SwConsoleTools.ANSI_GREEN + "You win !" + SwConsoleTools.ANSI_RESET);
    }

    public void displayLoose(Hero hero) {
        SwViewConsole.basicPrint(SwConsoleTools.ANSI_RED + "You loose ..." + SwConsoleTools.ANSI_RESET);

        SwConsoleCmd[] commands = {
                new SwConsoleCmd("1", SwGameAction.REPLAY, "Yes"),
                new SwConsoleCmd("2", SwGameAction.NO_REPLAY, "Nope")
        };

        String action = SwConsoleInput._getUserInput(commands, "Do you want to play again ?");
        triggerActionPerformed(action);
    }

    public void displayExperience(Hero hero, int experience) {
        SwViewConsole.basicPrint("You earn " + experience + "xp");
    }

    public void displayLevelUp(int level) {
        SwViewConsole.basicPrint(SwConsoleTools.ANSI_GREEN + "Level " + level + " reach !" + SwConsoleTools.ANSI_RESET);
    }

}
