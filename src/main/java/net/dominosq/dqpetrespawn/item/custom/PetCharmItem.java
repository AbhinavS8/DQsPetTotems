package net.dominosq.dqpetrespawn.item.custom;

import net.dominosq.dqpetrespawn.init.ModAttachments;
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


    public void handlePetClick(ItemStack stack, Player player, LivingEntity pet) {
        if (!player.level().isClientSide) {
            if (!pet.getData(ModAttachments.PET_HAS_CHARM.get())) {
                // TODO: consume item logic?
                pet.setData(ModAttachments.PET_HAS_CHARM.get(), true);
                player.displayClientMessage(
                        Component.literal("Pet linked: " + pet.getName().getString()),
                        false
                );
            }
            else {
                player.displayClientMessage(
                        Component.literal("Pet already linked vro"),
                        false
                );
            }

        }
    }


}
