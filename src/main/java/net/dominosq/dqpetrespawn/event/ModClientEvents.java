package net.dominosq.dqpetrespawn.event;

import net.dominosq.dqpetrespawn.DQPetRespawn;
import net.dominosq.dqpetrespawn.init.ModAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;

public class ModClientEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ModClientEvents::onRenderTotemPetNameTag);
    }

    public static void onRenderTotemPetNameTag(RenderNameTagEvent event) {
        Entity entity = event.getEntity();

        if (!entity.getData(ModAttachments.PET_HAS_TOTEM.get())) {
            return;
        }
        event.setCanRender(TriState.TRUE);

        Component name = entity.getDisplayName()
                .copy()
                .withStyle(ChatFormatting.GOLD);

        event.setContent(name);


    }
}
