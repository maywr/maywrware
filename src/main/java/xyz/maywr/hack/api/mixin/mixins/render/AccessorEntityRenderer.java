package xyz.maywr.hack.api.mixin.mixins.render;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface AccessorEntityRenderer {

    @Accessor("drawBlockOutline")
    void setDrawBlockOutline(boolean draw);

    @Invoker("orientCamera")
    void invokeOrientCamera(float partialTicks);

}
