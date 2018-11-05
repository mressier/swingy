package swingy.model.artifact;

import swingy.tools.SwRandom;

public abstract class ArtifactFactory
{
    private static final ArtifactDatas artifacts[] = {
            new ArtifactDatas(new WeaponMethod(), "Weapon", new String[] { "Portal Gun", "Hammer of Vigilance", "Spectral Sword", "Mystic Dagger" }),
            new ArtifactDatas(new HelmMethod(), "Helm", new String[] { "Helm of the Awakened Soul", "Dunce's Cap", "The Ironhead", "Cute Headdress" }),
            new ArtifactDatas(new ArmorMethod(), "Armor", new String[]  { "Diabolic Robe", "Serpentstalker Tunic", "Chain Armor", "Destiny Driver" })
    };

    /*
     * Public Methods
     */
    public static Artifact newArtifact(String name, String type, int effectValue) throws InvalidArtifactTypeException
    {
        for (ArtifactDatas artifact : artifacts) {
            if (type.compareToIgnoreCase(artifact.type) == 0)
                return artifact.create.method(name, effectValue);
        }
        throw new InvalidArtifactTypeException(type);
    }

    /*
     * Random artifact generation based on hero and enemy level
     */
    public static Artifact newArtifact(int heroLevel, int enemyLevel)
    {
        /*
         * Random algorithm for Artifact's level
         * based on the difference of level between the hero and the enemy/ies
         * if the enemy is weaker than hero, the artifact will be worse.
         * if the enemy is stronger than hero, the artifact will be better.
         */
        int     diff = (heroLevel - enemyLevel >= 0 ? 1 : -1);
        int     level = SwRandom.rand(0, Math.abs(heroLevel - enemyLevel)) + SwRandom.rand(-2, 2);

        level = heroLevel + diff * level;
        if (level < 1)
            level = 1;

        try
        {
            /* random type of artifact */
            int     index = SwRandom.rand(0, artifacts.length - 1);
            String  names[] = ArtifactFactory.artifacts[index].names;
            int     nameIndex = SwRandom.rand(0, names.length - 1);

            return ArtifactFactory.newArtifact(names[nameIndex], ArtifactFactory.artifacts[index].type, level);
        }
        catch (InvalidArtifactTypeException e)
        {
            System.err.println("Bad rand - " + e.getMessage());
        }
        catch (Exception e)
        {
            System.err.println("ERROR : Pogrammatic error !! " + e.getMessage());
        }

        //default (should not append)
        return new WeaponMethod().method("The Error Weapon", level);
    }

    /*
     * Private Artifact methods
     */
    private static class WeaponMethod implements ArtifactMethod {
        public Artifact method(String name, int effectValue) { return new Weapon(name, effectValue); }
    }

    private static class HelmMethod implements ArtifactMethod {
        public Artifact method(String name, int effectValue) { return new Helm(name, effectValue); }
    }

    private static class ArmorMethod implements ArtifactMethod {
        @Override
        public Artifact method(String name, int effectValue) { return new Armor(name, effectValue); }
    }

    private interface ArtifactMethod {
        Artifact method(String name, int effectValue);
    }


    private static class ArtifactDatas {
        private ArtifactMethod  create;
        private String          type;
        private String          names[] = {};

        ArtifactDatas(ArtifactMethod method, String type, String[] names) {
            this.create = method;
            this.type = type;
            this.names = names;
        }
    }
}
