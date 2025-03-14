package net.dominosq.dqpetrespawn.item;

import net.dominosq.dqpetrespawn.DQPetRespawn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DQPetRespawn.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
