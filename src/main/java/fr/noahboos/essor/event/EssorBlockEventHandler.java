package fr.noahboos.essor.event;

import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.component.challenge.Challenges;
import fr.noahboos.essor.registry.EssorRegistry;
import fr.noahboos.essor.util.InventoryUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.Map;

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
                int dropCount = event.getDrops().stream().mapToInt(itemEntity -> itemEntity.getItem().getCount()).sum();
                if (result.isRewardable()) {
                    float experience = result.experience() * dropCount;
                    ProgressionManager.AddExperience(heldItem, experience);
                    ProgressionManager.LevelUp(player, heldItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(heldItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, heldItem);
                    ProgressionManager.PrestigeUp(player, heldItem);
                }
                Challenges.AttemptToLevelUpChallenges(heldItem, BuiltInRegistries.BLOCK.getKey(block).toString(), dropCount);
                InventoryUtils.InventorySync((ServerPlayer) player);
            }
        }
    }

    @SubscribeEvent
    public static void OnBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            if (event.getEntity() instanceof Player player) {
                ItemStack heldItem = player.getMainHandItem();
                Block block = event.getEntity().level().getBlockState(event.getPos()).getBlock();
                EssorRegistry.ExperienceResult result = EssorRegistry.GetExperience(EssorRegistry.SECOND_ACTION_EXPERIENCE_TABLES, heldItem, BuiltInRegistries.BLOCK.getKey(block).toString());
                if (result.isRewardable()) {
                    ProgressionManager.AddExperience(heldItem, result.experience());
                    ProgressionManager.LevelUp(player, heldItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(heldItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, heldItem);
                    ProgressionManager.PrestigeUp(player, heldItem);
                }
                Challenges.AttemptToLevelUpChallenges(heldItem, BuiltInRegistries.BLOCK.getKey(block).toString());
                InventoryUtils.InventorySync((ServerPlayer) player);
            }
        }
    }
}
