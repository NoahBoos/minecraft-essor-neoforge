package fr.noahboos.essor.event;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.registry.EssorRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.Map;

@EventBusSubscriber
public class EssorEntityEventHandler {
    @SubscribeEvent
    public static void OnEntityHurt(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player player) {
                float damage = event.getOriginalDamage() - event.getNewDamage();
                float armorExperience = damage * 3.75f;
                Iterable<ItemStack> armor = player.getArmorSlots();
                for (ItemStack item : armor) {
                    if (item.getItem() instanceof ArmorItem) {
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

                ItemStack offHandItem = player.getOffhandItem();
                EssorRegistry.ExperienceResult offHandResult = EssorRegistry.GetExperience(EssorRegistry.PRIMARY_ACTION_EXPERIENCE_TABLES, offHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                if (offHandResult.isRewardable()) {
                    ProgressionManager.AddExperience(offHandItem, offHandResult.experience());
                    ProgressionManager.LevelUp(player, offHandItem);
                    Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(offHandItem);
                    ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, offHandItem);
                    ProgressionManager.PrestigeUp(player, offHandItem);
                }
            }
        }
    }
}
