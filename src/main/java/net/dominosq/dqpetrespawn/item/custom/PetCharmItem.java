package net.dominosq.dqpetrespawn.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class PetCharmItem extends Item {
    public PetCharmItem(Properties properties) {
        super(properties);
    }






//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        if (this.hasStoredEntity(context.getItemInHand())) {
//            Level level = context.getLevel();
//            if (level.isClientSide) {
//                return InteractionResult.SUCCESS;
//            } else {
//                ItemStack itemInHand = context.getItemInHand();
//                BlockPos blockPos = context.getClickedPos();
//                Direction direction = context.getClickedFace();
//                BlockState blockState = level.getBlockState(blockPos);
//
//                BlockPos releasePos;
//                if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
//                    releasePos = blockPos;
//                } else {
//                    releasePos = blockPos.relative(direction);
//                }
//
//                this.releaseContents(context.getPlayer(), level, itemInHand, blockPos, releasePos);
//                this.tryConvertPickUpTime(level, itemInHand);
//
//                return InteractionResult.CONSUME;
//            }
//        } else {
//            return InteractionResult.PASS;
//        }

//    }

//    private boolean isTamedPet(LivingEntity entity) {
//        // Example: Allow cats, dogs, and parrots to be linked
//        return entity.getType() == EntityType.WOLF ||
//                entity.getType() == EntityType.CAT ||
//                entity.getType() == EntityType.PARROT;
//    }

    public void handlePetClick(ItemStack stack, Player player, LivingEntity pet) {
        if (!player.level().isClientSide) {
            player.displayClientMessage(
                    Component.literal("Pet linked: " + pet.getUUID()),
                    false
            );

            // TODO: Save your UUID / NBT / data component here
        }
    }


}
