package swingy.model.character.hero;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public abstract class HeroFactory {

    private static final HeroData heroesData[] = {
            new HeroData(new AssasinMethod(), "assassin"),
            new HeroData(new WarriorMethod(), "warrior"),
            new HeroData(new ArcherMethod(), "archer"),
            new HeroData(new TankMethod(), "tank"),
            new HeroData(new SorcererMethod(), "sorcerer")
    };

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /*
     * Public Methods
     */
    public static Hero newHero(String name, String type) throws InvalidHeroTypeException
    {
        for (HeroData heroData : heroesData) {
            if (type.compareToIgnoreCase(heroData.type) == 0)
                return heroData.create.method(name);
        }
        throw new InvalidHeroTypeException(type);
    }

    public static Hero newHero(String name, String type, int level, int experience, int life) throws InvalidHeroTypeException, InvalidHeroException
    {
        for (HeroData heroData : heroesData)
        {
            if (type.compareToIgnoreCase(heroData.type) == 0)
            {
                Hero hero = heroData.create.method(name, level,experience, life);
                checkHeroConsistency(hero);
                return (hero);
            }
        }
        throw new InvalidHeroTypeException(type);
    }

    public static Hero[] allHeroes()
    {
        Hero        heroes[] = new Hero[heroesData.length];
        HeroData    heroData;

        for (int i = 0; i < heroes.length; i++) {
            heroData = heroesData[i];
            heroes[i] = heroData.create.method("hero name");
        }
        return (heroes);
    }

    public static void     checkHeroConsistency(Hero hero) throws InvalidHeroException
    {
        Set<ConstraintViolation<Hero>> violations = validator.validate(hero);
        for (ConstraintViolation<Hero> violation : violations)
            throw new InvalidHeroException(violation.getPropertyPath().toString(), violation.getMessage());
    }

    /*
     * Hero constructors
     */
    private static class AssasinMethod implements  HeroMethod {
        int attack      = 7;
        int defense     = 1;
        int hitPoint    = 2;

        public Hero method(String name) {
            return new Assassin(name, attack, defense, hitPoint);
        }

        public Hero method(String name, int level, int experience, int life) {
            return new Assassin(name, level, experience, life, attack, defense, hitPoint);
        }
    }

    private static class WarriorMethod implements HeroMethod {
        int attack      = 2;
        int defense     = 3;
        int hitPoint    = 6;

        public Hero method(String name) {
            return new Warrior(name, attack, defense, hitPoint);
        }

        public Hero method(String name, int level, int experience, int life) {
            return new Warrior(name, level, experience, life, attack, defense, hitPoint);
        }
    }

    private static class ArcherMethod implements HeroMethod {
        int attack      = 3;
        int defense     = 2;
        int hitPoint    = 5;

        public Hero method(String name) {
            return new Archer(name, attack, defense, hitPoint);
        }

        public Hero method(String name, int level, int experience, int life) {
            return new Archer(name, level, experience, life, attack, defense, hitPoint);
        }
    }

    private static class TankMethod implements HeroMethod {
        int attack      = 1;
        int defense     = 6;
        int hitPoint    = 3;

        public Hero method(String name) {
            return new Tank(name, attack, defense, hitPoint);
        }

        public Hero method(String name, int level, int experience, int life) {
            return new Tank(name, level, experience, life, attack, defense, hitPoint);
        }
    }

    private static class SorcererMethod implements HeroMethod {
        int attack      = 5;
        int defense     = 1;
        int hitPoint    = 4;

        public Hero method(String name) {
            return new Sorcerer(name, attack, defense, hitPoint);
        }

        public Hero method(String name, int level, int experience, int life) {
            return new Sorcerer(name, level, experience, life, attack, defense, hitPoint);
        }
    }

    private interface HeroMethod {
        Hero method(String name);
        Hero method(String name, int attack, int defense, int hitPoint);
    }

    private static class HeroData {
        private HeroMethod      create;
        private String          type;

        HeroData(HeroMethod method, String type) {
            this.create = method;
            this.type = type;
        }
    }
}

