package net.azisaba.azisabamod.paper.mixin.server.network;

import com.mojang.authlib.GameProfile;
import net.azisaba.azisabamod.paper.AzisabaModIntegration;
import net.azisaba.azisabamod.paper.network.Packets;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerConfigurationPacketListenerImpl.class)
public class MixinServerConfigurationPacketListenerImpl {
    @Shadow
    @Final
    private GameProfile gameProfile;

    @Inject(at = @At("HEAD"), method = "handleCustomPayload", cancellable = true)
    public void handleCustomPayload(ServerboundCustomPayloadPacket packet, CallbackInfo ci) {
        if (!(packet.payload() instanceof final net.minecraft.network.protocol.common.custom.DiscardedPayload discardedPayload)) {
            return;
        }
        Identifier packetId = packet.payload().type().id();
        if (!packetId.getNamespace().equals("azisabamod")) {
            return;
        }
        ci.cancel();
        Packets.handleCustomPayload(gameProfile, packetId.getPath(), discardedPayload.data());
    }
}
