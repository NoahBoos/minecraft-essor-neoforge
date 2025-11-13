package fr.noahboos.essor.component;

import fr.noahboos.essor.registry.EssorEnchantmentRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.joml.Random;

import java.util.Map;
import java.util.stream.Collectors;

public class ProgressionManager {
    public static void AddExperience(ItemStack item, float experience) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        data.SetCurrentExperience((float) Math.round((data.GetCurrentExperience() + experience) * 1000f) / 1000f);
    }

    public static void LevelUp(ItemStack item) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        while (data.GetCurrentExperience() > data.GetRequiredExperienceToLevelUp()) {
            data.SetLevel(data.GetLevel() + 1);
            data.SetCurrentExperience(data.GetCurrentExperience() - data.GetRequiredExperienceToLevelUp());
            data.SetRequiredExperienceToLevelUp(100 + (100 * data.GetLevel()));
            item.setDamageValue(0);
        }
    }

    public static void PrestigeUp(ItemStack item) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        while (data.GetLevel() > data.GetRequiredLevelToPrestige() && data.GetPrestige() < EquipmentLevelingData.maxPrestige) {
            data.SetPrestige(data.GetPrestige() + 1);
            data.SetLevel(data.GetLevel() - data.GetRequiredLevelToPrestige());
            data.SetPrestigeExperienceMultiplier((float) Math.round((data.GetPrestigeExperienceMultiplier() + EquipmentLevelingData.prestigeExperienceMultiplierStep) * 100f) / 100f);
            data.SetTotalExperienceMultiplier();
            data.SetRequiredExperienceToLevelUp(100 + (100 * data.GetLevel()));
            data.SetRequiredLevelToPrestige(10 + (10 * data.GetPrestige()));
        }
    }

    public static void ApplyEnchantment(Level level, Map<Integer, Map<String, Integer>> table, ItemStack item) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        Map<String, Integer> rewards = table.get(data.GetLevel());
        if (rewards == null || rewards.isEmpty()) return;
        Map<Holder<Enchantment>, Integer> enchantments = rewards.entrySet().stream().collect(Collectors.toMap(
                enchantment -> EssorEnchantmentRegistry.GetEnchantmentByID(enchantment.getKey(), level.registryAccess()),
                Map.Entry::getValue
        ));
        for (Map.Entry<Holder<Enchantment>, Integer> enchantment : enchantments.entrySet()) {
            Holder<Enchantment> holder = enchantment.getKey();
            if (item.getEnchantments().keySet().contains(holder)) {
                if (item.getEnchantments().getLevel(holder) < enchantment.getValue()) {
                    item.enchant(holder, enchantment.getValue());
                }
                return;
            }
        }
        Holder<Enchantment> enchantment = enchantments.keySet().stream().skip(new Random().nextInt(enchantments.size())).findFirst().orElse(null);
        if (enchantment != null) {
            int enchantLevel = enchantments.get(enchantment);
            item.enchant(enchantment, enchantLevel);
        }
    }
}