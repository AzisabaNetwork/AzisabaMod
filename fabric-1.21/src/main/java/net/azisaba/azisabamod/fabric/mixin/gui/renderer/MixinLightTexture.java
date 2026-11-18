package net.azisaba.azisabamod.fabric.mixin.gui.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(LightTexture.class)
public class MixinLightTexture {
    @Redirect(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"))
    private float fixNightVisionScale(LivingEntity livingEntity, float f) {
        float base = GameRenderer.getNightVisionScale(livingEntity, f);
        double gamma = Objects.requireNonNullElse(Minecraft.getInstance().options.gamma().get(), 0.0);
        double cappedGamma = Math.clamp(gamma, 0.0, 1.0);
        float factor = (float) (1.0 + Math.pow(cappedGamma, 1.8) * 2.0);
        float adjustedScale = base * factor;
        return Math.max(0.0f, adjustedScale);
    }
}
