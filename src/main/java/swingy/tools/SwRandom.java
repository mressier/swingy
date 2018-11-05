package swingy.tools;

import java.util.Random;

public abstract class SwRandom {

    private static final Random random = new Random();

    /*
     * You should be careful with this function
     * Big values of min and max should result on overflow
     * But in the Swingy application it should not be a problem, we work on small numbers.
     */
    public static int rand(int min, int max) {
        return (SwRandom.random.nextInt((max - min) + 1) + min);
    }
}
