package net.azisaba.azisabamod.fabric.commands;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InspectCommand implements Command {
    @Override
    public void execute(@NotNull LocalPlayer player, @NotNull String[] args) {
        ItemStack stack = player.getInventory().getSelectedItem();
        String item = stack.getItemHolder().getRegisteredName() + " + " + stack.getComponentsPatch();
        MutableComponent component = Component.literal(item);
        component.withStyle(style -> style
                .withHoverEvent(new HoverEvent.ShowText(Component.literal("クリックでコピー")))
                .withClickEvent(new ClickEvent.CopyToClipboard(item)));
        player.displayClientMessage(component, false);
    }

    @Override
    public @NotNull String getName() {
        return "inspect";
    }

    @Override
    public @NotNull String getDescription() {
        return "手に持っているアイテムのデータコンポーネントを表示します";
    }
}
