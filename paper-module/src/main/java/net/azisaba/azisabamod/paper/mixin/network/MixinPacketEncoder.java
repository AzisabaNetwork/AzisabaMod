package net.azisaba.azisabamod.paper.mixin.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.azisaba.azisabamod.paper.AzisabaModIntegration;
import net.azisaba.azisabamod.paper.mixin.network.protocol.game.MixinClientboundBlockUpdatePacketAccessor;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PacketEncoder.class)
public class MixinPacketEncoder {
    @Inject(at = @At("HEAD"), method = "encode(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;Lio/netty/buffer/ByteBuf;)V")
    public void encode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, ByteBuf byteBuf, CallbackInfo ci) {
        try {
            if (!(channelHandlerContext.pipeline().get(Connection.class).getPacketListener() instanceof ServerCommonPacketListenerImpl impl)) {
                return;
            }
            if (AzisabaModIntegration.isModded(impl.getOwner().id())) {
                return;
            }
            if (packet instanceof ClientboundContainerSetContentPacket(
                    int containerId, int stateId, List<ItemStack> items, ItemStack carriedItem
            )) {
                for (ItemStack item : items) {
                    AzisabaModIntegration.transform(item);
                }
                AzisabaModIntegration.transform(carriedItem);
            } else if (packet instanceof ClientboundSetEntityDataPacket entityDataPacket) {
                for (SynchedEntityData.DataValue<?> packedItem : entityDataPacket.packedItems()) {
                    if (packedItem.value() instanceof ItemStack item) {
                        AzisabaModIntegration.transform(item);
                    }
                }
            } else if (packet instanceof ClientboundSystemChatPacket systemChatPacket) {
                AzisabaModIntegration.transform(systemChatPacket.content());
            } else if (packet instanceof ClientboundSetCursorItemPacket(ItemStack contents)) {
                AzisabaModIntegration.transform(contents);
            } else if (packet instanceof ClientboundContainerSetSlotPacket containerSetSlotPacket) {
                AzisabaModIntegration.transform(containerSetSlotPacket.getItem());
            } else if (packet instanceof ClientboundBlockUpdatePacket blockUpdatePacket) {
                if (AzisabaModIntegration.isCustomBlock(blockUpdatePacket.getBlockState())) {
                    ((MixinClientboundBlockUpdatePacketAccessor) blockUpdatePacket).setBlockState(AzisabaModIntegration.getFallbackBlockState(blockUpdatePacket.getBlockState()));
                }
            }
        } catch (Exception e) {
            AzisabaModIntegration.LOGGER.error("Failed to transform packet", e);
        }
    }
}
