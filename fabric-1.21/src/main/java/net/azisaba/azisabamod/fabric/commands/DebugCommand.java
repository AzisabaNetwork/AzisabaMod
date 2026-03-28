package net.azisaba.azisabamod.fabric.commands;

import net.azisaba.azisabamod.fabric.debug.AzisabaDebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DebugCommand implements Command {
    @Override
    public void execute(@NotNull LocalPlayer player, @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("isDebugEnabled")) {
            player.displayClientMessage(Component.literal("Enabled: " + AzisabaDebugScreenEntries.isEnabled(Identifier.parse(args[1]))), false);
        }
    }

    @Override
    public @NotNull String getName() {
        return "debug";
    }

    @Override
    public @NotNull String getDescription() {
        return "Displays the help message.";
    }

    @Override
    public @NotNull List<String> getUsage() {
        List<String> suggestions = new ArrayList<>();
        for (Identifier identifier : DebugScreenEntries.allEntries().keySet()) {
            if (identifier.getNamespace().equals("azisaba")) {
                suggestions.add("isDebugEnabled " + identifier);
            }
        }
        return suggestions;
    }
}
