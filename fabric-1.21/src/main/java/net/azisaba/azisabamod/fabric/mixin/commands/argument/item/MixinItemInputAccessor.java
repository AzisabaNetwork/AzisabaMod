package net.azisaba.azisabamod.fabric.mixin.commands.argument.item;

import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.component.DataComponentPatch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemInput.class)
public interface MixinItemInputAccessor {
    @Accessor
    DataComponentPatch getComponents();
}
