package net.azisaba.azisabamod.paper.mixin.network;

import net.azisaba.azisabamod.paper.AzisabaModIntegration;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class MixinConnection {
    @Shadow
    private volatile @Nullable PacketListener packetListener;

    @Inject(at = @At("HEAD"), method = "handleDisconnection")
    public void disconnect(CallbackInfo ci) {
        try {
            if (packetListener instanceof ServerCommonPacketListenerImpl impl) {
                AzisabaModIntegration.INTEGRATION_ENABLED.remove(impl.getOwner().id());
            }
        } catch (Exception ignored) {}
    }
}
