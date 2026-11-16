package net.azisaba.azisabamod.paper.mixin.paper;

import io.papermc.paper.FeatureHooks;
import net.azisaba.azisabamod.paper.AzisabaModIntegration;
import net.azisaba.azisabamod.paper.ClientContext;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(FeatureHooks.class)
public class MixinFeatureHooks {
    @Redirect(method = "sendChunkRefreshPackets",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"))
    private static void sendChunkPacket(ServerGamePacketListenerImpl instance, Packet packet) {
        // cancel
    }

    @Inject(method = "sendChunkRefreshPackets",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void createChunkPacket(List<ServerPlayer> playersInRange, LevelChunk chunk, CallbackInfo ci, Map<Object, ClientboundLevelChunkWithLightPacket> refreshPackets, Iterator<ServerPlayer> var3, ServerPlayer player, Boolean shouldModify) {
        ClientContext.setModded(AzisabaModIntegration.isModded(player.getGameProfile().id()));
        try {
            player.connection.send(refreshPackets.computeIfAbsent(shouldModify, s -> new ClientboundLevelChunkWithLightPacket(chunk, chunk.level.getLightEngine(), null, null, (Boolean) s)));
        } finally {
            ClientContext.setModded(false);
        }
    }
}
