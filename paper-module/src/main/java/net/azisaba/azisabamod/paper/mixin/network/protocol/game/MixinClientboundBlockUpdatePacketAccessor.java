package net.azisaba.azisabamod.paper.mixin.network.protocol.game;

import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundBlockUpdatePacket.class)
public interface MixinClientboundBlockUpdatePacketAccessor {
    @Mutable
    @Accessor
    void setBlockState(BlockState blockState);
}
