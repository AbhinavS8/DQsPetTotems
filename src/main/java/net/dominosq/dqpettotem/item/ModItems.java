package net.dominosq.dqpettotem.item;

import net.dominosq.dqpettotem.DQPetTotem;
import net.dominosq.dqpettotem.item.custom.PetTotemItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DQPetTotem.MODID);

    public static final DeferredItem<Item> PET_TOTEM_ITEM = ITEMS.register("pet_totem_item",
            () -> new PetTotemItem(new Item.Properties()
                    .stacksTo(16)
                    .rarity(Rarity.UNCOMMON)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}