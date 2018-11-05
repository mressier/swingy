package swingy.view.swSelection;

import swingy.model.character.hero.Hero;
import swingy.model.character.hero.HeroFactory;
import swingy.tools.SwGameAction;
import swingy.view.swView.console.SwConsoleCmd;
import swingy.view.swView.console.SwConsoleInput;
import swingy.view.swView.console.SwConsoleTools;
import swingy.view.swView.console.SwViewConsole;
import swingy.view.swView.SwViewObserver;

public class SwSelectionViewConsole extends SwViewConsole implements SwSelectionView
{
    private Hero            allHeroes[] = HeroFactory.allHeroes();
    private SwConsoleCmd    createHeroCmds[];

    private int       selectHeroId;
    private String    createHeroType;
    private String    createHeroName;

    /*
     * Constructor
     */
    public SwSelectionViewConsole() {
        SwViewConsole.basicPrint(SwConsoleTools.SwingyTitle);
        _initSelectionValue();
    }

    /*
     * Public Method
     */
    public void setObservers(SwViewObserver observers[])
    {
        this.observers = observers;
    }

    public void quitSelectionView() {}

    public void displaySwingyGame()
    {
        SwConsoleCmd commands[] = {
                                    new SwConsoleCmd("0", SwGameAction.CHANGE_DISPLAY, "Change to GUI mode"),
                                    new SwConsoleCmd("1", SwGameAction.DISPLAY_CREATE, "Create Hero"),
                                    new SwConsoleCmd("2", SwGameAction.DISPLAY_SELECT, "Select Hero"),
                                    new SwConsoleCmd("Q", SwGameAction.QUIT, "Save and quit the Game")
                                };

        String action = SwConsoleInput._getUserInput(commands, "Welcome ! What do you want to do ?");
        triggerActionPerformed(action);
    }

    public void displayCreateHero()
    {
        String input;

        SwViewConsole.basicPrint(SwConsoleTools.CreateHeroTitle);

        /* Ask for hero type */
        input = SwConsoleInput._getUserInput(this.createHeroCmds, "Create your hero to eat the whole world!");
        if (input.equals("back"))
        {
            displaySwingyGame();
            return ;
        }
        this.createHeroType = input;

        /* ask for hero Name */
        while (true)
        {
            SwViewConsole.basicPrint("Enter the name of your hero : ");
            this.createHeroName = SwConsoleInput.readLineFromStdin();
            if (this.createHeroName == null || this.createHeroName.trim().equals(""))
                this.error("Name cannot be blank");
            else {
                break ;
            }
        }
        /* valid creation */
        triggerActionPerformed(SwGameAction.VALID_CREATE.toString());
    }

    public void displaySelectHero(Hero[] heroes)
    {
        SwConsoleCmd[]  commands = new SwConsoleCmd[heroes.length + 1]; // +1 for back commands
        String          input;
        String          desc;
        Hero            hero;

        /* if no hero */
        if (heroes.length == 0)
        {
            SwViewConsole.printWarning("You must create an hero !");
            this.displayCreateHero();
            return ;
        }

        /* add actions and commands */
        for (int i = 0; i < heroes.length; i++)
        {
            hero = heroes[i];
            desc = hero.getName()  + " (" + hero.getType()
                    + " lvl " + hero.getLevel()
                    + "|" + hero.getExperience() + "XP"
                    + ")";
            commands[i] = new SwConsoleCmd(String.valueOf(i), String.valueOf(i), desc);
        }
        /* add back cmd */
        commands = SwConsoleCmd.addBackCmd(commands, heroes.length);

        /* get user input */
        SwViewConsole.basicPrint(SwConsoleTools.SelectHeroTitle);
        input = SwConsoleInput._getUserInput(commands, "Choose your hero to go to conquer the whole world !\n");
        if (input.equals("back"))
        {
            displaySwingyGame();
            return;
        }

        try {
            this.selectHeroId = Integer.parseInt(input);
        }
        catch (Exception e) {
            this.error("Ooups looks like Mathilde did a big poo again!");
            displaySwingyGame();
        }
        triggerActionPerformed(SwGameAction.PLAY.toString());
    }

    public String getCreateHeroName() {
        return this.createHeroName;
    }

    public String getCreateHeroType() {
        return this.createHeroType;
    }

    public int getSelectHeroId() {
        return this.selectHeroId;
    }

    /*
     * Private Methods
     */
    private void _initSelectionValue() {
        this.selectHeroId = -1;
        this.createHeroType = "";
        this.createHeroName = "";
        this._createHeroListForInput();
    }

    private void        _createHeroListForInput()
    {
        SwConsoleCmd commands[] = new SwConsoleCmd[allHeroes.length + 1]; // +1 for back commands

        for (int i = 0; i < allHeroes.length; i++)
        {
            Hero hero = allHeroes[i];
            commands[i] = new SwConsoleCmd(String.valueOf(i), hero.getType(), hero.resumeClass());
        }

        /* add default and back to the main menu */
        commands = SwConsoleCmd.addBackCmd(commands, allHeroes.length);
        this.createHeroCmds = commands;
    }

}

