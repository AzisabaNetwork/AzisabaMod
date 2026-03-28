package net.azisaba.azisabamod.fabric.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class RemoveCustomDataTagElementCommand implements Command {
    @Override
    public void execute(@NotNull LocalPlayer player, @NotNull String[] args) {
        ItemStack stack = player.getInventory().getSelectedItem().copy();
        if (stack.isEmpty()) {
            player.displayClientMessage(Component.literal("アイテムを手に持ってください"), false);
            return;
        }
        CompoundTag tag = Objects.requireNonNull(stack.get(DataComponents.CUSTOM_DATA)).copyTag();
        for (String key : args) {
            CompoundTag current = tag;
            String[] arr = key.split("/");
            for (int i = 0; i < arr.length; i++) {
                String s = arr[i];
                if (i == arr.length - 1) {
                    current.remove(s);
                } else {
                    if (!(current.get(s) instanceof CompoundTag)) {
                        break;
                    }
                    current = current.getCompound(s).orElse(null);
                    if (current == null) break;
                }
            }
        }
        if (tag.isEmpty()) {
            stack.remove(DataComponents.CUSTOM_DATA);
        } else {
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
        player.connection.send(new ServerboundSetCreativeModeSlotPacket(player.getInventory().getSelectedSlot() + 36, stack));
    }

    @Override
    public @NotNull String getName() {
        return "removeCustomDataTagElement";
    }

    @Override
    public @NotNull String getDescription() {
        return "手に持っているアイテムのカスタムデータ(NBT)から指定した要素を削除します";
    }

    @Override
    public @NotNull List<String> getUsage() {
        return Collections.singletonList("<スラッシュ区切りの要素名>");
    }
}
