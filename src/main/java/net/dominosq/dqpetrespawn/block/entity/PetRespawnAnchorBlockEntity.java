package net.dominosq.dqpetrespawn.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PetRespawnAnchorBlockEntity extends BlockEntity {
    public PetRespawnAnchorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.PET_RESPAWN_ANCHOR_BE.get(), pos, blockState);
    }

//    private final ListTag petDataList = new ListTag(); // Store multiple pet NBT data
//
//    public void addPetData(TamableAnimal pet) {
//        CompoundTag petData = new CompoundTag();
//        pet.saveWithoutId(petData);
//        petDataList.add(petData); // Add to the list
//        setChanged();
//    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
//        tag.put("PetDataList", petDataList);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
//        if (tag.contains("PetDataList")) {
//            petDataList.clear();
//            petDataList.addAll(tag.getList("PetDataList", 10)); // 10 = CompoundTag type
//        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);    }

    //    public void respawnPets() {
//        if (!petDataList.isEmpty() && level != null && !level.isClientSide) {
//            for (int i = 0; i < petDataList.size(); i++) {
//                CompoundTag petData = petDataList.getCompound(i);
//
//                EntityType<?> type = EntityType.by(petData).orElse(null);
//                if (type != null) {
//                    Entity pet = type.create(level);
//                    if (pet instanceof TamableAnimal tamablePet) {
//                        tamablePet.load(petData);
//
//                        // Spawn pet at block position
//                        tamablePet.moveTo(worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5, 0, 0);
//                        level.addFreshEntity(tamablePet);
//                    }
//                }
//            }
//
//            // Clear after respawn (optional)
//            petDataList.clear();
//            setChanged();
//        }
//    }
}
