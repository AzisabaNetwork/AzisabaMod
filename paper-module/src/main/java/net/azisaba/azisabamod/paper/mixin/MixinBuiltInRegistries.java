package net.azisaba.azisabamod.paper.mixin;

import net.azisaba.azisabamod.paper.AzisabaBlocks;
import net.azisaba.azisabamod.paper.AzisabaItems;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltInRegistries.class)
public class MixinBuiltInRegistries {
    @Inject(at = @At("HEAD"), method = "bootStrap(Ljava/lang/Runnable;)V")
    private static void bootStrap(Runnable runnable, CallbackInfo ci) {
        AzisabaBlocks.init();
        AzisabaItems.init();
    }
}
