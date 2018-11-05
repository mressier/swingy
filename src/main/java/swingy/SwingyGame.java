package swingy;

import swingy.controller.swGame.SwGameController;
import swingy.controller.swSelection.SwSelectionController;
import swingy.model.character.hero.Hero;
import swingy.model.SwSelection.SwSelection;
import swingy.tools.BackUpFileException;
import swingy.model.swGame.SwGame;
import swingy.tools.SwBackUp;
import swingy.tools.SwGameAction;
import swingy.view.swGame.SwGameView;
import swingy.view.swGame.SwGameViewConsole;
import swingy.view.swGame.SwGameViewGUI;
import swingy.view.swView.InvalidDisplayModeException;
import swingy.view.swView.SwDisplayMode;
import swingy.view.swSelection.*;
import swingy.view.swView.SwViewObserver;

import java.util.ArrayList;

public class SwingyGame implements SwViewObserver
{
    private String                      backUpFile;
    private SwDisplayMode               displayMode;
    private ArrayList<Hero>             heroes;

    private SwSelectionController       swSelectionController;
    private SwGameController            swGameController;

    SwingyGame(String backUpFile, String displayMode) throws InvalidDisplayModeException, BackUpFileException
    {
        this.backUpFile = backUpFile;
        this.heroes = SwBackUp.getHeroesFromFile(backUpFile);

        _setDisplayMode(displayMode);
        _setSelectionController();
    }

    void            start()
    {
        this.swSelectionController.launchSelection();
    }

    @Override
    public void     actionPerformed(String command) {
        if (SwGameAction.QUIT.equals(command))
        {
            this._saveAndQuit();
        }
        else if (SwGameAction.PLAY.equals(command))
        {
            this.swSelectionController.quitSelection();
            this._setGameController();
            this.swGameController.playGame();
        }
        else if (SwGameAction.INTERRUPT.equals(command)) {
            this.swGameController.quitGame();
            this.start();
        }
        else if (SwGameAction.REPLAY.equals(command)) {
            this.swSelectionController.removeHeroToPlay();
            this.swGameController.quitGame();
            this.start();
        }
        else if (SwGameAction.NO_REPLAY.equals(command)) {
            this.swSelectionController.removeHeroToPlay();
            this._saveAndQuit();
        }
        else if (SwGameAction.CHANGE_DISPLAY.equals(command)) {
            this.swSelectionController.quitSelection();
            this.displayMode = this.displayMode.isGUIMode() ? SwDisplayMode.CONSOLE : SwDisplayMode.GUI;
            this._setSelectionController();
            this.start();
        }
    }

    /*
     * Private Methods
     */
    private void _setSelectionController()
    {
        SwSelectionView swSelectionView;
        SwSelection     swSelection;

        swSelection = new SwSelection(this.displayMode, this.heroes);
        if (this.displayMode.isGUIMode())
            swSelectionView = new SwSelectionViewGUI();
        else
            swSelectionView = new SwSelectionViewConsole();
        this.swSelectionController = new SwSelectionController(swSelection, swSelectionView);
        swSelectionView.setObservers(new SwViewObserver[]{this.swSelectionController, this});
    }

    private void _setGameController()
    {
        SwGameView  swGameView;
        SwGame      swGame;

        swGame = new SwGame(this.swSelectionController.getHeroToPlay());
        if (this.displayMode.isGUIMode()) {
            swGameView = new SwGameViewGUI();
        }
        else {
            swGameView = new SwGameViewConsole();
        }
        this.swGameController = new SwGameController(swGame, swGameView);
        swGameView.setObservers(new SwViewObserver[]{this.swGameController, this});
    }

    private void _setDisplayMode(String displayMode) throws InvalidDisplayModeException
    {
        if (SwDisplayMode.isGUIMode(displayMode))
            this.displayMode = SwDisplayMode.GUI;
        else if (SwDisplayMode.isConsoleMode(displayMode))
            this.displayMode = SwDisplayMode.CONSOLE;
        else
            throw new InvalidDisplayModeException(displayMode);
    }

    private void _saveAndQuit()
    {
        try
        {
            SwBackUp.setHeroesOnFile(this.backUpFile, this.heroes);
        }
        catch (BackUpFileException e)
        {
            System.err.println("Error : " + e.getMessage());
        }
        System.exit(0);
    }
}
