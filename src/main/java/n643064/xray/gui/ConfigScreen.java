package n643064.xray.gui;

import n643064.xray.client.Config;
import n643064.xray.client.XrayClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.IOException;
import java.util.List;

import static n643064.xray.client.XrayClient.MC;

public class ConfigScreen extends Screen
{
    public Screen previousScreen = null;
    public ConfigScreen()
    {
        super(Text.translatable("title.config"));




    }

    @Override
    protected void init()
    {
        super.init();

        int rightRow = this.width / 2;
        int leftRow = rightRow - 98;
        int incrementY = this.height / 9;


        this.addDrawableChild(new ButtonWidget(leftRow, incrementY * 7, 98, 20, Text.translatable("button.back"), (button -> MC.setScreen(this.previousScreen))));
        this.addDrawableChild(new ButtonWidget(leftRow, incrementY, 98, 20, Text.translatable("title.list"), (button ->
        {
            ListScreen listScreen = new ListScreen();
            listScreen.previousScreen = this;
            MC.setScreen(listScreen);
        })));
        this.addDrawableChild(new ButtonWidget(leftRow, incrementY * 2, 98, 20, Text.translatable("button.listReset"), (button ->
        {
            Config.createList(List.of(XrayClient.DEFAULT));
            try
            {
                Config.readList();
            } catch (IOException ignored)
            {
                System.out.println("Couldn't read list");
            }
        })));
        this.addDrawableChild(new ButtonWidget(leftRow, incrementY * 3, 98, 20, Text.translatable("button.listRefresh"), (button ->
        {
            try
            {
                Config.readList();
            } catch (IOException ignored)
            {
                System.out.println("Couldn't read list");
            }
        })));

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
