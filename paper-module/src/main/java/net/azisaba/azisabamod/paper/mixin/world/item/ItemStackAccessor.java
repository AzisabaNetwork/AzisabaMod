package net.azisaba.azisabamod.paper.mixin.world.item;

import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStack.class)
public interface ItemStackAccessor {
    @Accessor
    void setItem(Item item);

    @Accessor
    PatchedDataComponentMap getComponents();

    @Accessor
    void setComponents(PatchedDataComponentMap components);
}
