package net.azisaba.azisabamod.paper.mixin.world.item;

import net.azisaba.azisabamod.paper.AzisabaItems;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Items.class)
public class MixinItems {
    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void init(CallbackInfo ci) {
        AzisabaItems.init();
    }
}
