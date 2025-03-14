package net.dominosq.dqpetrespawn.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class PetRespawnAnchorBlockEntity extends BlockEntity {
    public PetRespawnAnchorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.PET_RESPAWN_ANCHOR_BE.get(), pos, blockState);
    }
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
    };

}
