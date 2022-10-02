package n643064.xray.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Config
{
    private static final String pathSeparator = System.getProperty("file.separator");
    public static final String DIR_PATH = MinecraftClient.getInstance().runDirectory.getPath() + pathSeparator + "config" + pathSeparator + "xray";
    public static final String LIST_PATH = DIR_PATH + pathSeparator + "blocks.list";

    static
    {
        System.out.println(DIR_PATH + LIST_PATH);
    }

    public static void clearArrayLists()
    {
        XrayClient.FLUIDS = new ArrayList<>();
        XrayClient.BLOCKS = new ArrayList<>();
        System.gc();
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

    public static void createList(List<Identifier> list)
    {
        final File file = new File(LIST_PATH);
        try
        {
            if (!file.exists()) file.createNewFile();
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
            return;
        }
        System.out.println("Created list file");
    }

    public static void add(Identifier id)
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

        final FileReader reader = new FileReader(LIST_PATH);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        String s = bufferedReader.readLine();
        while (s != null)
        {
            add(new Identifier(s));
            s = bufferedReader.readLine();
        }
    }

    public static void copyDefaultList() throws IOException
    {
        final InputStream stream = Config.class.getResourceAsStream("/default.list");
        final Path path = Path.of(LIST_PATH);
        assert stream != null;
        Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
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
                try
                {
                    copyDefaultList();
                    readList();
                } catch (IOException ignored)
                {
                    return false;
                }
            }
        } else {
            if (Config.createDir())
            {

                try
                {
                    copyDefaultList();
                    readList();
                } catch (IOException ignored)
                {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }



}
