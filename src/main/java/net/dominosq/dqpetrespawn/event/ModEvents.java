package net.dominosq.dqpetrespawn.event;

import net.dominosq.dqpetrespawn.DQPetRespawn;
import net.dominosq.dqpetrespawn.init.ModAttachments;
import net.dominosq.dqpetrespawn.item.custom.PetCharmItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
    public static void onRightClickPet(PlayerInteractEvent.EntityInteractSpecific event) {

        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof PetCharmItem)) return;

        Player player = event.getEntity();
        Entity target = event.getTarget();

        if (!(target instanceof LivingEntity living)) return;

        // Must be a tameable animal
        if (!(living instanceof TamableAnimal tamable)) return;

        // Must be tamed
        if (!tamable.isTame()) return;

        // (Optional but recommended) Must be owned by this player
        if (!tamable.isOwnedBy(player)) {
            if (!player.level().isClientSide) {
                player.displayClientMessage(
                        Component.literal("You are not the owner of this pet."),
                        false
                );
            }
            return;
        }

        ((PetCharmItem) stack.getItem()).handlePetClick(stack, player, living);

    }

    @SubscribeEvent
    public static void onPetTakeDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        float damage = event.getAmount();
        float currentHealth = target.getHealth();

        if (!(target instanceof TamableAnimal pet)) return;
        if (!pet.isTame()) return;

        // get owner (may be null)
        if (pet.getOwner() == null) return;
        if (!(pet.getOwner() instanceof Player owner)) return;
        if (damage < currentHealth) return;

        if (pet.getData(ModAttachments.PET_HAS_CHARM.get())) {
            owner.displayClientMessage(
                    Component.literal("Your charm saved " + pet.getName().getString() + "!"),
                    false
            );
            event.setCanceled(true);      // cancel the damage event
            pet.setHealth(5.0F);
            pet.setData(ModAttachments.PET_HAS_CHARM.get(), false);

            pet.level().playSound(
                    null,                     // null = play for everyone nearby
                    pet.getX(),
                    pet.getY(),
                    pet.getZ(),
                    net.minecraft.sounds.SoundEvents.ENCHANTMENT_TABLE_USE,
                    net.minecraft.sounds.SoundSource.PLAYERS,
                    1.0F, 1.2F
            );

            // Spawn particles
            for (int i = 0; i < 20; i++) {
                double dx = (pet.level().random.nextDouble() - 0.5) * 0.5;
                double dy = pet.level().random.nextDouble() * 0.5 + 0.2;
                double dz = (pet.level().random.nextDouble() - 0.5) * 0.5;

                pet.level().addParticle(
                        net.minecraft.core.particles.ParticleTypes.HEART,
                        pet.getX() + dx,
                        pet.getY() + dy + 0.5,
                        pet.getZ() + dz,
                        0, 0, 0
                );
            }

            // Brief glowing effect (2 seconds)
            pet.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.GLOWING,
                    40,   // duration in ticks (40 ticks = 2 seconds)
                    0,
                    false,
                    false
            ));
            // TODO: TP to safe place logic?
        }
    }
}