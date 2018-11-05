package swingy.model.character;

import lombok.Getter;
import lombok.Setter;
import swingy.tools.SwLog;
import swingy.tools.SwRandom;
import swingy.model.SwEntity;
import swingy.tools.SwCoord;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public abstract class SwCharacter extends SwEntity
{
    @Min(1)
    @Max(20)
    @Getter @Setter
    protected int                 level;

    @Min(0)
    @Setter
    protected int                 attack;

    @Min(0)
    @Setter
    protected int                 defense;

    @Min(0)
    @Setter
    protected int                 hitPoint;

    @Min(0)
    @Getter @Setter
    protected int                 life;

    @Getter @Setter
    protected SwCoord             position;

    /*
     * Constructor
     */
    public SwCharacter(String name, String type, int level, int attack, int defense, int hitPoint)
    {
        this(name, type, level, 0, attack, defense, hitPoint);
        this.life = this.getHitPoint();
    }

    public SwCharacter(String name, String type, int level, int life, int attack, int defense, int hitPoint)
    {
        super(name, type);
        this.level = level;
        this.attack = attack;
        this.defense = defense;
        this.hitPoint = hitPoint;
        this.life = life;
        this.position = new SwCoord(0, 0);
        SwLog.log.info("Create SwCharacter " + this.toString());
    }

    /*
     * Abstract Methods
     */
    abstract public int getAttack();
    abstract public int getDefense();
    abstract public int getHitPoint();

    /*
     * Public Methods
     */
    @Override
    public String toString()
    {
        return this.name + "(" + this.type + "|LVL" + this.level + ")"
                + "attack[" + this.attack + "] "
                +" defense[" + this.defense + "] "
                + "life[" + this.life + "]"
                + "pos(" + this.position.getX() + "," + this.position.getY() + ")";
    }

    public int hit()
    {
        int     min = this.getAttack() - this.level;
        int     max = this.getAttack();

        if (min < 0)
            min = 0;
        return (SwRandom.rand(min, max));
    }

    public int takeDamage(int damageValue)
    {
        int min = this.getDefense() - this.level;
        int max = this.getDefense();

        if (min < 0)
            min = 0;

        int realDamage = damageValue - (SwRandom.rand(min, max));

        if (realDamage < 0)
            realDamage = 0;
        if (realDamage > this.getLife())
            realDamage = this.getLife();

        this.life -= realDamage;
        return realDamage;
    }

}
