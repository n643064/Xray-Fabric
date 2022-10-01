package n643064.xray.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static n643064.xray.client.XrayClient.DEFAULT;

public class Config
{
    private static final String pathSeparator = System.getProperty("file.separator");
    public static String DIR_PATH = MinecraftClient.getInstance().runDirectory.getPath() + pathSeparator + "config" + pathSeparator + "xray";
    public static String LIST_PATH = DIR_PATH + pathSeparator + "blocks.list";

    static
    {
        System.out.println(DIR_PATH + LIST_PATH);
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

    public static boolean createList(Identifier[] list)
    {
        System.out.println("Create list");
        File file = new File(LIST_PATH);
        try
        {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            for (Identifier line : list)
            {
                writer.write(line.getNamespace() + ':' + line.getPath() + System.lineSeparator());
            }
            writer.close();


        } catch (IOException ioException)
        {
            ioException.printStackTrace();
            System.err.println("Couldn't create block list");
            return false;
        }

        for (Identifier id : DEFAULT)
        {
            add(id);
        }
        return true;
    }

    private static void add(Identifier id)
    {
        if (Registry.FLUID.containsId(id))
        {
            XrayClient.FLUIDS.add(id);
        } else if (Registry.BLOCK.containsId(id))
        {
            XrayClient.BLOCKS.add(id);
        }
    }

    public static void readList() throws IOException
    {

        FileReader reader = new FileReader(LIST_PATH);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String s = bufferedReader.readLine();
        while (s != null)
        {
            add(new Identifier(s));
            s = bufferedReader.readLine();
        }
    }

    public static boolean startup()
    {
        if (Files.exists(Path.of(DIR_PATH)))
        {
            if(Files.exists(Path.of(LIST_PATH)))
            {
                try
                {
                   readList();
                } catch (Exception ignored) {}
            } else {
                return Config.createList(DEFAULT);
            }
        } else {
            if (Config.createDir())
            {
                return Config.createList(DEFAULT);
            } else {
                return false;
            }
        }
        return true;
    }



}
