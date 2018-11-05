package swingy.view.swGame;

import lombok.Getter;
import swingy.model.artifact.Artifact;
import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.model.map.SwMap;
import swingy.tools.SwGameAction;
import swingy.tools.SwMove;
import swingy.view.fight.SwFightViewGUI;
import swingy.view.hero.HeroViewGUI;
import swingy.view.map.SwMapViewGUI;
import swingy.view.swView.SwViewObserver;
import swingy.view.swView.gui.SwIcon;
import swingy.view.swView.gui.SwIconFactory;
import swingy.view.swView.gui.SwViewGUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SwGameViewGUI extends SwViewGUI implements SwGameView
{
    private JFrame              gameFrame;
    private SwMapViewGUI        mapView;
    private HeroViewGUI         heroView;

    @Getter
    private SwFightViewGUI      fightView;

    private JButton             runBtn;
    private JButton             fightBtn;
    private JButton[]           directions;
    private JButton             pickBtn;
    private JButton             continueBtn;

    public SwGameViewGUI()
    {
        super();
        this.gameFrame = initFrame("~ Swingy ~", 1200, 1200);
        this.gameFrame.setLayout(new GridLayout(2, 1));
        this.gameFrame.addWindowListener(new SwGameViewGUI.windowActionHandler());

        this.mapView = new SwMapViewGUI();
        this.heroView = new HeroViewGUI();
        this.fightView = new SwFightViewGUI(this.gameFrame);

        _initDisplay();
        this.gameFrame.setVisible(true);
    }

    /*
     * Public Method
     */
    public void setObservers(SwViewObserver observers[])
    {
        this.observers = observers;
        this.fightView.setObservers(observers);
    }

    public void displayGame(Hero hero, SwMap map)
    {
        this.displayMap(hero, map);
        this.displayHero(hero);
        this.gameFrame.setVisible(true);
    }

    public void displayHero(Hero hero) {
        this.heroView.setDisplayHero(hero);
        this.gameFrame.setVisible(true);
    }

    public void displayMap(Hero hero, SwMap map) {
        SwCharacter[] onMap = map.getCharacters();
        SwCharacter[] enemyAndHero = new SwCharacter[onMap.length + 1];

        enemyAndHero[0] = hero;
        System.arraycopy(onMap, 0, enemyAndHero, 1, onMap.length);
        this.mapView.setDisplayMap(map.getSize(), enemyAndHero);
        this.gameFrame.setVisible(true);
    }

    public void quitGameView() {
        this.gameFrame.setVisible(false);
    }

    public void displayHeroMove(Hero hero)
    {
        this.runBtn.setEnabled(false);
        this.fightBtn.setEnabled(false);
        this.pickBtn.setEnabled(false);
        this.continueBtn.setEnabled(false);
        for (JButton direction : directions) { direction.setEnabled(true); }
    }

    public void displayStartConfrontation(Hero hero, SwCharacter[] characters)
    {
        this.fightView.displayStartConfrontation(hero, characters);
    }

    public void displayHeroRun(Hero hero)
    {
        this.runBtn.setEnabled(true);
        this.fightBtn.setEnabled(true);
        this.pickBtn.setEnabled(false);
        this.continueBtn.setEnabled(false);
        for (JButton direction : directions) { direction.setEnabled(false); }
    }

    public void displayFightOrRun(boolean tryRun, boolean tryRunSuccess)
    {
        String text;

        if (tryRun)
        {
            if (tryRunSuccess)
                text = "You go away...";
            else
                text = "Arg! You are trapped, you must fight!";
            this.fightView.addMessage(text);
        }
        else {
            this.fightView.displayFight();
        }

        this.runBtn.setEnabled(false);
        this.fightBtn.setEnabled(false);
        this.gameFrame.setVisible(true);
    }

    public void displayChangeLevel(int mapNumber, int mapLevel)
    {
        this.success("Map finish ! New map (" + mapNumber + ") level " + mapLevel);
    }

    public void displayArtifact(Artifact artifact)
    {
        this.pickBtn.setEnabled(true);
        this.continueBtn.setEnabled(true);

        this.runBtn.setEnabled(false);
        this.fightBtn.setEnabled(false);
        for (JButton direction : directions) { direction.setEnabled(false); }

        this.fightView.addMessage("A new object have been found !", Color.BLUE);
        this.fightView.addMessage(Artifact.resumeArtifact(artifact));
        this.gameFrame.setVisible(true);
    }

    /*
     * Private Methods
     */

    /*
     * Tools
     */

    /*
     * Initialization of display
     */
    private void _initDisplay()
    {
        JPanel controlPanel = new JPanel(new GridLayout(1, 2));

        /* add the map on the top */
        this.gameFrame.add(this.mapView.getMapScrollPanel());

        controlPanel.add(_initHeroActionPanel());
        controlPanel.add(_initTextPanel());
        gameFrame.add(controlPanel);
    }

    private JPanel _initTextPanel()
    {
        JPanel textPanel                = new JPanel(new BorderLayout());
        JPanel buttons                  = new JPanel(new GridLayout(1, 2));

        this.pickBtn = _generateDisableButton("Pick it", SwGameAction.PICK_ARTIFACT);
        this.continueBtn = _generateDisableButton("Leave it", SwGameAction.LEAVE_ARTIFACT);

        textPanel.add(this.fightView.getFightScrollPane(), BorderLayout.PAGE_START);
        buttons.add(pickBtn);
        buttons.add(continueBtn);
        textPanel.add(buttons, BorderLayout.PAGE_END);
        return textPanel;
    }

    private JPanel _initHeroActionPanel()
    {
        JPanel heroActionPanel          = new JPanel(new BorderLayout());

        heroActionPanel.add(_initActionButtons(), BorderLayout.PAGE_START);
        heroActionPanel.add(this.heroView.getHeroPanel(), BorderLayout.CENTER);
        return heroActionPanel;
    }

    private JPanel _initActionButtons()
    {
        JPanel heroActionsAndDirections = new JPanel(new BorderLayout());
        JPanel heroActionButtons        = new JPanel(new GridLayout(2, 1));

        /* 4 buttons */
        DirectionButtonData buttons[] = {
                new DirectionButtonData(SwMove.NORTH, BasicArrowButton.NORTH, BorderLayout.NORTH),
                new DirectionButtonData(SwMove.WEST, BasicArrowButton.WEST, BorderLayout.WEST),
                new DirectionButtonData(SwMove.EAST, BasicArrowButton.EAST, BorderLayout.EAST),
                new DirectionButtonData(SwMove.SOUTH, BasicArrowButton.SOUTH, BorderLayout.SOUTH),
        };

        this.directions = new JButton[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            this.directions[i] = new BasicArrowButton(buttons[i].button);
            this._initButton(this.directions[i], buttons[i].move.toString());
            heroActionsAndDirections.add(this.directions[i], buttons[i].pos);
        }

        /* 2 buttons */
        this.runBtn = _generateDisableButton("Run !", SwGameAction.HERO_RUN);
        this.fightBtn = _generateDisableButton("Fight !", SwGameAction.HERO_FIGHT);
        heroActionButtons.add(runBtn);
        heroActionButtons.add(fightBtn);

        heroActionsAndDirections.add(heroActionButtons, BorderLayout.CENTER);
        return heroActionsAndDirections;
    }

    /*
     * Private Tools
     */
    private JButton _generateDisableButton(String name, SwGameAction actionCmd) {
        JButton newBtn = super._generateButton(name, actionCmd);
        newBtn.setEnabled(false);
        return (newBtn);
    }

    /*
     * Private Class
     */
    private class windowActionHandler extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            triggerActionPerformed(SwGameAction.INTERRUPT.toString());
        }
    }
}

class DirectionButtonData {
    SwMove      move;
    int         button;
    String      pos;

    DirectionButtonData(SwMove move, int button, String pos) {
        this.move = move;
        this.button = button;
        this.pos = pos;
    }
}

