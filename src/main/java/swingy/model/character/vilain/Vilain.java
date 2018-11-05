package swingy.model.character.vilain;

import lombok.Getter;
import lombok.Setter;
import swingy.model.character.SwCharacter;

public class Vilain extends SwCharacter {

    @Getter @Setter
    private boolean isVisible;

    /*
     * Constructor
     */
    public Vilain(String name, String type, int level, int attack, int defense, int hitPoint) {
        super(name, type, level, attack, defense, hitPoint);
        this.isVisible = false;
    }

    /*
     * Public Methods
     */

    @Override
    public String toString() { return super.toString(); }

    public int getAttack() {
        return (this.attack * level);
    }

    public int getDefense() {
        return (this.defense * level);
    }

    public int getHitPoint() { return (this.hitPoint * level); }
}
