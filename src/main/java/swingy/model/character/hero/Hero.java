package swingy.model.character.hero;

import lombok.Getter;
import lombok.Setter;
import swingy.model.artifact.Artifact;
import swingy.model.character.SwCharacter;

import javax.validation.constraints.Min;

public abstract class Hero extends SwCharacter
{
    @Min(0)
    @Getter @Setter
    protected int               experience;

    @Getter
    protected Artifact          artifact;

    /*
     *  Constructor
     */
    public Hero(String name, String type, int level, int experience, int attack, int defense, int hitPoint)
    {
        super(name, type, level, attack, defense, hitPoint);
        this.experience = experience;
    }

    public Hero(String name, String type, int level, int experience, int life, int attack, int defense, int hitPoint)
    {
        super(name, type, level, attack, defense, hitPoint);
        this.experience = experience;
        this.life = life > this.getHitPoint() ? this.getHitPoint() : life;
    }

    public Hero(String name, String type, int attack, int defense, int hitPoint) {
        this(name, type, 1, 0, attack, defense, hitPoint);
    }

    /*
     * Public Static Method
     */
    static public double getNextLevelExperience(int level)
    {
        return (level * 1000 + Math.pow((level - 1), 2) * 450);
    }

    /*
     * Public methods
     */
    public int getAttack()
    {
        int     bonus = 0;

        if (this.artifact != null)
            bonus = this.artifact.getAttackEffect();
        return ((this.attack * (Math.round(level / 2) + 1)) + bonus);
    }

    public int getDefense()
    {
        int     bonus = 0;

        if (this.artifact != null)
            bonus = this.artifact.getDefenseEffect();
        return ((this.defense * (Math.round(level / 2) + 1)) + bonus);
    }

    public int getHitPoint()
    {
        int     bonus = 0;

        if (this.artifact != null)
            bonus = this.artifact.getHitPointEffect();
        return ((this.hitPoint * (Math.round(level / 2) + 1)) + bonus);
    }

    public void setLevel(int level)
    {
        this.level = level;
        this.life = this.getHitPoint();
    }

    public void setArtifact(Artifact artifact)
    {
        int     oldHitPointEffect = 0;

        if (this.artifact != null)
            oldHitPointEffect = this.artifact.getHitPointEffect();
        this.artifact = artifact;
        this.life += this.artifact.getHitPointEffect() - oldHitPointEffect;
    }

    public void heal()
    {
        if (this.life < this.hitPoint)
        {
            int     life = this.life + (this.hitPoint / 3);

            if (life > this.hitPoint)
                this.life = this.hitPoint;
            else
                this.life = life;
        }
    }

    public void increaseExperience(int experience)
    {
        double      nextLevelXP = Hero.getNextLevelExperience(this.getLevel());

        experience += this.getExperience();
        if (experience > nextLevelXP) {
            this.setExperience((int)(experience - nextLevelXP));
            this.setLevel(this.getLevel() + 1);
        }
        else {
            this.setExperience(experience);
        }
    }

    @Override
    public String toString() {
        return this.name + "(" + this.type + "|LVL" + this.level + "|" + this.experience + "XP)"
                + "attack[" + this.attack + "] "
                + "defense[" + this.defense + "] "
                + "life[" + this.life + "] "
                + "pos(" + this.position.getX() + "," + this.position.getY() + ")";
    }

    /*
     * Resumes
     */
    public String resumeClass() {
        return this.type
                + " attack: " + this.attack
                + " defense: " + this.defense
                + " life : " + this.hitPoint;
    }

    public String resumeLevel() {
        return (this.getLevel() + " - "
                + this.getExperience() + " / "
                + (int)Hero.getNextLevelExperience(this.getLevel()) + " XP");
    }

    public String resumeAttack() {
        String data = this.getAttack() + "pt";
        if (this.artifact != null && this.artifact.getAttackEffect() != 0)
            data += " (+" + this.artifact.getAttackEffect() + ")";
        return (data);
    }

    public String resumeDefense() {
        String data = this.getDefense() + "pt";
        if (this.artifact != null && this.artifact.getDefenseEffect() != 0)
            data += "(+" + this.artifact.getDefenseEffect() + ")";
        return (data);
    }

    public String resumeLife() {
        String data = this.getLife() + " / " + this.getHitPoint() + "PV";
        if (this.artifact != null && this.artifact.getHitPointEffect() != 0)
            data += " (+" + this.artifact.getHitPointEffect() + ")";
        return (data);
    }

}
