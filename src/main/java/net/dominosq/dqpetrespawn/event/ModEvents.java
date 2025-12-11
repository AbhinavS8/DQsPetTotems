package net.dominosq.dqpetrespawn.event;

import net.dominosq.dqpetrespawn.DQPetRespawn;
import net.dominosq.dqpetrespawn.item.custom.PetCharmItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
}