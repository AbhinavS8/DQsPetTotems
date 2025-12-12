package net.dominosq.dqpetrespawn.event;

import net.dominosq.dqpetrespawn.DQPetRespawn;
import net.dominosq.dqpetrespawn.init.ModAttachments;
import net.dominosq.dqpetrespawn.item.custom.PetCharmItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = DQPetRespawn.MODID,bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void onRightClickPet(PlayerInteractEvent.EntityInteract event) {

        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof PetCharmItem)) return;

        Player player = event.getEntity();
        Entity target = event.getTarget();

        if (!(target instanceof LivingEntity living)) return;

        // MUST be an OwnableEntity
        if (!(living instanceof OwnableEntity ownable)) return;

        // Check ownership
        if (ownable.getOwnerUUID() == null || !ownable.getOwnerUUID().equals(player.getUUID())) {
            if (!player.level().isClientSide) {
                player.displayClientMessage(
                        Component.literal("You are not the owner of this pet."),
                        false
                );
            }
            return;
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        ((PetCharmItem) stack.getItem()).handlePetClick(stack, player, living);

    }

    @SubscribeEvent
    public static void onPetTakeDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        float damage = event.getAmount();
        float currentHealth = target.getHealth();

        // MUST be an ownable (player-owned) pet/mount
        if (!(target instanceof OwnableEntity ownable)) return;
        if (ownable.getOwnerUUID() == null) return;

        // Must have a player owner
        Entity ownerEntity = ownable.getOwner();
        if (!(ownerEntity instanceof Player owner)) return;

        // Lethal check
        if (damage < currentHealth) return;

        if (target.getData(ModAttachments.PET_HAS_CHARM.get())) {
            owner.displayClientMessage(
                    Component.literal("Your charm saved " + target.getName().getString() + "!"),
                    false
            );
            event.setCanceled(true);      // cancel the damage event
            target.setHealth(1.0F);
            target.setData(ModAttachments.PET_HAS_CHARM.get(), false);

            target.level().playSound(
                    null,                     // null = play for everyone nearby
                    target.getX(),
                    target.getY(),
                    target.getZ(),
                    SoundEvents.TOTEM_USE,
                    SoundSource.PLAYERS,
                    0.2F, 0.5F
            );

            // Spawn particles
            target.level().broadcastEntityEvent(target, (byte) 35);

            // Brief glowing effect (2 seconds) and other totem stuff
            target.removeAllEffects();

            target.addEffect(new MobEffectInstance(
                    MobEffects.REGENERATION,
                    900,
                    1      // level II = amplifier 1
            ));

            target.addEffect(new MobEffectInstance(
                    MobEffects.FIRE_RESISTANCE,
                    800,
                    0
            ));

            target.addEffect(new MobEffectInstance(
                    MobEffects.ABSORPTION,
                    100,
                    1
            ));
            target.addEffect(new MobEffectInstance(
                    MobEffects.GLOWING,
                    40,   // duration in ticks (40 ticks = 2 seconds)
                    0,
                    false,
                    false
            ));
        }
    }
}