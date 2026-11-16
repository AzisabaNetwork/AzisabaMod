package net.azisaba.azisabamod.paper.mixin.world.level.block;

import net.azisaba.azisabamod.paper.AzisabaBlocks;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blocks.class)
public class MixinBlocks {
    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void init(CallbackInfo ci) {
        AzisabaBlocks.init();
    }
}
