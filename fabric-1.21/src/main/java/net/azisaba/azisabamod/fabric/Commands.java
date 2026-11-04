package net.azisaba.azisabamod.fabric;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.azisaba.azisabamod.fabric.commands.Command;
import net.azisaba.azisabamod.fabric.commands.HelpCommand;
import net.azisaba.azisabamod.fabric.commands.argument.LazyItemArgument;
import net.azisaba.azisabamod.fabric.mixin.commands.argument.item.MixinItemInputAccessor;
import net.azisaba.azisabamod.fabric.util.ComponentUtil;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.world.item.ItemStack;

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
                .then(literal("mergeItem")
                        .then(argument("item", StringArgumentType.greedyString())
                                .suggests((context, builder) -> LazyItemArgument.listSuggestions(builder))
                                .executes(context -> mergeItem(LazyItemArgument.parse(new StringReader(StringArgumentType.getString(context, "item")))))
                        )
                )
                .then(argument("command", StringArgumentType.word())
                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(CommandManager.COMMANDS.stream().map(Command::getName), builder))
                        .executes(context -> execute(StringArgumentType.getString(context, "command"), ""))
                        .then(argument("args", StringArgumentType.greedyString())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(suggest(StringArgumentType.getString(context, "command"), builder.getRemaining()), builder))
                                .executes(context -> execute(StringArgumentType.getString(context, "command"), StringArgumentType.getString(context, "args")))
                        )
                );
    }

    public static int mergeItem(ItemInput itemInput) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return 0;
        ItemStack stack = player.getInventory().getSelectedItem().copy();
        try {
            DataComponentPatch componentPatch = ((MixinItemInputAccessor) itemInput).getComponents();
            if (!stack.getItem().equals(itemInput.getItem())) {
                DataComponentPatch originalDataComponent = stack.getComponentsPatch();
                stack = itemInput.getItem().getDefaultInstance();
                stack.applyComponents(originalDataComponent);
            }
            stack.applyComponents(componentPatch);
            player.connection.send(new ServerboundSetCreativeModeSlotPacket(player.getInventory().getSelectedSlot() + 36, stack));
            player.getInventory().setSelectedItem(stack);
        } catch (Exception e) {
            player.displayClientMessage(Component.literal("Failed to apply item input: " + e.getClass().getName() + ": " + e.getMessage()), false);
        }
        return 1;
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
