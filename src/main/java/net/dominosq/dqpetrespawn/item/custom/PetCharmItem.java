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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
                player.displayClientMessage(Component.literal("Pet linked!"), true);
                CompoundTag tag = new CompoundTag();
                target.save(tag);  // or the newer data components API
                player.displayClientMessage(Component.literal(tag.toString()), true);


                try {
                    // 3. Choose the file path
                    Path outPath = Path.of("debug", "entity_dump.txt");

                    // Ensure debug directory exists
                    Files.createDirectories(outPath.getParent());

                    // 4. Write the text to the file
                    Files.writeString(outPath, tag.toString());

                    // Notify the player (server sends to client)
                    player.displayClientMessage(
                            Component.literal("NBT dumped to debug/entity_dump.txt"),
                            false
                    );

                } catch (IOException e) {
                    player.displayClientMessage(
                            Component.literal("Failed to write NBT dump: " + e.getMessage()),
                            false
                    );
                    e.printStackTrace();
                }

                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.literal("This entity cannot be linked."), true);
                return InteractionResult.FAIL;
            }

        }
        return InteractionResult.PASS;
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

    private boolean isTamedPet(LivingEntity entity) {
        // Example: Allow cats, dogs, and parrots to be linked
        return entity.getType() == EntityType.WOLF ||
                entity.getType() == EntityType.CAT ||
                entity.getType() == EntityType.PARROT;
    }




}
