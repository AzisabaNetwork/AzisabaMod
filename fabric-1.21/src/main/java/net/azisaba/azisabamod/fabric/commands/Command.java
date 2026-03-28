package net.azisaba.azisabamod.fabric.commands;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.player.LocalPlayer;

public interface Command {
    void execute(@NotNull LocalPlayer player, @NotNull String[] args) throws Exception;

    @NotNull
    String getName();

    @NotNull
    String getDescription();

    @NotNull
    default List<String> getUsage() {
        return Collections.emptyList();
    }

    @NotNull
    default Stream<String> getSuggestions(@NotNull LocalPlayer player, @NotNull String @NotNull [] args) throws Exception {
        return getUsage().stream();
    }

    @NotNull
    default String getFullUsage() {
        return ("/azisaba " + getName() + " " + getUsage()).trim();
    }
}
