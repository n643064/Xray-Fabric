package n643064.xray.client;

import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static n643064.xray.client.XrayClient.VISIBLE;

public class Config
{
    private static final String pathSeparator = System.getProperty("file.separator");
    public static String DIR_PATH = MinecraftClient.getInstance().runDirectory.getPath() + pathSeparator + "config" + pathSeparator + "xray";
    public static String LIST_PATH = DIR_PATH + pathSeparator + "blocks.list";

    static
    {
        System.out.println(DIR_PATH + LIST_PATH);
    }

    public static boolean doesDirExist()
    {
        File file = new File(DIR_PATH);
        return file.exists();
    }

    public static boolean doesListExist()
    {
        File file = new File(LIST_PATH);
        return file.exists();
    }

    public static boolean createDir()
    {
        try
        {
            Files.createDirectory(Path.of(DIR_PATH));
        } catch (IOException ioException)
        {
            ioException.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createList(String[] list)
    {
        System.out.println("Create list");
        File file = new File(LIST_PATH);
        try
        {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            for (String line : list)
            {
                writer.write(line + System.lineSeparator());
            }
            writer.close();

        } catch (IOException ioException)
        {
            ioException.printStackTrace();
            System.err.println("Couldn't create block list");
            return false;
        }

        return true;
    }


    public static boolean startup()
    {
        if (Config.doesDirExist())
        {
            if(Config.doesListExist())
            {

                try
                {
                    FileReader reader = new FileReader(LIST_PATH);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line;
                    XrayClient.BLOCKS = new ArrayList<>(bufferedReader.lines().toList());
                } catch (Exception ignored) {}
            } else {
                return Config.createList(VISIBLE);
            }
        } else {
            if (Config.createDir())
            {
                return Config.createList(VISIBLE);
            } else {
                return false;
            }
        }
        return true;
    }



}
