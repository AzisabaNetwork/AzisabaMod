package net.azisaba.azisabamod.paper;

public class ClientContext {
    private static final ThreadLocal<Boolean> isModded = new ThreadLocal<>();

    public static boolean isModded() {
        return isModded.get() != null && isModded.get();
    }

    public static void setModded(boolean modded) {
        isModded.set(modded);
    }
}
