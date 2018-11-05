package swingy.view.swSelection;

import swingy.model.character.hero.Hero;
import swingy.model.character.hero.HeroFactory;
import swingy.tools.SwGameAction;
import swingy.view.swView.gui.SwIcon;
import swingy.view.swView.gui.SwIconFactory;
import swingy.view.swView.gui.SwViewGUI;
import swingy.view.swView.SwViewObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SwSelectionViewGUI extends SwViewGUI implements SwSelectionView {

    private JFrame                  selectionFrame;

    private JPanel                  controlPanel;
    private JPanel                  heroActionPanel;

    /* action panel */
    private JComboBox               creationHeroList;
    private JComboBox               existingHeroList;
    private JButton                 creationButton;
    private JButton                 playButton;
    private JTextField              creationHeroName;

    private Hero                    allHeroes[] = HeroFactory.allHeroes();

    /*
     * Constructor
     */
    public SwSelectionViewGUI()
    {
        super();

        this.selectionFrame = initFrame("Welcome to Swingy!", 1000, 600);
        this.selectionFrame.setLayout(new GridLayout(1, 2));
        this.selectionFrame.addWindowListener(new SwSelectionViewGUI.windowActionHandler());

        JPanel      imagePanel = new JPanel(new BorderLayout());
        JLabel      label = new JLabel(SwIconFactory.getIcon(SwIcon.TITLE));
        imagePanel.add(label);

        this.controlPanel = new JPanel(new GridBagLayout());
        this.heroActionPanel = new JPanel(new GridLayout(0, 1));
        _initHeroActionPanel();
        _initControlPanel();

        this.selectionFrame.add(imagePanel);
        this.selectionFrame.add(controlPanel);

        this.selectionFrame.setVisible(true);
    }

    /*
     * Public Methods
     */
    public void         setObservers(SwViewObserver observers[])
    {
        this.observers = observers;
    }

    /*
     * Display
     */
    public void         displaySwingyGame()
    {
        this.selectionFrame.setVisible(true);
        this.displayCreateHero();
    }

    public void         quitSelectionView() { this.selectionFrame.setVisible(false); }

    public void         displayCreateHero()
    {
        heroActionPanel.removeAll();
        heroActionPanel.repaint();
        _createHeroPanelDisplay(heroActionPanel);
        this.selectionFrame.setVisible(true);
    }

    public void         displaySelectHero(Hero heroes[])
    {
        heroActionPanel.removeAll();
        heroActionPanel.repaint();
        _selectHeroPanelDisplay(heroActionPanel, heroes);
        this.selectionFrame.setVisible(true);
    }

    /*
     * Some getters
     */
    public String         getCreateHeroName() { return this.creationHeroName.getText(); }
    public int            getSelectHeroId() { return this.existingHeroList.getSelectedIndex(); }
    public String         getCreateHeroType()
    {
        int index = this.creationHeroList.getSelectedIndex();
        return this.allHeroes[index].getType();
    }

    /*
     * Private Methods
     * Initialize panels
     */
    private void        _initControlPanel()
    {
        GridBagConstraints  constraints = new GridBagConstraints();

        /* buttons */
        JPanel buttons = new JPanel(new GridLayout(3, 1));
        JButton changeMode = _generateButton("Console mode", SwGameAction.CHANGE_DISPLAY);
        JButton createButton = _generateButton("Create hero", SwGameAction.DISPLAY_CREATE);
        JButton selectButton = _generateButton("Select hero", SwGameAction.DISPLAY_SELECT);
        buttons.add(changeMode);
        buttons.add(createButton);
        buttons.add(selectButton);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 20, 0, 20);
        this.controlPanel.add(buttons, constraints);

        /* hero Panel */
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(50, 50, 0, 50);
        this.controlPanel.add(this.heroActionPanel, constraints);
    }

    private void        _initHeroActionPanel()
    {
        /* hero name creation */
        this.creationHeroName = new JTextField();
        this.creationHeroName.setColumns(20);

        /* creation button */
        this.creationButton = _generateButton("Create !", SwGameAction.VALID_CREATE);
        /* enable create button */
        ButtonEnablement buttonEnablement = new ButtonEnablement(this.creationButton.getModel());
        buttonEnablement.addDocument(this.creationHeroName.getDocument());

         /* play button */
         this.playButton = _generateButton("Play !", SwGameAction.PLAY);

        /* list of heroes to create */
        String description[] = new String[this.allHeroes.length];
        for (int i = 0; i < this.allHeroes.length; i++)
        {
            Hero hero = this.allHeroes[i];
            description[i] = hero.resumeClass();
        }
        this.creationHeroList = new JComboBox(description);

        /* list of existing heroes : empty */
        this.existingHeroList = new JComboBox(new String[]{});
    }

    private void        _createHeroPanelDisplay(JPanel panel)
    {
        /* Title */
        this.heroActionPanel.setBorder(BorderFactory.createTitledBorder("Hero Creation"));

        panel.add(this.creationHeroList);
        panel.add(this.creationHeroName);
        panel.add(this.creationButton);
    }

    /*
     * Panels to display
     */
    private void        _selectHeroPanelDisplay(JPanel panel, Hero[] heroes)
    {
        /* Title */
        this.heroActionPanel.setBorder(BorderFactory.createTitledBorder("Hero Selection"));

        /* list of heroes */
        String description[] = new String[heroes.length];
        for (int i = 0; i < heroes.length; i++) {
            Hero hero = heroes[i];
            description[i] = hero.getName()  + " (" + hero.getType()
                            + " lvl " + hero.getLevel()
                            + "|" + hero.getExperience() + "XP)"
                            + " att : " + hero.getAttack()
                            + " def : " + hero.getDefense()
                            + " life : " + hero.getLife();
        }
        this.existingHeroList = new JComboBox(description);

        /* validation button */
        if (heroes.length == 0)
            this.playButton.setEnabled(false);
        else
            this.playButton.setEnabled(true);

        panel.add(this.existingHeroList);
        panel.add(this.playButton);
    }

    /*
    * Enable a button using text field value
    */
    class ButtonEnablement implements DocumentListener {

        private ButtonModel buttonModel;
        private ArrayList<Document> documents = new ArrayList<Document>();

        ButtonEnablement(ButtonModel buttonModel) {
            this.buttonModel = buttonModel;
        }

        /*
         * Package Methods
         */
        void addDocument(Document document) {
            document.addDocumentListener(this);
            this.documents.add(document);
            documentChanged();
        }

        /*
         * Private methods
         */
        private void documentChanged()
        {
            boolean buttonEnable = false;
            for (Document document : documents) {
                if (document.getLength() > 0) {
                    buttonEnable = true;
                    break ;
                }
            }
            buttonModel.setEnabled(buttonEnable);
        }

        public void insertUpdate(DocumentEvent e) { documentChanged(); }

        public void removeUpdate(DocumentEvent e) { documentChanged(); }

        public void changedUpdate(DocumentEvent e) { documentChanged(); }
    }


    /*
     * Private Methods
     */
    private class windowActionHandler extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            triggerActionPerformed(SwGameAction.QUIT.toString());
        }
    }
}
