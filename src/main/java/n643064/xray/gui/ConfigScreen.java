package n643064.xray.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen
{
    public Screen previousScreen = null;
    public ConfigScreen()
    {
        super(Text.translatable("title.config"));
    }
}
