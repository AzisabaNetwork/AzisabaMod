package net.azisaba.azisabamod.paper.network;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public interface CustomPacket {
    @NotNull
    String getId();

    void handle(@NotNull GameProfile gameProfile, @NotNull FriendlyByteBuf buf);

    default void handle(@NotNull GameProfile gameProfile, byte @NotNull [] data) {
        handle(gameProfile, new FriendlyByteBuf(Unpooled.wrappedBuffer(data)));
    }
}
