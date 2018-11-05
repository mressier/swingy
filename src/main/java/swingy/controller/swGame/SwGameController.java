package swingy.controller.swGame;

import swingy.tools.SwRandom;
import swingy.model.artifact.Artifact;
import swingy.tools.SwCoord;
import swingy.tools.SwGameAction;
import swingy.tools.SwMove;
import swingy.model.swGame.SwGame;
import swingy.view.swGame.SwGameView;
import swingy.view.swView.SwViewObserver;

public class SwGameController implements SwViewObserver
{
    private SwGame              game;
    private SwGameView          gameView;
    private SwFightController   fightController;

    private SwCoord previousPos;
    private Artifact            pickedArtifact;

    private int                 mapNumber;
    private static final int    LEVEL_MAX = 20;
    /*
     * Constructor
     */
    public SwGameController(SwGame game, SwGameView gameView) {
        this.game = game;
        this.gameView = gameView;
        this.fightController = new SwFightController(this.game, this.gameView.getFightView());

        this.pickedArtifact = null;
        this.mapNumber = 1;
    }

    /*
     * Public Methods
     */
    public void playGame()
    {
        this.gameView.displayGame(this.game.getHero(), this.game.getMap());
        this.gameView.displayHeroMove(this.game.getHero());
    }

    public void quitGame() {
        this.gameView.quitGameView();
    }

    @Override
    public void actionPerformed(String action)
    {
        if (SwMove.isMove(action)) {
            this._moveHero(SwMove.toMove(action));
        }
        else if (SwGameAction.HERO_RUN.equals(action)) {
            this._tryRun();
        }
        else if (SwGameAction.HERO_FIGHT.equals(action)) {
            this._fightThenArtifact();
        }
        else if (SwGameAction.PICK_ARTIFACT.equals(action)) {
            this._pickArtifact();
        }
        else if (SwGameAction.LEAVE_ARTIFACT.equals(action)) {
            this.playGame();
        }
    }

    /*
     * Private Methods
     */

    private void _pickArtifact()
    {
        if (this.pickedArtifact != null)
            this.game.getHero().setArtifact(this.pickedArtifact);
        this.pickedArtifact = null;
        this.playGame();
    }

    private void _moveHero(SwMove move)
    {
        previousPos = new SwCoord(this.game.getHero().getPosition().getX(),
                                        this.game.getHero().getPosition().getY());

        this.game.moveHero(move);

        if (this.game.isConfrontation())
        {
            this.game.setCharactersOnHeroVisible();
            this.gameView.displayMap(this.game.getHero(), this.game.getMap());
            this.gameView.displayHero(this.game.getHero());
            this.gameView.displayStartConfrontation(this.game.getHero(), this.game.getCharactersOnHeroPos());
            this.gameView.displayHeroRun(this.game.getHero());
        }
        else if (this.game.heroIsOutOfMap())
        {
            if (this.game.getHero().getLevel() >= LEVEL_MAX)
            {
                this.gameView.success("You reach the level max ! Yahou !!");
                this.quitGame();
            }
            // change level
            this.mapNumber++;
            this.game.newLevel();
            this.gameView.displayChangeLevel(this.mapNumber, this.game.getHero().getLevel());
            this.gameView.displayMap(this.game.getHero(), this.game.getMap());
            this.gameView.displayHeroMove(this.game.getHero());
        }
        else {
            this.gameView.displayMap(this.game.getHero(), this.game.getMap());
            this.gameView.displayHeroMove(this.game.getHero());
        }
    }

    private void _fightThenArtifact()
    {
        this.fightController.fight();
        if (_heroIsAlive()) {
            this.fightController.winFight();
            this._generateArtifact();
        }
        else {
            this.fightController.looseFight();
        }
    }

    private void _tryRun()
    {
        boolean run = (SwRandom.rand(0, 1) == 0);

        this.gameView.displayFightOrRun(true, run);
        if (run)
        {
            game.getHero().setPosition(previousPos);
            gameView.displayGame(game.getHero(), game.getMap());
            gameView.displayHeroMove(game.getHero());
        }
        else {
            actionPerformed("fight");
        }
    }

    private void _generateArtifact()
    {
        if (SwRandom.rand(0, 3) != 0) {
            this.pickedArtifact = this.game.generateArtifact();
            this.gameView.displayHero(this.game.getHero());
            this.gameView.displayArtifact(this.pickedArtifact);
        }
        else
        {
            // reprise
            this.playGame();
        }
    }

    /*
     * Tools
     */
    private boolean _heroIsAlive() {
        return (this.game.getHero().getLife() > 0);
    }

}

