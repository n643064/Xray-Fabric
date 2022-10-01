package n643064.xray.mixin;

import n643064.xray.client.XrayClient;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin
{
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void shouldRenderSide(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState, CallbackInfo ci)
    {
        if (XrayClient.XRAY)
        {
            Identifier identifier = Registry.BLOCK.getId(blockState.getBlock());
            if (!XrayClient.FLUIDS.contains(identifier)) ci.cancel();

        }
    }
}
