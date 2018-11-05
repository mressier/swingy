package swingy.model.artifact;

import lombok.Getter;
import swingy.model.SwEntity;

import javax.validation.constraints.Min;

public abstract class Artifact extends SwEntity {

    @Getter
    @Min(0)
    protected int     attackEffect;

    @Getter
    @Min(0)
    protected int     defenseEffect;

    @Getter
    @Min(0)
    protected int     hitPointEffect;

    /*
     * Constructor
     */

    // From scratch
    public Artifact(String name, String type, int attackEffect, int defenseEffect, int hitPointEffect)
    {
        super(name, type);
        this.attackEffect = attackEffect;
        this.defenseEffect = defenseEffect;
        this.hitPointEffect = hitPointEffect;
    }

    // No effect weapon -> TO REMOVE
    public Artifact(String name, String type)
    {
        this(name, type, 0, 0, 0);
    }

    @Override
    public String toString() {
        return "" + this.name + "(" + this.type + ")"
                + " att[" + this.attackEffect + "]"
                + " def[" + this.defenseEffect + "]"
                + " hitPt[" + this.hitPointEffect + "]";
    }

    static public String resumeArtifact(Artifact artifact)
    {
        if (artifact == null)
            return "None";

        String data = artifact.getName() + " (" + artifact.getType() + ")";
        if (artifact.getHitPointEffect() != 0)
            data += " hit point : +" + artifact.getHitPointEffect();
        else if (artifact.getAttackEffect() != 0)
            data += " attack : +" + artifact.getAttackEffect();
        else if (artifact.getDefenseEffect() != 0)
            data += " defense : +" + artifact.getDefenseEffect();
        return data;
    }
}
