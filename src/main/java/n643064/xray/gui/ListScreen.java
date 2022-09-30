package n643064.xray.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ListScreen extends Screen
{
    public Screen previousScreen = null;
    public ListScreen()
    {
        super(Text.translatable("title.list"));
    }
}
