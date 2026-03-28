package net.azisaba.azisabamod.common.util;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class LazyValue<T> {
    private final AtomicReference<T> value = new AtomicReference<>();
    private final Mode mode;
    private final Supplier<T> supplier;

    public LazyValue(@NotNull Mode mode, @NotNull Supplier<T> supplier) {
        this.mode = mode;
        this.supplier = supplier;
    }

    public T get() {
        if (mode == Mode.SYNCHRONIZED) {
            synchronized (this) {
                T t = supplier.get();
                value.set(t);
                return t;
            }
        } else {
            T t = supplier.get();
            value.set(t);
            return t;
        }
    }

    public enum Mode {
        SYNCHRONIZED,
        NON_BLOCKING,
    }
}
