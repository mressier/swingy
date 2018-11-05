package swingy.tools;

import swingy.model.artifact.Artifact;
import swingy.model.artifact.ArtifactFactory;
import swingy.model.artifact.InvalidArtifactTypeException;
import swingy.model.character.hero.Hero;
import swingy.model.character.hero.HeroFactory;
import swingy.model.character.hero.InvalidHeroException;
import swingy.model.character.hero.InvalidHeroTypeException;
import swingy.view.swView.console.SwConsoleTools;

import java.io.*;
import java.util.ArrayList;

public abstract class SwBackUp
{
    /*
     * Public static methods
     */
    public static ArrayList<Hero> getHeroesFromFile(String backUpFile) throws BackUpFileException
    {
        String          fileContent = SwBackUp.getFileContent(backUpFile);

        return SwBackUp.getHeroesFromFileContent(fileContent);
    }

    public static void setHeroesOnFile(String backUpFile, ArrayList<Hero> heroes) throws BackUpFileException
    {
        StringBuilder string = new StringBuilder();

        for (Hero hero: heroes)
        {
            string.append(heroToString(hero));
            string.append("\n");
        }

        try
        {
            OutputStream    file = new FileOutputStream(backUpFile);
            byte[]          output = string.toString().getBytes();

            file.write(output);
            file.close();
        }
        catch (IOException e)
        {
            throw new BackUpFileException(backUpFile, e.getMessage());
        }
    }


    /*
     * Private attributes
     */
    private static final String HERO_SEPARATOR = ";";
    private static final String ARTIFACT_SEPARATOR = "-";

    /*
     * Private Methods
     */
    private static String getFileContent(String fileName)
    {
        String      strContent = "";

        try
        {
            InputStream file = new FileInputStream(fileName);
            int         size = file.available();
            byte[]      content = new byte[size];

            if (file.read(content) != size) {
                SwLog.log.warning("Error reading " + fileName + " : not matching sizes");
            }
            strContent = new String(content);
            file.close();
        }
        catch (IOException e)
        {
            _backUpFileError("Backup file error : " + e.getMessage());
        }
        return (strContent);
    }

    private static ArrayList<Hero> getHeroesFromFileContent(String fileContent)
    {
        ArrayList<Hero> heroes = new ArrayList<Hero>();
        String[]        lines = fileContent.split("\n");

        for (String line : lines)
        {
            if (line.trim().isEmpty())
                continue;

            try
            {
                Hero hero = stringToHero(line);
                if (hero != null)
                    heroes.add(hero);
            }
            catch (InvalidHeroException e)
            {
                _backUpFileError(e.getMessage());
            }
            catch (InvalidHeroTypeException e)
            {
                _backUpFileError(e.getMessage());
            }
        }
        return heroes;
    }

    private static String heroToString(Hero hero)
    {
        return hero.getName() + HERO_SEPARATOR
                + hero.getType() + HERO_SEPARATOR
                + hero.getLevel() + HERO_SEPARATOR
                + hero.getExperience() + HERO_SEPARATOR
                + hero.getLife() + ARTIFACT_SEPARATOR
                + artifactToString(hero.getArtifact());
    }

    private static Hero stringToHero(String line) throws InvalidHeroException, InvalidHeroTypeException
    {
        int         nbHeroDatasExpected = 5;
        String      heroString;
        String[]    heroSplit;
        String      artifactString;
        Hero        hero = null;
        Artifact    artifact;

        try
        {
            heroString = line.split(ARTIFACT_SEPARATOR, 2)[0];
            artifactString = line.split(ARTIFACT_SEPARATOR, 2)[1];
        }
        catch (Exception e) {
            throw new InvalidHeroException("Missing artifact datas");
        }

        heroSplit = heroString.split(HERO_SEPARATOR);
        if (heroSplit.length < nbHeroDatasExpected)
            throw new InvalidHeroException("Missing datas about hero");
        if (heroSplit.length > nbHeroDatasExpected)
            throw new InvalidHeroException("Too much datas about hero");

        try
        {
            int     i = 0;

            hero = HeroFactory.newHero(heroSplit[i++],
                                        heroSplit[i++],
                                        Integer.parseInt(heroSplit[i++]),
                                        Integer.parseInt(heroSplit[i++]),
                                        Integer.parseInt(heroSplit[i]));

            artifact = stringToArtifact(artifactString);
            if (artifact != null)
                hero.setArtifact(artifact);
        }
        catch (Exception e)
        {
            throw new InvalidHeroException("Invalid data about hero : " + e.getMessage());
        }
        return hero;
    }

    private static String artifactToString(Artifact artifact)
    {
        if (artifact != null)
        {
             // This is valid only because artifact has one and just ONE effect.
             // (only attack, only hit point or only defense)
            int     effectValue = artifact.getHitPointEffect() + artifact.getAttackEffect() + artifact.getDefenseEffect();

            return artifact.getName() + ARTIFACT_SEPARATOR
                    + artifact.getType() + ARTIFACT_SEPARATOR
                    + effectValue;
        }
        else
            return "none";
    }

    private static Artifact stringToArtifact(String line) throws Exception
    {
        int         nbArtifactDataExpected = 3;
        Artifact    artifact;
        String[]    splitArtifact;

        if (line.equals("none"))
            return null ;

        splitArtifact = line.split(ARTIFACT_SEPARATOR);
        if (splitArtifact.length < nbArtifactDataExpected)
            throw new Exception("Missing datas about artifact");
        if (splitArtifact.length > nbArtifactDataExpected)
            throw new Exception("Too much datas about artifact");

        try
        {
            int i = 0;
            artifact = ArtifactFactory.newArtifact(splitArtifact[i++],
                                                    splitArtifact[i++],
                                                    Integer.parseInt(splitArtifact[i]));
        }
        catch (InvalidArtifactTypeException e)
        {
            throw new Exception(e.getMessage());
        }
        catch (Exception e)
        {
            throw new Exception("Invalid artifact datas " + e.getMessage());
        }

        return artifact;
    }

    private static void _backUpFileError(String error) {
        System.err.println(SwConsoleTools.ANSI_YELLOW + error + SwConsoleTools.ANSI_RESET);
    }
}
