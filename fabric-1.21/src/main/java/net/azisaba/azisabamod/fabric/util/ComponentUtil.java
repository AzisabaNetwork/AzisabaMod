package net.azisaba.azisabamod.fabric.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class ComponentUtil {
    public static @NotNull MutableComponent stacktraceComponent(Throwable t) {
        MutableComponent component = Component.literal(t.getClass().getTypeName() + ": " + t.getMessage() + "\n");
        int collapsedInternals = 0;
        for (StackTraceElement element : t.getStackTrace()) {
            if (element.getClassName().startsWith("net.minecraft.") || element.getClassName().startsWith("org.lwjgl.") || element.getClassName().startsWith("com.mojang.")) {
                collapsedInternals++;
                continue;
            }
            if (collapsedInternals > 0) {
                component.append(Component.literal("  <" + collapsedInternals + " Minecraft classes hidden>\n"));
                collapsedInternals = 0;
            }
            component.append(Component.literal("  at " + element));
            component.append(Component.literal("\n"));
        }
        if (collapsedInternals > 0) {
            component.append(Component.literal("  <" + collapsedInternals + " Minecraft classes hidden>\n"));
        }
        if (t.getCause() != null) {
            component.append("Caused by: ");
            component.append(stacktraceComponent(t.getCause()));
        }
        return component;
    }
}
