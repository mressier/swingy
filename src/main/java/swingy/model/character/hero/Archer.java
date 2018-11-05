package swingy.model.character.hero;

class Archer extends Hero {
    Archer(String name,  int attack, int defense, int hitPoint) {
        super(name, "archer", attack, defense, hitPoint);
    }

    Archer(String name, int level, int experience, int life, int attack, int defense, int hitPoint) {
        super(name, "archer", level, experience, life, attack, defense, hitPoint);
    }
}
