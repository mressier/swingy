package swingy.view.swView.gui;

import lombok.Getter;
import swingy.model.character.SwCharacter;
import swingy.model.character.hero.Hero;
import swingy.model.character.vilain.Vilain;

import javax.swing.*;

public class SwIconFactory
{
    /* paths */
    static final String                         RESOURCES_PATH = "src/main/resources/";

    /* icons */
    private static final GameIcon               ICON_DANGER = new GameIcon(SwIcon.DANGER, "danger.jpg");
    private static final GameIcon               ICON_DEAD = new GameIcon(SwIcon.DEAD, "dead.jpg");
    private static final GameIcon               ICON_GRASS = new GameIcon(SwIcon.GRASS, "grass.jpg");
    private static final GameIcon               ICON_HERO = new GameIcon(SwIcon.HERO, "bonhomme.jpg");
    private static final GameIcon               ICON_HEART = new GameIcon(SwIcon.HEART, "heart.jpg");
    private static final GameIcon               ICON_SWORD = new GameIcon(SwIcon.SWORD, "sword.jpg");
    private static final GameIcon               ICON_SHIELD = new GameIcon(SwIcon.SHIELD, "shield.jpg");
    private static final GameIcon               ICON_TITLE = new GameIcon(SwIcon.TITLE, "title.jpg");
    private static final GameIcon               ICON_ARCHER = new GameIcon(SwIcon.ARCHER, "archer.jpg");
    private static final GameIcon               ICON_ASSASSIN = new GameIcon(SwIcon.ASSASSIN, "assassin.jpg");
    private static final GameIcon               ICON_SORCERER = new GameIcon(SwIcon.SORCERER, "sorcerer.jpg");
    private static final GameIcon               ICON_TANK = new GameIcon(SwIcon.TANK, "tank.jpg");
    private static final GameIcon               ICON_WARRIOR = new GameIcon(SwIcon.WARRIOR, "warrior.jpg");

    private static final GameIcon[]             heroIcons = {ICON_ARCHER, ICON_ASSASSIN, ICON_SORCERER, ICON_TANK, ICON_WARRIOR};
    private static final GameIcon[]             allIcons = {ICON_DANGER, ICON_DEAD, ICON_GRASS, ICON_HERO, ICON_HEART,
                                                            ICON_SWORD, ICON_SHIELD, ICON_TITLE,
                                                            ICON_ARCHER, ICON_ASSASSIN, ICON_SORCERER, ICON_TANK, ICON_WARRIOR};

    public static ImageIcon getIcon(SwIcon icon)
    {
        for (GameIcon oneIcon: allIcons)
        {
            if (oneIcon.getIconName().equals(icon)) {
                return oneIcon.getImage();
            }
        }
        return (null);
    }

    public static ImageIcon getIcon(SwCharacter character)
    {
        if (character instanceof Hero) {
            return (getHeroIcon((Hero)character));
        }
        else if (character instanceof Vilain && ((Vilain) character).isVisible())
        {
            if (character.getLife() > 0)
                return (getIcon(SwIcon.DANGER));
            else
                return (getIcon(SwIcon.DEAD));
        }
        return (null);
    }

    public static ImageIcon getHeroIcon(Hero hero)
    {
        for (GameIcon heroIcon : heroIcons) {
            if (hero.getType().equals(heroIcon.getIconName().getName()))
                return heroIcon.getImage();
        }
        return getIcon(SwIcon.HERO);
    }
}

class GameIcon {
    @Getter
    SwIcon      iconName;
    @Getter
    String      path;
    @Getter
    ImageIcon   image;

    GameIcon(SwIcon iconName, String path)
    {
        this.iconName = iconName;
        this.path = SwIconFactory.RESOURCES_PATH + path;
        this.image = new ImageIcon(this.path);
    }
}


