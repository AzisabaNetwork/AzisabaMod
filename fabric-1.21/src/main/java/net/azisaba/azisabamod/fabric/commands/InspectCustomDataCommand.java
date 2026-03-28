package net.azisaba.azisabamod.fabric.commands;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public class InspectCustomDataCommand implements Command {
    @Override
    public void execute(@NotNull LocalPlayer player, @NotNull String[] args) {
        ItemStack stack = player.getInventory().getSelectedItem();
        if (stack.isEmpty()) {
            player.displayClientMessage(Component.literal("アイテムを手に持ってください"), false);
            return;
        }
        CompoundTag tag = Objects.requireNonNull(stack.get(DataComponents.CUSTOM_DATA)).copyTag();
        MutableComponent component = Component.literal("");
        component.append(NbtUtils.toPrettyComponent(tag));
        component.toFlatList(component.getStyle()
                .withHoverEvent(new HoverEvent.ShowText(Component.literal("クリックでコピー")))
                .withClickEvent(new ClickEvent.CopyToClipboard(tag.toString())));
        player.displayClientMessage(component, false);
    }

    @Override
    public @NotNull String getName() {
        return "inspectCustomData";
    }

    @Override
    public @NotNull String getDescription() {
        return "手に持っているアイテムのカスタムデータ(NBT)を表示します";
    }
}
