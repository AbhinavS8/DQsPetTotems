package net.dominosq.dqpetrespawn.init;

import com.mojang.serialization.Codec;
import net.dominosq.dqpetrespawn.DQPetRespawn;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DQPetRespawn.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> PET_HAS_CHARM =
            ATTACHMENT_TYPES.register("pet_has_charm",
                    () -> AttachmentType.builder(() -> false)    // default value = false
                            .serialize(Codec.BOOL)               // persist across world saves
                            .build()
            );
}
