package swingy.model.character.hero;

class Tank extends Hero {
    Tank(String name, int attack, int defense, int hitPoint) {
        super(name, "tank", attack, defense, hitPoint);
    }

    Tank(String name, int level, int experience, int life, int attack, int defense, int hitPoint) {
        super(name, "tank", level, experience, life, attack, defense, hitPoint);
    }
}
