package n643064.xray.mixin;

import n643064.xray.gui.ConfigScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static n643064.xray.client.XrayClient.MC;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen
{

    protected GameMenuScreenMixin(Text title)
    {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci)
    {
        int x = this.width / 32;
        int y = this.height / 26;
        this.addDrawableChild(new ButtonWidget(x, y, 60, 20, Text.translatable("title.config"), (button ->
        {
            ConfigScreen configScreen = new ConfigScreen();
            configScreen.previousScreen = this;
            MC.setScreen(configScreen);
        })));
    }
}
