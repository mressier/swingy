package swingy.model.character.hero;

class Warrior extends Hero {
    Warrior(String name, int attack, int defense, int hitPoint) {
        super(name, "warrior", attack, defense, hitPoint);
    }

    Warrior(String name, int level, int experience, int life, int attack, int defense, int hitPoint) {
        super(name, "warrior", level, experience, life, attack, defense, hitPoint);
    }
}
