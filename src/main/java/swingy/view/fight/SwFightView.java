package swingy.view.fight;

import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;

public interface SwFightView
{
    void         displayStartConfrontation(Hero hero, SwCharacter[] characters);
    void         displayFight();
    void         displayHit(SwCharacter hit, int damageValue);
    void         displayTakeDamage(SwCharacter victim, int damageValue);
    void         displayWin(Hero hero);
    void         displayLoose(Hero hero);
    void         displayExperience(Hero hero, int experience);
    void         displayLevelUp(int level);

}
