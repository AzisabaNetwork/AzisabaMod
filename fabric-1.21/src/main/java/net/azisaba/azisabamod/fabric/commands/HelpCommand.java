package net.azisaba.azisabamod.fabric.commands;

import net.azisaba.azisabamod.fabric.CommandManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements Command {
    @Override
    public void execute(@NotNull LocalPlayer player, @NotNull String[] args) {
        for (Command command : CommandManager.COMMANDS) {
            player.displayClientMessage(
                    Component.literal("")
                            .append(Component.literal(("/azisaba " + command.getName() + " " + command.getUsage()).trim()).withStyle(ChatFormatting.AQUA))
                            .append(Component.literal(" - ").withStyle(ChatFormatting.GRAY))
                            .append(Component.literal(command.getDescription()).withStyle(ChatFormatting.LIGHT_PURPLE)),
                    false
            );
        }
    }

    @Override
    public @NotNull String getName() {
        return "help";
    }

    @Override
    public @NotNull String getDescription() {
        return "Displays the help message.";
    }
}
