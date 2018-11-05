package swingy.model.character.vilain;

import swingy.tools.SwRandom;

public abstract class VilainFactory {

    private static VilainDatas[] vilains = {
        new VilainDatas(new OrcMethod(), "orc", new String[] { "Prof", "Simplet", "Joyeux", "Atchoum" }),
        new VilainDatas(new SkeletonMethod(), "skeleton", new String[] { "Smelly", "Wormy", "Decompose", "Buried Alive", "King of the Night" }),
        new VilainDatas(new GhostMethod(), "ghost", new String[] { "Casper", "Fantominus" }),
        new VilainDatas(new GiantMethod(), "giant", new String[] { "Hagrid", "Graup", "Obelix" }),
        new VilainDatas(new DragonMethod(), "dragon", new String[] { "Drogon", "Rhaegal", "Viserion", "Norbert" })
    };

    private static Vilain newVilain(String name, String type, int level) throws InvalidVilainTypeException
    {
        for (VilainDatas vilain : vilains) {
            if (type.compareToIgnoreCase(vilain.type) == 0)
                return vilain.create.method(name, level);
        }
        throw new InvalidVilainTypeException(type);
    }

    /* generate random vilain from heroLevel */
    public static Vilain newVilain(int heroLevel)
    {
        try
        {
            /* randomize vilainLevel */
            int rangeValue = 2;
            int min = heroLevel <= rangeValue ? 1 : heroLevel - rangeValue;
            int max = heroLevel + rangeValue;
            int vilainLevel = SwRandom.rand(min, max);

            /* randomise vilain type */
            int typeIndex = SwRandom.rand(0, vilains.length - 1);

            /* randomise vilain name */
            int nameIndex = SwRandom.rand(0, vilains[typeIndex].names.length - 1);

            return newVilain(vilains[typeIndex].names[nameIndex], vilains[typeIndex].type, vilainLevel);
        }
        catch (Exception e)
        {
            System.err.println("ERROR : " + e.getMessage());
        }

        // default (should not append)
        return new OrcMethod().method("The Vilain Error", 1);
    }

    /*
     * Private Methods
     */
    private static class OrcMethod implements VilainMethod {
        public Vilain method(String name, int level) {
            return new Orc(name, level, 1, 1, 2);
        }
    }

    private static class SkeletonMethod implements VilainMethod {
        public Vilain method(String name, int level) {
            return new Skeleton(name, level, 2, 0, 1);
        }
    }

    private static class GhostMethod implements VilainMethod {
        public Vilain method(String name, int level) {
            return new Ghost(name, level, 1, 1, 1);
        }
    }

    private static class GiantMethod implements VilainMethod {
        public Vilain method(String name, int level) {
            return new Giant(name, level, 1, 2, 2);
        }
    }

    private static class DragonMethod implements VilainMethod {
        public Vilain method(String name, int level) {
            return new Dragon(name, level, 3, 1, 4);
        }
    }

    private static class VilainDatas {
        VilainMethod    create;
        String          type;
        String          names[] = {};

        VilainDatas(VilainMethod method, String type, String[] names) {
            this.create = method;
            this.type = type;
            this.names = names;
        }
    }

    private interface VilainMethod {
        Vilain method(String name, int level);
    }
}
