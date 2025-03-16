//package net.dominosq.dqpetrespawn.event;
//
//import net.dominosq.dqpetrespawn.DQPetRespawn;
//import net.dominosq.dqpetrespawn.block.ModBlocks;
//import net.dominosq.dqpetrespawn.block.entity.PetRespawnAnchorBlockEntity;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.TamableAnimal;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
//
//@EventBusSubscriber(modid = DQPetRespawn.MODID,bus = EventBusSubscriber.Bus.GAME)
//public class ModEvents {
//
//    @SubscribeEvent
//    public static void onPetDeath(LivingDeathEvent event) {
//        if (event.getEntity() instanceof TamableAnimal pet && pet.isTame()) {
//            Level level = pet.level();
//
//            // Find nearest respawn block
//            BlockPos respawnBlockPos = findNearestRespawnBlock(level, pet.blockPosition());
//            if (respawnBlockPos != null) {
//                BlockEntity blockEntity = level.getBlockEntity(respawnBlockPos);
//                if (blockEntity instanceof PetRespawnAnchorBlockEntity be) {
//                    // Save multiple pets' data
//                    be.addPetData(pet);
//
//                    // Schedule respawn after 5 seconds (100 ticks)
//                    level.scheduleTick(respawnBlockPos, be.getBlockState().getBlock(), 100);
//                }
//            }
//        }
//    }
//
//    private static BlockPos findNearestRespawnBlock(Level level, BlockPos petPos) {
//        int radius = 10;
//        for (BlockPos pos : BlockPos.betweenClosed(petPos.offset(-radius, -radius, -radius), petPos.offset(radius, radius, radius))) {
//            if (level.getBlockState(pos).is(ModBlocks.PET_RESPAWN_ANCHOR_BLOCK.get())) {
//                return pos;
//            }
//        }
//        return null;
//    }
//
//
//}
