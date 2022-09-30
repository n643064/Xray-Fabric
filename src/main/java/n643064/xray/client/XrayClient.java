package n643064.xray.client;

import n643064.xray.SimpleOptionInterface;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class XrayClient implements ClientModInitializer
{
    public static KeyBinding xkey, fkey;
    public static boolean XRAY = false;
    public static MinecraftClient MC;
    private double gamma;
    public static ArrayList<String> BLOCKS;

    public static String[] VISIBLE = {
            Blocks.COAL_ORE.getTranslationKey(),
            Blocks.DEEPSLATE_COAL_ORE.getTranslationKey(),
            Blocks.IRON_ORE.getTranslationKey(),
            Blocks.DEEPSLATE_IRON_ORE.getTranslationKey(),
            Blocks.GOLD_ORE.getTranslationKey(),
            Blocks.DEEPSLATE_GOLD_ORE.getTranslationKey(),
            Blocks.REDSTONE_ORE.getTranslationKey(),
            Blocks.DEEPSLATE_REDSTONE_ORE.getTranslationKey(),
            Blocks.DIAMOND_ORE.getTranslationKey(),
            Blocks.DEEPSLATE_DIAMOND_ORE.getTranslationKey(),
            Blocks.EMERALD_ORE.getTranslationKey(),
            Blocks.DEEPSLATE_EMERALD_ORE.getTranslationKey(),
            Blocks.NETHER_QUARTZ_ORE.getTranslationKey(),
            Blocks.NETHER_GOLD_ORE.getTranslationKey()
    };

    @Override
    public void onInitializeClient()
    {
        MC = MinecraftClient.getInstance();
        xkey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.xray",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.xray"
        ));
        fkey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fullbright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.xray"
        ));

        if (!Config.startup())
        {
            System.err.println("Couldn't set up config system, the mod will not initialize");
            return;
        }

        ClientTickEvents.START_CLIENT_TICK.register(client ->
        {
            if (xkey.wasPressed() )
            {
                XRAY = !XRAY;
                if (XRAY)
                {
                    this.gamma = MC.options.getGamma().getValue();
                    SimpleOption<Double> option = MC.options.getGamma();
                    @SuppressWarnings("unchecked")
                    SimpleOptionInterface<Double> optionInterface = (SimpleOptionInterface<Double>) (Object) option;
                    optionInterface.forceSetValue(16d);
                    MC.chunkCullingEnabled = false;
                    MC.worldRenderer.reload();
                } else
                {
                    MC.options.getGamma().setValue(gamma);
                    MC.chunkCullingEnabled = true;
                    MC.worldRenderer.reload();
                }
            }

        });
    }
}
