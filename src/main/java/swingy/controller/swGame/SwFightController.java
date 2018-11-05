package swingy.controller.swGame;

import swingy.tools.SwLog;
import swingy.tools.SwRandom;
import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.model.swGame.SwGame;
import swingy.view.fight.SwFightView;

class SwFightController
{
    private SwGame              game;
    private SwFightView         fightView;

    private int                 tourCount;

    SwFightController(SwGame game, SwFightView fightView)
    {
        this.game = game;
        this.fightView = fightView;
    }

    /*
     * Public Methods
     */
    void fight()
    {
        this.fightView.displayFight();

        this._fightUntilDeath();
    }

    void winFight()
    {
        Hero hero = this.game.getHero();
        int prevLevel = hero.getLevel();

        this.fightView.displayWin(hero);
        this.fightView.displayExperience(hero, this.game.increaseHeroExperience());

        if (hero.getLevel() != prevLevel)
            this.fightView.displayLevelUp(hero.getLevel());
    }

    void looseFight() {
        this.fightView.displayLoose(this.game.getHero());
    }

    /*
     * Private Method
     */

    private void _fightUntilDeath()
    {
        Hero            hero = this.game.getHero();
        SwCharacter[]   characters = this.game.getCharactersOnHeroPos();

        SwLog.log.info("A fight start. Hero : " + hero.toString() + " VS " + characters.length + " characters");

        this.tourCount = 0;
        while (_charactersOnHeroPosAreAlive() && _heroIsAlive())
        {
            this.tourCount++;
            for (SwCharacter character: characters)
            {
                if (character.getLife() <= 0 || hero.getLife() <= 0) {
                    continue;
                }
                if (SwRandom.rand(0, 2) == 0) {
                    _oneToOneFight(character, hero);
                }
                else {
                    _oneToOneFight(hero, character);
                }
            }
        }
        SwLog.log.info("Fight end");
    }

    private void  _oneToOneFight(SwCharacter attack, SwCharacter victim)
    {
        _oneToOne(attack, victim);
        if (victim.getLife() > 0)
            _oneToOne(victim, attack);
    }

    private void   _oneToOne(SwCharacter attack, SwCharacter victim)
    {
        int     attackValue;
        int     victimValue;

        SwLog.log.info(attack.getName() + " attack " + victim.getName());

        attackValue = attack.hit();
        if (tourCount % 10 == 0) {
            attackValue *= (tourCount / 10);
        }
        this.fightView.displayHit(attack, attackValue);

        victimValue = victim.takeDamage(attackValue);
        this.fightView.displayTakeDamage(victim, victimValue);
    }

    /*
     * Tools
     */
    private boolean _charactersOnHeroPosAreAlive()
    {
        boolean oneLive = false;
        for (SwCharacter character : this.game.getCharactersOnHeroPos())
        {
            if (character.getLife() > 0)
                oneLive = true;
        }
        return (oneLive);
    }

    private boolean _heroIsAlive() {
        return (this.game.getHero().getLife() > 0);
    }
}
