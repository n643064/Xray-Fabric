package n643064.xray.client;

import n643064.xray.SimpleOptionInterface;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class XrayClient implements ClientModInitializer
{
    public static KeyBinding xkey, fkey, nkey;
    public static boolean XRAY = false;
    public static boolean FULLBRIGHT = false;
    public static boolean NOFOG = false;
    public static MinecraftClient MC;
    public static boolean wasFullbrightDisabled = true;
    public static boolean wasNofogDisabled = true;
    private double gamma;
    public static ArrayList<Identifier> BLOCKS = new ArrayList<>();
    public static ArrayList<Identifier> FLUIDS = new ArrayList<>();

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

        nkey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nofog",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.xray"
        ));

        if (!Config.startup())
        {
            System.err.println("Couldn't set up config system, the mod will not initialize");
            return;
        }

        //DEFAULT = null;
        //System.gc();

        ClientTickEvents.START_CLIENT_TICK.register(client ->
        {
            if (xkey.wasPressed() )
            {
                toggleXray();
            } else if (fkey.wasPressed())
            {
                if (wasFullbrightDisabled) wasFullbrightDisabled = false;
                toggleFullbright();
            } else if (nkey.wasPressed())
            {
                if (wasNofogDisabled) wasNofogDisabled = false;
                NOFOG = !NOFOG;
            }
        });
    }
    private void toggleXray()
    {
        XRAY = !XRAY;
        if (XRAY)
        {
            MC.chunkCullingEnabled = false;
            MC.worldRenderer.reload();
            if (!FULLBRIGHT)
            {
                wasFullbrightDisabled = true;
                toggleFullbright();
            }
            if (!NOFOG)
            {
                wasNofogDisabled = true;
                NOFOG = true;
            }
        } else
        {
            MC.chunkCullingEnabled = true;
            MC.worldRenderer.reload();
            if (wasFullbrightDisabled)
            {
                toggleFullbright();
            }
            NOFOG = !wasNofogDisabled;
        }

    }

    private void toggleFullbright()
    {
        FULLBRIGHT = !FULLBRIGHT;
        if (FULLBRIGHT)
        {
            this.gamma = MC.options.getGamma().getValue();
            SimpleOption<Double> option = MC.options.getGamma();
            @SuppressWarnings("unchecked")
            SimpleOptionInterface<Double> optionInterface = (SimpleOptionInterface<Double>) (Object) option;
            optionInterface.forceSetValue(16d);
        } else {
            MC.options.getGamma().setValue(gamma);
        }
    }
}
