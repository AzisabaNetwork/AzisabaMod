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
import net.minecraft.nbt.TagParser;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class MergeCustomDataCommand implements Command {
    @Override
    public void execute(@NotNull LocalPlayer player, @NotNull String[] args) throws CommandSyntaxException {
        ItemStack stack = player.getInventory().getSelectedItem().copy();
        if (stack.isEmpty()) {
            player.displayClientMessage(Component.literal("アイテムを手に持ってください"), false);
            return;
        }
        CompoundTag tag = Objects.requireNonNull(stack.get(DataComponents.CUSTOM_DATA)).copyTag();
        tag.merge(TagParser.parseCompoundFully(String.join(" ", args)));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        player.connection.send(new ServerboundSetCreativeModeSlotPacket(player.getInventory().getSelectedSlot() + 36, stack));
    }

    @Override
    public @NotNull String getName() {
        return "mergeCustomData";
    }

    @Override
    public @NotNull String getDescription() {
        return "カスタムデータ(NBT)をまぜまぜします";
    }

    @Override
    public @NotNull List<String> getUsage() {
        return Collections.singletonList("<NBT>");
    }
}
