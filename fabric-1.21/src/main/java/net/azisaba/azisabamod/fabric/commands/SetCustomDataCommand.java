package net.azisaba.azisabamod.fabric.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class SetCustomDataCommand implements Command {
    @Override
    public void execute(@NotNull LocalPlayer player, @NotNull String[] args) throws CommandSyntaxException {
        ItemStack stack = player.getInventory().getSelectedItem().copy();
        if (stack.isEmpty()) {
            player.displayClientMessage(Component.literal("アイテムを手に持ってください"), false);
            return;
        }
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(TagParser.parseCompoundFully(String.join(" ", args))));
        player.connection.send(new ServerboundSetCreativeModeSlotPacket(player.getInventory().getSelectedSlot() + 36, stack));
    }

    @Override
    public @NotNull String getName() {
        return "setCustomData";
    }

    @Override
    public @NotNull String getDescription() {
        return "手に持っているアイテムのカスタムデータ(NBT)を編集します";
    }

    @Override
    public @NotNull List<String> getUsage() {
        return Collections.singletonList("<NBT>");
    }
}
