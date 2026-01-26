package net.dominosq.dqpetrespawn.item.custom;

import net.dominosq.dqpetrespawn.init.ModAttachments;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PetTotemItem extends Item {
    public PetTotemItem(Properties properties) {
        super(properties);
    }


    public void handlePetClick(ItemStack stack, Player player, LivingEntity pet) {
        if (!player.level().isClientSide) {
            if (!pet.getData(ModAttachments.PET_HAS_TOTEM.get())) {


                // Particles
                if (player.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            ParticleTypes.END_ROD,
                            pet.getX(),
                            pet.getY() + pet.getBbHeight() * 0.6,
                            pet.getZ(),
                            10,
                            0.1, 0.1, 0.1, // very small spread
                            0.05              // very low speed
                    );

                }

                pet.setData(ModAttachments.PET_HAS_TOTEM.get(), true);
                player.displayClientMessage(
                        Component.literal("Gave a totem to " + pet.getName().getString()),
                        true
                );
            }
            else {
                player.displayClientMessage(
                        Component.literal("Pet already has a totem"),
                        true
                );
            }

        }
    }


}
