package swingy.model.character.hero;

class Sorcerer extends Hero {
    Sorcerer(String name, int attack, int defense, int hitPoint) {
        super(name, "sorcerer", attack, defense, hitPoint);
    }

    Sorcerer(String name, int level, int experience, int life, int attack, int defense, int hitPoint) {
        super(name, "sorcerer", level, experience, life, attack, defense, hitPoint);
    }
}
