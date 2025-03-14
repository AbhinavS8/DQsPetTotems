package net.dominosq.dqpetrespawn.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PetRespawnAnchorBlockEntity extends BlockEntity {
    public PetRespawnAnchorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.PET_RESPAWN_ANCHOR_BE.get(), pos, blockState);
    }
}
