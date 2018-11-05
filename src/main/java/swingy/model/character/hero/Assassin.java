package swingy.model.character.hero;

class Assassin extends Hero {
    Assassin(String name, int attack, int defense, int hitPoint) {
        super(name, "assassin", attack, defense, hitPoint);
    }

    Assassin(String name, int level, int experience, int life, int attack, int defense, int hitPoint) {
        super(name, "assassin", level, experience, life, attack, defense, hitPoint);
    }
}
