package net.azisaba.azisabamod.paper.network;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Packets {
    private static final Map<String, CustomPacket> PACKETS = new HashMap<>();
    public static final HandshakePacket HANDSHAKE = register(new HandshakePacket());

    @Contract("_ -> param1")
    private static <T extends CustomPacket> @NotNull T register(@NotNull T packet) {
        PACKETS.put(packet.getId(), packet);
        return packet;
    }

    public static @Nullable CustomPacket get(@NotNull String id) {
        return PACKETS.get(id);
    }

    public static void handleCustomPayload(@NotNull GameProfile gameProfile, @NotNull String id, byte[] data) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(data));
        CustomPacket packet = get(id);
        if (packet != null) {
            packet.handle(gameProfile, buf);
        }
    }
}
