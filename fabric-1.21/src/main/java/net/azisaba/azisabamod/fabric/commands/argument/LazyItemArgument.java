package net.azisaba.azisabamod.fabric.commands.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class LazyItemArgument {
    public static ItemParser getParser() {
        return new ItemParser(Objects.requireNonNull(Minecraft.getInstance().player, "player is null").registryAccess());
    }

    public static @NonNull ItemInput parse(@NonNull StringReader stringReader) throws CommandSyntaxException {
        ItemParser.ItemResult itemResult = getParser().parse(stringReader);
        return new ItemInput(itemResult.item(), itemResult.components());
    }

    public static @NonNull CompletableFuture<Suggestions> listSuggestions(@NonNull SuggestionsBuilder suggestionsBuilder) {
        return getParser().fillSuggestions(suggestionsBuilder);
    }
}
