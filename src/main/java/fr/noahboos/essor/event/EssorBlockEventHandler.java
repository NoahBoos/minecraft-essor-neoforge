package fr.noahboos.essor.event;

import fr.noahboos.essor.component.ActionBar;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.component.challenge.Challenges;
import fr.noahboos.essor.registry.EssorRegistry;
import fr.noahboos.essor.util.E_EquipmentType;
import fr.noahboos.essor.util.EquipmentType;
import fr.noahboos.essor.util.InventoryUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

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
                    ProgressionManager.HandleProgress(player, heldItem, experience);
                }
                Challenges.AttemptToLevelUpChallenges(heldItem, BuiltInRegistries.BLOCK.getKey(block).toString(), dropCount);
                InventoryUtils.InventorySync((ServerPlayer) player);
                ActionBar.DisplayXPCount((ServerPlayer) player, heldItem);
            }
        }
    }

    @SubscribeEvent
    public static void OnBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            if (event.getEntity() instanceof Player player) {
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offHandItem = player.getOffhandItem();
                Block block = event.getEntity().level().getBlockState(event.getPos()).getBlock();

                if (EquipmentType.GetEquipmentType(offHandItem) == E_EquipmentType.SHIELD && EquipmentType.GetEquipmentType(mainHandItem) == E_EquipmentType.AXE) return;

                EssorRegistry.ExperienceResult result = EssorRegistry.GetExperience(EssorRegistry.SECOND_ACTION_EXPERIENCE_TABLES, mainHandItem, BuiltInRegistries.BLOCK.getKey(block).toString());
                if (result.isRewardable()) {
                    ProgressionManager.HandleProgress(player, mainHandItem, result.experience());
                }
                Challenges.AttemptToLevelUpChallenges(mainHandItem, BuiltInRegistries.BLOCK.getKey(block).toString());
                InventoryUtils.InventorySync((ServerPlayer) player);
                ActionBar.DisplayXPCount((ServerPlayer) player, mainHandItem);
            }
        }
    }
}
