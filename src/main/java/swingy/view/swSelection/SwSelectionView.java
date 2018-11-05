package swingy.view.swSelection;

import swingy.model.character.hero.Hero;
import swingy.view.swView.SwViewObserver;

public interface SwSelectionView {
    void         setObservers(SwViewObserver observers[]);

    void         quitSelectionView();

    void         displaySwingyGame();
    void         displayCreateHero();
    void         displaySelectHero(Hero heroes[]);

    String       getCreateHeroType();
    String       getCreateHeroName();
    int          getSelectHeroId();

    void         success(String message);
    void         error(String message);
}
