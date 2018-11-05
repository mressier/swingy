package swingy.view.fight;

import lombok.Getter;
import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.tools.SwGameAction;
import swingy.view.swView.SwViewObserver;
import swingy.view.swView.gui.SwIcon;
import swingy.view.swView.gui.SwIconFactory;
import swingy.view.swView.gui.SwViewGUI;

import javax.swing.*;
import java.awt.*;

public class SwFightViewGUI extends SwViewGUI implements SwFightView
{
    @Getter
    private JScrollPane fightScrollPane;
    private JPanel      fightPanel;
    private JFrame      gameFrame;

    /*
     * Constructor
     */
    public SwFightViewGUI(JFrame gameFrame)
    {
        this.gameFrame = gameFrame;

        this.fightPanel = new JPanel(new GridLayout(0, 1));
        this.fightPanel.setBackground(Color.WHITE);
        this.fightPanel.setAutoscrolls(true);

        this.fightScrollPane = new JScrollPane(this.fightPanel);
        this.fightScrollPane.setPreferredSize(new Dimension(500, 550));
    }

    /*
     * Public Methods
     */
    public void setObservers(SwViewObserver observers[])
    {
        this.observers = observers;
    }

    public void displayFight()
    {
        this.addMessage("Let's fiiiiight !");
    }

    public void displayStartConfrontation(Hero hero, SwCharacter[] characters)
    {
        String text;
        this.clean();

        text = hero.getName() + " meet something on the map!";
        this.addMessage(text, Color.RED);

        for (SwCharacter character : characters) {
            text = character.getName() + " (" + character.getType() + " lvl " + character.getLevel() + ") ";
            this.addMessage(text, SwIconFactory.getIcon(SwIcon.DANGER), Color.BLACK);
        }

        this.gameFrame.setVisible(true);
    }


    public void displayHit(SwCharacter hit, int damageValue)
    {
        String text = hit.getName() + " hit with <" + damageValue + "> damages";
        this.addMessage(text);
        this.gameFrame.setVisible(true);
    }

    public void displayTakeDamage(SwCharacter victim, int damageValue)
    {
        ImageIcon image = SwIconFactory.getIcon(victim);
        String text = victim.getName() + " takes <" + damageValue + "> damages";
        this.addMessage(text);

        text = victim.getName() + " is " + victim.getLife() + " / " + victim.getHitPoint() + "PV";
        this.addMessage(text, image);
        this.gameFrame.setVisible(true);
    }

    public void displayWin(Hero hero)
    {
        this.addMessage("You win!", Color.GREEN);
        this.gameFrame.setVisible(true);
    }

    public void displayLoose(Hero hero)
    {
        this.addMessage("You loose ...", Color.RED);
        this.gameFrame.setVisible(true);

        int input = JOptionPane.showOptionDialog(this.gameFrame,
                hero.getName() + " die at level " + hero.getLevel() + ". Do you want to play again ?",
                "You loose...", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);

        if (input == JOptionPane.YES_OPTION) {
            triggerActionPerformed(SwGameAction.REPLAY.toString());
        }
        else {
            triggerActionPerformed(SwGameAction.NO_REPLAY.toString());
        }
    }

    public void displayExperience(Hero hero, int experience)
    {
        this.addMessage("You earn " + experience + "xp");
        this.gameFrame.setVisible(true);
    }

    public void displayLevelUp(int level)
    {
        this.addMessage("Level " + level + " reach !", Color.GREEN);
        this.gameFrame.setVisible(true);
    }

    /*
     * Message Methods
     */
    public void addMessage(String message, ImageIcon icon, Color color)
    {
        JLabel label = new JLabel("", JLabel.CENTER);

        label.setText(message);
        label.setForeground(color);
        if (icon != null)
            label.setIcon(icon);
        this.fightPanel.add(label);
    }

    public void addMessage(String message, Color color) { addMessage(message, null, color); }
    public void addMessage(String message, ImageIcon icon) { addMessage(message, icon, Color.BLACK); }
    public void addMessage(String message) {
        addMessage(message, null, Color.BLACK);
    }

    /*
     * Private Methods
     */
    private void clean() {
        this.fightPanel.removeAll();
        this.fightPanel.repaint();
    }
}
