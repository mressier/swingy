package swingy.view.hero;

import lombok.Getter;
import swingy.model.artifact.Artifact;
import swingy.model.character.hero.Hero;
import swingy.view.swView.gui.SwIcon;
import swingy.view.swView.gui.SwIconFactory;
import swingy.view.swView.gui.SwViewGUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class HeroViewGUI extends SwViewGUI {

    @Getter
    private JPanel heroPanel;

    private TitledBorder border;
    private JLabel heroLabel = new JLabel(SwIconFactory.getIcon(SwIcon.HERO));
    private JLabel artifactLabel = new JLabel();
    private JProgressBar levelBar = new JProgressBar(0, 100);
    private JLabel lifeLabel = new JLabel();
    private JLabel attackLabel = new JLabel();
    private JLabel defenseLabel = new JLabel();

    public HeroViewGUI()
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.ipadx = 0;
        constraints.weightx = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.ABOVE_BASELINE;
        this.heroPanel = new JPanel(new GridBagLayout());
        this.heroPanel.setBackground(Color.WHITE);
        this.heroPanel.add(heroLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridy++;
        lifeLabel.setIcon(SwIconFactory.getIcon(SwIcon.HEART));
        this.heroPanel.add(lifeLabel, constraints);

        constraints.gridx++;
        attackLabel.setIcon(SwIconFactory.getIcon(SwIcon.SWORD));
        this.heroPanel.add(attackLabel, constraints);

        constraints.gridx++;
        defenseLabel.setIcon(SwIconFactory.getIcon(SwIcon.SHIELD));
        this.heroPanel.add(defenseLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.ipadx = 200;
        constraints.weightx = 0.0;
        constraints.gridwidth = 4;
        this.heroPanel.add(artifactLabel, constraints);

        constraints.gridy++;
        this.heroPanel.add(this.levelBar, constraints);

        this.border = BorderFactory.createTitledBorder(this.heroPanel.getBorder(),
                "Hero",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION,
                Font.getFont(Font.MONOSPACED),
                Color.BLUE);

        this.heroPanel.setBorder(this.border);
    }

    public void setDisplayHero(Hero hero)
    {
        String      data;
        Artifact    heroArtifact = hero.getArtifact();

        /* title */
        this.border.setTitle(hero.getName() + " (" + hero.getType() + ")");

        /* Hero */
        this.heroLabel.setIcon(SwIconFactory.getHeroIcon(hero));

        /* Artifact */
        data = "Artifact : " + Artifact.resumeArtifact(heroArtifact);
        this.artifactLabel.setText(data);

        /* Level */
        this.levelBar.setMaximum((int)Hero.getNextLevelExperience(hero.getLevel()));
        this.levelBar.setValue(hero.getExperience());
        data = "Lvl " + hero.resumeLevel();
        this.levelBar.setString(data);
        this.levelBar.setStringPainted(true);

        /* Life */

        this.lifeLabel.setText(hero.resumeLife());

        /* Attack */
        this.attackLabel.setText(hero.resumeAttack());

        /* Defense */

        this.defenseLabel.setText(hero.resumeDefense());
    }

}
