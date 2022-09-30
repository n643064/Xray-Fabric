package n643064.xray.mixin;

import n643064.xray.SimpleOptionInterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin<T> implements SimpleOptionInterface<T>
{
    @Shadow
    T value;

    @Shadow
    @Final
    private Consumer<T> changeCallback;

    @Override
    public void forceSetValue(T newValue)
    {
        if(!MinecraftClient.getInstance().isRunning())
        {
            value = newValue;
            return;
        }
        if(!Objects.equals(value, newValue))
        {
            value = newValue;
            changeCallback.accept(value);
        }
    }
}