package fr.noahboos.essor.component;

import fr.noahboos.essor.component.challenge.ChallengeDefinition;
import fr.noahboos.essor.component.challenge.ChallengeProgress;
import fr.noahboos.essor.registry.EssorEnchantmentRegistry;
import fr.noahboos.essor.registry.EssorRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.joml.Random;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProgressionManager {
    public static void HandleProgress(Player player, ItemStack item, float experience) {
        AddExperience(item, experience);
        LevelUp(player, item);
        PrestigeUp(player, item);
    }

    public static void AddExperience(ItemStack item, float experience) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        data.SetCurrentExperience((float) Math.round((data.GetCurrentExperience() + (experience * data.GetTotalExperienceMultiplier())) * 1000f) / 1000f);
    }

    public static void LevelUp(Player player, ItemStack item) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        while (data.GetCurrentExperience() >= data.GetRequiredExperienceToLevelUp()) {
            data.SetLevel(data.GetLevel() + 1);
            data.SetCurrentExperience(data.GetCurrentExperience() - data.GetRequiredExperienceToLevelUp());
            data.SetRequiredExperienceToLevelUp(100 + (100 * data.GetLevel()));
            Map<Integer, Map<String, Integer>> enchantmentRewardTable = EssorRegistry.GetEnchantmentRewardTable(item);
            ProgressionManager.ApplyEnchantment(player.level(), enchantmentRewardTable, item);
            item.setDamageValue(0);
            player.displayClientMessage(Component.translatable("chat.essor.levelUpMessage", item.getDisplayName(), data.GetLevel()), false);
        }
    }

    public static void PrestigeUp(Player player, ItemStack item) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        while (data.GetLevel() >= data.GetRequiredLevelToPrestige() && data.GetPrestige() < EquipmentLevelingData.maxPrestige) {
            data.SetPrestige(data.GetPrestige() + 1);
            data.SetLevel(data.GetLevel() - data.GetRequiredLevelToPrestige());
            data.SetPrestigeExperienceMultiplier((float) Math.round((data.GetPrestigeExperienceMultiplier() + EquipmentLevelingData.prestigeExperienceMultiplierStep) * 100f) / 100f);
            data.SetTotalExperienceMultiplier();
            data.SetRequiredExperienceToLevelUp(100 + (100 * data.GetLevel()));
            data.SetRequiredLevelToPrestige(10 + (10 * data.GetPrestige()));
            player.displayClientMessage(Component.translatable("chat.essor.prestigeMessage", item.getDisplayName(), data.GetPrestige()), false);
        }
    }

    public static void ApplyEnchantment(Level level, Map<Integer, Map<String, Integer>> table, ItemStack item) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        Map<String, Integer> rewards = table.get(data.GetLevel());
        if (rewards == null || rewards.isEmpty()) return;
        Map<Holder<Enchantment>, Integer> enchantments = rewards.entrySet().stream().map(enchantment -> {
            Holder<Enchantment> enchantmentHolder = EssorEnchantmentRegistry.GetEnchantmentByID(enchantment.getKey(), level.registryAccess());
            return enchantmentHolder == null ? null : Map.entry(enchantmentHolder, enchantment.getValue());
        }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (enchantments.isEmpty()) return;
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
        if (enchantment != null && !item.getEnchantments().keySet().contains(enchantment)) {
            int enchantLevel = enchantments.get(enchantment);
            item.enchant(enchantment, enchantLevel);
        }
    }

    public static void IncrementChallenge(ChallengeProgress challenge, ChallengeDefinition definition, int progress) {
        if (challenge.GetCurrentTier() < definition.GetMaximumTier()) {
            challenge.SetProgress(challenge.GetProgress() + progress);
        }
    }

    public static void LevelUpChallenge(ItemStack item, ChallengeProgress challenge, ChallengeDefinition definition) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        if (challenge.GetCurrentTier() >= definition.GetMaximumTier()) return;
        if (challenge.GetProgress() >= definition.GetThresholds().get(challenge.GetCurrentTier())) {
            challenge.SetCurrentTier(challenge.GetCurrentTier() + 1);
            challenge.SetProgress(0);
            data.SetChallengeExperienceMultiplier(data.GetChallengeExperienceMultiplier() + EquipmentLevelingData.challengeExperienceMultiplierStep);
            data.SetTotalExperienceMultiplier();
        }
    }
}