package swingy.controller.swSelection;

import swingy.model.character.hero.Hero;
import swingy.model.character.hero.HeroFactory;
import swingy.model.SwSelection.SwSelection;
import swingy.tools.SwGameAction;
import swingy.view.swView.SwViewObserver;
import swingy.view.swSelection.SwSelectionView;

public class SwSelectionController implements SwViewObserver
{
    private SwSelection         swSelection;
    private SwSelectionView     swSelectionView;

    public SwSelectionController(SwSelection swSelection, SwSelectionView swSelectionView) {
        this.swSelection = swSelection;
        this.swSelectionView = swSelectionView;
    }

    public void launchSelection() {
        this.swSelectionView.displaySwingyGame();
    }

    public Hero getHeroToPlay() {
        return this.swSelection.getHeroes().get(this.swSelectionView.getSelectHeroId());
    }
    public void removeHeroToPlay() { this.swSelection.deleteHero(this.getHeroToPlay()); }

    public void actionPerformed(String command) //must be public (inherited from SwViewObserver)
    {
        if (SwGameAction.DISPLAY_SELECT.equals(command)) {
            this._displaySelectHero();
        }
        else if (SwGameAction.DISPLAY_CREATE.equals(command)) {
            this._displayCreateHero();
        }
        else if (SwGameAction.VALID_CREATE.equals(command)) {
            this._createHero();
        }
    }

    public void quitSelection() {
        this.swSelectionView.quitSelectionView();
    }

    /*
     * Private Methods
     */
    private void    _displaySelectHero()
    {
        Hero heroes[] = new Hero[this.swSelection.getHeroes().size()];

        this.swSelection.getHeroes().toArray(heroes);
        swSelectionView.displaySelectHero(heroes);
    }

    private void    _displayCreateHero()
    {
        this.swSelectionView.displayCreateHero();
    }

    private void    _createHero()
    {
        try
        {
            Hero newHero = HeroFactory.newHero(this.swSelectionView.getCreateHeroName().trim(), this.swSelectionView.getCreateHeroType());
            this.swSelection.createHero(newHero);
            this.swSelectionView.success("Hero " + newHero.getName() + " successfully created :)");
            this.swSelectionView.displaySwingyGame();
        }
        catch (Exception exp)
        {
            this.swSelectionView.error(exp.getMessage());
        }
        this._displayCreateHero();
    }

}
