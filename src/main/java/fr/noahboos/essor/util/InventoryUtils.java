package fr.noahboos.essor.util;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.challenge.ChallengeProgress;
import fr.noahboos.essor.component.challenge.ChallengesFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {
    public static void InventorySync(ServerPlayer player) {
        player.containerMenu.broadcastChanges();
        player.inventoryMenu.broadcastChanges();
    }

    public static void InitializeEquipmentLevelingDataOnInventoryItems(Inventory inventory) {
        for (ItemStack item : inventory.getNonEquipmentItems()) {
            if (EquipmentLevelingData.UPGRADABLE_ITEM_CLASSES.contains(EquipmentType.GetEquipmentType(item))) {
                if (!item.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                    item.set(
                        EssorDataComponents.EQUIPMENT_LEVELING_DATA.get(),
                        new EquipmentLevelingData()
                    );
                }

                ChallengesFactory.AssignChallenges(item);

                EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                if (data == null) return;
                data.SetPrestigeExperienceMultiplier((float) Math.round((data.GetPrestige() * EquipmentLevelingData.prestigeExperienceMultiplierStep) * 100f) / 100f);
                data.SetChallengeExperienceMultiplier(0.00f);
                for (ChallengeProgress challenge : data.GetChallenges().GetChallenges()) {
                    float challengeExperienceMultiplier = (float) Math.round((challenge.GetCurrentTier() * EquipmentLevelingData.challengeExperienceMultiplierStep) * 1000) / 1000;
                    data.SetChallengeExperienceMultiplier(data.GetChallengeExperienceMultiplier() + challengeExperienceMultiplier);
                }
                data.SetTotalExperienceMultiplier();
            }
        }

        for (ItemStack item : InventoryUtils.GetPlayerArmor(inventory)) {
            if (EquipmentLevelingData.UPGRADABLE_ARMOR_CLASSES.contains(EquipmentType.GetEquipmentType(item))) {
                if (!item.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                    item.set(
                        EssorDataComponents.EQUIPMENT_LEVELING_DATA.get(),
                        new EquipmentLevelingData()
                    );
                }

                ChallengesFactory.AssignChallenges(item);

                EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                if (data == null) return;
                data.SetPrestigeExperienceMultiplier((float) Math.round((data.GetPrestige() * EquipmentLevelingData.prestigeExperienceMultiplierStep) * 100f) / 100f);
                data.SetChallengeExperienceMultiplier(0.00f);
                for (ChallengeProgress challenge : data.GetChallenges().GetChallenges()) {
                    float challengeExperienceMultiplier = (float) Math.round((challenge.GetCurrentTier() * EquipmentLevelingData.challengeExperienceMultiplierStep) * 1000) / 1000;
                    data.SetChallengeExperienceMultiplier(data.GetChallengeExperienceMultiplier() + challengeExperienceMultiplier);
                }
                data.SetTotalExperienceMultiplier();
            }
        }

        ItemStack offhand = inventory.getItem(40);
        if (EquipmentLevelingData.UPGRADABLE_ITEM_CLASSES.contains(EquipmentType.GetEquipmentType(offhand))) {
            if (!offhand.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                offhand.set(
                    EssorDataComponents.EQUIPMENT_LEVELING_DATA.get(),
                    new EquipmentLevelingData()
                );
            }

            ChallengesFactory.AssignChallenges(offhand);

            EquipmentLevelingData data = offhand.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
            if (data == null) return;
            data.SetPrestigeExperienceMultiplier((float) Math.round((data.GetPrestige() * EquipmentLevelingData.prestigeExperienceMultiplierStep) * 100f) / 100f);
            data.SetChallengeExperienceMultiplier(0.00f);
            for (ChallengeProgress challenge : data.GetChallenges().GetChallenges()) {
                float challengeExperienceMultiplier = (float) Math.round((challenge.GetCurrentTier() * EquipmentLevelingData.challengeExperienceMultiplierStep) * 1000) / 1000;
                data.SetChallengeExperienceMultiplier(data.GetChallengeExperienceMultiplier() + challengeExperienceMultiplier);
            }
            data.SetTotalExperienceMultiplier();
        }
    }

    public static List<ItemStack> GetPlayerArmor(Inventory inventory) {
        List<ItemStack> armor = new ArrayList<>();
        armor.add(inventory.getItem(36));
        armor.add(inventory.getItem(37));
        armor.add(inventory.getItem(38));
        armor.add(inventory.getItem(39));
        return armor;
    }
}
