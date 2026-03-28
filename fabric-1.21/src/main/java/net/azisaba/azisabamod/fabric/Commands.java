package net.azisaba.azisabamod.fabric;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.azisaba.azisabamod.fabric.commands.Command;
import net.azisaba.azisabamod.fabric.commands.HelpCommand;
import net.azisaba.azisabamod.fabric.util.ComponentUtil;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import java.util.Objects;
import java.util.stream.Stream;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

public class Commands {
    public static LiteralArgumentBuilder<FabricClientCommandSource> builder() {
        return literal("azisaba")
                .executes(context -> {
                    new HelpCommand().execute(Objects.requireNonNull(Minecraft.getInstance().player), new String[0]);
                    return 0;
                })
                .then(argument("command", StringArgumentType.word())
                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(CommandManager.COMMANDS.stream().map(Command::getName), builder))
                        .executes(context -> execute(StringArgumentType.getString(context, "command"), ""))
                        .then(argument("args", StringArgumentType.greedyString())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(suggest(StringArgumentType.getString(context, "command"), builder.getRemaining()), builder))
                                .executes(context -> execute(StringArgumentType.getString(context, "command"), StringArgumentType.getString(context, "args")))
                        )
                );
    }

    @SuppressWarnings("SameReturnValue")
    public static int execute(String commandName, String input) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return 0;
        try {
            String[] args = input.isEmpty() ? new String[0] : input.split(" ");
            Command command = CommandManager.getCommand(commandName);
            if (command == null) {
                player.displayClientMessage(Component.literal("Unknown command: " + commandName), false);
                return 0;
            }
            command.execute(player, args);
        } catch (Exception e) {
            MutableComponent text = Component.literal("An internal error occurred while executing command: " + e.getMessage()).withStyle(ChatFormatting.RED);
            Style style = text.getStyle();
            style = style.withHoverEvent(new HoverEvent.ShowText(ComponentUtil.stacktraceComponent(e)));
            text.setStyle(style);
            player.displayClientMessage(text, false);
            e.printStackTrace();
        }
        return 0;
    }

    public static Stream<String> suggest(String commandName, String input) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return Stream.empty();
        try {
            String[] args = input.isEmpty() ? new String[0] : input.split(" ");
            Command command = CommandManager.getCommand(commandName);
            if (command != null) {
                return command.getSuggestions(player, args);
            }
        } catch (Exception e) {
            MutableComponent text = Component.literal("An internal error occurred while suggesting command arguments: " + e.getMessage()).withStyle(ChatFormatting.RED);
            Style style = text.getStyle();
            style = style.withHoverEvent(new HoverEvent.ShowText(ComponentUtil.stacktraceComponent(e)));
            text.setStyle(style);
            player.displayClientMessage(text, false);
            e.printStackTrace();
        }
        return Stream.empty();
    }
}
