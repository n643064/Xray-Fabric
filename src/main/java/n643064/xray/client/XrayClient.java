package n643064.xray.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class XrayClient implements ClientModInitializer {
    public static KeyBinding xkey;
    public static boolean XRAY = false;
    public static MinecraftClient MC;

    public static Block[] INVISIBLE = {
            Blocks.STONE,
            Blocks.GRASS_BLOCK,
            Blocks.GRASS,
            Blocks.TALL_GRASS,
            Blocks.TALL_SEAGRASS,
            Blocks.DIRT,
            Blocks.DIORITE,
            Blocks.ANDESITE,
            Blocks.GRANITE,
            Blocks.GRAVEL,
            Blocks.MYCELIUM,
            Blocks.COARSE_DIRT,
            Blocks.PODZOL,
            Blocks.TUFF,
            Blocks.SAND,
            Blocks.SANDSTONE,
            Blocks.BEDROCK,
            Blocks.NETHERRACK,
            Blocks.BASALT,
            Blocks.SOUL_SAND,
            Blocks.SOUL_SOIL,
            Blocks.COBBLESTONE,
            Blocks.CLAY,
            Blocks.DEEPSLATE,
            Blocks.TUFF

    };
    public static ArrayList BLOCKS = new ArrayList(Arrays.asList(INVISIBLE));
    private double gamma;
    @Override
    public void onInitializeClient() {
        MC = MinecraftClient.getInstance();
        xkey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.xray",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.xray"
        ));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (xkey.wasPressed() ) {
                XRAY = !XRAY;
                if (XRAY) {
                    MC.chunkCullingEnabled = false;
                    MC.worldRenderer.reload();
                    gamma = MC.options.gamma;
                    MC.options.gamma = 420d;
                } else {
                    MC.chunkCullingEnabled = true;
                    MC.worldRenderer.reload();
                    MC.options.gamma = gamma;
                }
            }

        });
    }
}
