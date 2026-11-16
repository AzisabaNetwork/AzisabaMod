package net.azisaba.azisabamod.paper.mixin.network.protocol.game;

import io.papermc.paper.antixray.ChunkPacketInfo;
import net.azisaba.azisabamod.paper.AzisabaBlocks;
import net.azisaba.azisabamod.paper.AzisabaModIntegration;
import net.azisaba.azisabamod.paper.ClientContext;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.TreeSet;

@Mixin(ClientboundLevelChunkPacketData.class)
public class MixinClientboundLevelChunkPacketData {
    @Redirect(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunkSection;write(Lnet/minecraft/network/FriendlyByteBuf;Lio/papermc/paper/antixray/ChunkPacketInfo;I)V"),
            method = "extractChunkData(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/world/level/chunk/LevelChunk;Lio/papermc/paper/antixray/ChunkPacketInfo;)V"
    )
    private static void writeSection(
            LevelChunkSection instance, FriendlyByteBuf friendlyByteBuf, ChunkPacketInfo<BlockState> chunkPacketInfo, int i
    ) {
        if (ClientContext.isModded()) {
            instance.write(friendlyByteBuf, chunkPacketInfo, i);
            return;
        }
        azisaba$copyAndTransform(instance).write(friendlyByteBuf, chunkPacketInfo, i);
    }

    @Inject(at = @At("HEAD"), method = "calculateChunkSize", cancellable = true)
    private static void calculateChunkSize(LevelChunk chunk, CallbackInfoReturnable<Integer> cir) {
        if (ClientContext.isModded()) {
            return;
        }
        int size = 0;
        for (LevelChunkSection levelChunkSection : chunk.getSections()) {
            size += azisaba$copyAndTransform(levelChunkSection).getSerializedSize();
        }
        cir.setReturnValue(size);
    }

    @Unique
    private static LevelChunkSection azisaba$copyAndTransform(LevelChunkSection original) {
        // 新しいPalettedContainerを作成
        PalettedContainer<BlockState> states = new PalettedContainer<>(
                Blocks.AIR.defaultBlockState(),
                Strategy.createForBlockStates(Block.BLOCK_STATE_REGISTRY),
                null
        );

        for (int y = 0; y < 16; y++) {
            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    BlockState state = original.getBlockState(x, y, z);

                    if (BuiltInRegistries.BLOCK.getKey(state.getBlock()).getNamespace().equals("azisaba")) {
                        Block newBlock = AzisabaBlocks.FALLBACK.getOrDefault(state.getBlock(), Blocks.STONE);
                        state = newBlock.defaultBlockState();
                    }

                    states.set(x, y, z, state);
                }
            }
        }

        // 元のBiome情報はそのまま引き継ぐ
        LevelChunkSection copy = new LevelChunkSection(
                states,
                (PalettedContainer<Holder<Biome>>) original.getBiomes()
        );

        // ブロック数などのカウントを再計算
        copy.recalcBlockCounts();

        return copy;
    }
}
