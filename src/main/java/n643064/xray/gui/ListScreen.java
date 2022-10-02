package n643064.xray.gui;

import n643064.xray.client.Config;
import n643064.xray.client.XrayClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

import static n643064.xray.client.XrayClient.MC;

public class ListScreen extends Screen
{
    public Screen previousScreen = null;
    public ListScreen()
    {
        super(Text.translatable("title.list"));
    }



    @Override
    protected void init()
    {
        super.init();

        int rightRow = this.width / 2;
        int leftRow = rightRow - 98;
        int y = (this.height / 10) * 8;

        EditBoxWidget list = new EditBoxWidget(MC.textRenderer, this.width / 2 - 80, 30, 160, (this.height / 4) * 3, Text.translatable("title.list"), Text.translatable("title.list"));
        StringBuilder blockList = new StringBuilder();
        for (Identifier identifier : XrayClient.BLOCKS)
        {
            blockList.append(identifier.toString()).append(System.lineSeparator());
        }
        for (Identifier identifier : XrayClient.FLUIDS)
        {
            blockList.append(identifier.toString()).append(System.lineSeparator());
        }
        list.setText(blockList.toString());
        this.addDrawableChild(list);

        this.addDrawableChild(new ButtonWidget(leftRow, y + 22, 98, 20, Text.translatable("button.back"), b -> MC.setScreen(this.previousScreen)));

        this.addDrawableChild(new ButtonWidget(rightRow, y + 22, 98, 20, Text.translatable("button.save"), (button ->
        {
            XrayClient.FLUIDS = new ArrayList<>();
            XrayClient.BLOCKS = new ArrayList<>();
            String[] values = list.getText().split(System.lineSeparator());
            for (String s : values)
            {
                Config.add(new Identifier(s));
            }

            ArrayList<Identifier> identifiers = new ArrayList<>(XrayClient.BLOCKS);
            identifiers.addAll(XrayClient.FLUIDS);
            Config.createList(identifiers.stream().toList());
            MC.setScreen(this.previousScreen);
        })));

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
