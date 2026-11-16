package net.azisaba.azisabamod.paper.mixin.network;

import net.azisaba.azisabamod.paper.AzisabaModIntegration;
import net.azisaba.azisabamod.paper.ClientContext;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.PlayerChunkSender;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerChunkSender.class)
public class MixinPlayerChunkSender {
    @Redirect(method = "sendChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"))
    private static void sendChunk(ServerGamePacketListenerImpl instance, Packet<?> packet) {
        // cancel
    }

    @Inject(method = "sendChunk",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private static void sendChunk(ServerGamePacketListenerImpl packetListener, ServerLevel level, LevelChunk chunk, CallbackInfo ci, boolean shouldModify) {
        ClientContext.setModded(AzisabaModIntegration.isModded(packetListener.getOwner().id()));
        try {
            packetListener.send(new ClientboundLevelChunkWithLightPacket(chunk, level.getLightEngine(), null, null, shouldModify));
        } finally {
            ClientContext.setModded(false);
        }
    }
}
