package swingy.model.SwSelection;

import lombok.Getter;
import lombok.Setter;
import swingy.controller.swGame.SwGameController;
import swingy.model.character.hero.HeroFactory;
import swingy.model.character.hero.InvalidHeroException;
import swingy.model.character.hero.Hero;
import swingy.view.swGame.SwGameView;
import swingy.view.swGame.SwGameViewConsole;
import swingy.view.swGame.SwGameViewGUI;
import swingy.view.swView.SwDisplayMode;
import swingy.view.swView.SwViewObserver;
import swingy.view.swView.console.SwConsoleInput;
import swingy.view.swView.console.SwConsoleTools;
import swingy.view.swView.console.SwViewConsole;

import java.util.ArrayList;

public class SwSelection
{
    @Getter
    private ArrayList<Hero >        heroes = new ArrayList<Hero>();

    @Getter @Setter
    private SwDisplayMode           displayMode;

    /*
     * Constructor
     */
    public SwSelection(SwDisplayMode mode, ArrayList<Hero> heroes)
    {
        this.displayMode = mode;
        this.heroes = heroes;
    }

    /*
     * Public Methods
     */
    public void createHero(Hero hero) throws InvalidHeroException
    {
        HeroFactory.checkHeroConsistency(hero);
        heroes.add(hero);
    }

    public void deleteHero(Hero hero)
    {
        heroes.remove(hero);
    }
}
