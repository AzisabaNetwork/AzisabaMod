package net.azisaba.azisabamod.fabric;

import net.azisaba.azisabamod.fabric.commands.*;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CommandManager {
    public static final List<Command> COMMANDS = Arrays.asList(
            new DebugCommand(),
            new HelpCommand(),
            new ClearTPSCommand(),
            new InspectCustomDataCommand(),
            new MergeCustomDataCommand(),
            new SetCustomDataCommand(),
            new RemoveCustomDataTagElementCommand()
    );

    public static @Nullable Command getCommand(String name) {
        for (Command command : COMMANDS) {
            if (command.getName().equalsIgnoreCase(name)) return command;
        }
        return null;
    }
}
