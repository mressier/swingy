package swingy.model.artifact;

public class Weapon extends Artifact {

    public Weapon(String name, int attackEffect) {
        super(name, "weapon", attackEffect, 0, 0);
    }
}
