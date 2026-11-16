package net.azisaba.azisabamod.paper.network;

import com.mojang.authlib.GameProfile;
import net.azisaba.azisabamod.paper.AzisabaModIntegration;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class HandshakePacket implements CustomPacket {
    @Override
    public @NotNull String getId() {
        return "handshake";
    }

    @Override
    public void handle(@NotNull GameProfile gameProfile, @NotNull FriendlyByteBuf buf) {
        AzisabaModIntegration.INTEGRATION_ENABLED.add(gameProfile.id());
        AzisabaModIntegration.LOGGER.info("AzisabaMod integration enabled for {}", gameProfile.id());
    }
}
