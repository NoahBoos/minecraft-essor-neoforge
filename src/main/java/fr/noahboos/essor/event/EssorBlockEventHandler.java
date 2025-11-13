package fr.noahboos.essor.event;

import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.registry.EssorRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

@EventBusSubscriber
public class EssorBlockEventHandler {
    @SubscribeEvent
    public static void OnBlockDropped(BlockDropsEvent event) {
        if (event.getBreaker() == null) return;
        if (event.getBreaker().level().isClientSide()) {
            return;
        } else {
            if (event.getBreaker() instanceof Player player) {
                ItemStack heldItem = player.getMainHandItem();
                Block block = event.getState().getBlock();
                EssorRegistry.ExperienceResult result = EssorRegistry.GetExperience(EssorRegistry.PRIMARY_ACTION_EXPERIENCE_TABLES, heldItem, BuiltInRegistries.BLOCK.getKey(block).toString());
                if (result.isRewardable()) {
                    int dropCount = event.getDrops().stream().mapToInt(itemEntity -> itemEntity.getItem().getCount()).sum();
                    float experience = result.experience() * dropCount;
                    ProgressionManager.AddExperience(heldItem, experience);
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {

        }
    }
}
