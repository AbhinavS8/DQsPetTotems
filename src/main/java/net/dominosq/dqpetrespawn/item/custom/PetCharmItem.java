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

import java.util.List;
import java.util.UUID;

public class PetCharmItem extends Item {
    public PetCharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
        if (!player.level().isClientSide) {
            if (isTamedPet(target)) {
                //storePetData(stack, target);
                player.displayClientMessage(Component.literal("Pet linked!"), true);
                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.literal("This entity cannot be linked."), true);
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
//        if (!this.hasStoredEntity(context.getItemInHand())) return InteractionResult.PASS;
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getItemInHand();
            BlockPos clickedPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockState = level.getBlockState(clickedPos);

            BlockPos releasePos;
            if (blockState.getCollisionShape(level, clickedPos).isEmpty()) {
                releasePos = clickedPos;
            } else {
                releasePos = clickedPos.relative(direction);
            }

            this.releaseContents(context.getPlayer(), level, itemStack, clickedPos, releasePos);

            return InteractionResult.CONSUME;
        }

    }

    private boolean isTamedPet(LivingEntity entity) {
        // Example: Allow cats, dogs, and parrots to be linked
        return entity.getType() == EntityType.WOLF ||
                entity.getType() == EntityType.CAT ||
                entity.getType() == EntityType.PARROT;
    }




}
