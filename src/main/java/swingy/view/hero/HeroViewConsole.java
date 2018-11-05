package swingy.view.hero;

import swingy.model.artifact.Artifact;
import swingy.model.character.hero.Hero;
import swingy.view.swView.console.SwViewConsole;

public class HeroViewConsole extends SwViewConsole
{
    public HeroViewConsole() {}

    /*
     * Public Methods
     */
    public void displayHero(Hero hero)
    {
        /* title */
        SwViewConsole.basicPrint("HERO : " + hero.getName() + " (" + hero.getType() + ")");

        /* artifact */
        SwViewConsole.basicPrint("Artifact : " + Artifact.resumeArtifact(hero.getArtifact()));

        /* level */
        SwViewConsole.basicPrint("Lvl " + hero.resumeLevel());
        SwViewConsole.basicPrint("------------------------------------------");

        /* life */
        SwViewConsole.basicPrint("life : " + hero.resumeLife());

        /* Attack */
        SwViewConsole.basicPrint("attack : " + hero.resumeAttack());

        /* defense */
        SwViewConsole.basicPrint("defense : " + hero.resumeDefense());
        SwViewConsole.basicPrint("------------------------------------------");
    }
}
