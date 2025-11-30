package fr.noahboos.essor.event;

import fr.noahboos.essor.component.ActionBar;
import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.component.challenge.Challenges;
import fr.noahboos.essor.registry.EssorRegistry;
import fr.noahboos.essor.util.InventoryUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Map;

public class EssorEntityEventHandler {
    @SubscribeEvent
    public static void OnEntityHurt(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            LivingEntity entity = event.getEntity();
            Entity attacker = event.getSource().getEntity();
            if (entity instanceof Player player) {
                float damage = event.getOriginalDamage() - event.getNewDamage();
                float armorExperience = damage * 3.75f;
                Iterable<ItemStack> armor = InventoryUtils.GetPlayerArmor(player.getInventory());
                for (ItemStack item : armor) {
                    if (item instanceof ItemStack) {
                        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                        if (data == null) continue;
                        ProgressionManager.AddExperience(item, armorExperience);
                        ProgressionManager.LevelUp(player, item);
                        Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(item);
                        ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, item);
                        ProgressionManager.PrestigeUp(player, item);
                    }
                }
                float shieldExperience = event.getBlockedDamage() * 7.5f;
                ItemStack heldItem = player.getMainHandItem();
                if (heldItem.getItem() instanceof ShieldItem) {
                    ProgressionManager.AddExperience(heldItem, shieldExperience);
                    ProgressionManager.LevelUp(player, heldItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(heldItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, heldItem);
                    ProgressionManager.PrestigeUp(player, heldItem);
                }

                ItemStack offHandItem = player.getOffhandItem();
                if (offHandItem.getItem() instanceof ShieldItem) {
                    ProgressionManager.AddExperience(offHandItem, shieldExperience);
                    ProgressionManager.LevelUp(player, offHandItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(offHandItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, offHandItem);
                    ProgressionManager.PrestigeUp(player, offHandItem);
                }
                InventoryUtils.InventorySync((ServerPlayer) player);
            }

            if (attacker instanceof Player player) {
                float experience = (float) (Math.ceil((event.getOriginalDamage() * 0.75f) * 2) / 2);

                ItemStack heldItem = player.getMainHandItem();
                if (!(heldItem.getItem() instanceof ShieldItem)) {
                    ProgressionManager.AddExperience(heldItem, experience);
                    ProgressionManager.LevelUp(player, heldItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(heldItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, heldItem);
                    ProgressionManager.PrestigeUp(player, heldItem);
                }


                ItemStack offHandItem = player.getOffhandItem();
                if (!(offHandItem.getItem() instanceof ShieldItem)) {
                    ProgressionManager.AddExperience(offHandItem, experience);
                    ProgressionManager.LevelUp(player, offHandItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(offHandItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, offHandItem);
                    ProgressionManager.PrestigeUp(player, offHandItem);
                }
                InventoryUtils.InventorySync((ServerPlayer) player);
            }
        }
    }

    @SubscribeEvent
    public static void OnEntityDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            LivingEntity entity = event.getEntity();
            Entity killer = event.getSource().getEntity();
            if (killer instanceof Player player) {
                ItemStack heldItem = player.getMainHandItem();
                EssorRegistry.ExperienceResult heldItemResult = EssorRegistry.GetExperience(EssorRegistry.PRIMARY_ACTION_EXPERIENCE_TABLES, heldItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                if (heldItemResult.isRewardable()) {
                    ProgressionManager.AddExperience(heldItem, heldItemResult.experience());
                    ProgressionManager.LevelUp(player, heldItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(heldItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, heldItem);
                    ProgressionManager.PrestigeUp(player, heldItem);
                }
                Challenges.AttemptToLevelUpChallenges(heldItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                ActionBar.DisplayXPCount((ServerPlayer) player, heldItem);

                ItemStack offHandItem = player.getOffhandItem();
                EssorRegistry.ExperienceResult offHandResult = EssorRegistry.GetExperience(EssorRegistry.PRIMARY_ACTION_EXPERIENCE_TABLES, offHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                if (offHandResult.isRewardable()) {
                    ProgressionManager.AddExperience(offHandItem, offHandResult.experience());
                    ProgressionManager.LevelUp(player, offHandItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(offHandItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, offHandItem);
                    ProgressionManager.PrestigeUp(player, offHandItem);
                }
                Challenges.AttemptToLevelUpChallenges(offHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                InventoryUtils.InventorySync((ServerPlayer) player);
            }
        }
    }

    @SubscribeEvent
    public static void OnLivingTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) return;

        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);

        if (player.isFallFlying()) {
            ProgressionManager.AddExperience(chestStack, EquipmentLevelingData.DEFAULT_XP_ELYTRA_GLIDE);
            ProgressionManager.LevelUp(player, chestStack);
            Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(chestStack);
            ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, chestStack);
            ProgressionManager.PrestigeUp(player, chestStack);
        }
    }
}
