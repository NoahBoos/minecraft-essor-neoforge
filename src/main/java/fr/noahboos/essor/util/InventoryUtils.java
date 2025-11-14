package fr.noahboos.essor.util;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.challenge.ChallengeProgress;
import fr.noahboos.essor.component.challenge.ChallengesFactory;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class InventoryUtils {
    public static void InventorySync(ServerPlayer player) {
        player.containerMenu.broadcastChanges();

        int stateId = player.containerMenu.getStateId();

        // Hotbar (0-8)
        for (int i = 0; i <= 8; i++) {
            player.connection.send(new ClientboundContainerSetSlotPacket(-2, stateId, i, player.getInventory().getItem(i)));
        }

        // Inventaire principal (9-35)
        for (int i = 9; i <= 35; i++) {
            player.connection.send(new ClientboundContainerSetSlotPacket(-2, stateId, i, player.getInventory().getItem(i)));
        }

        // Armure (36-39)
        for (int i = 36; i <= 39; i++) {
            player.connection.send(new ClientboundContainerSetSlotPacket(-2, stateId, i, player.getInventory().getItem(i)));
        }

        // Main hand (slot sélectionné)
        player.connection.send(new ClientboundContainerSetSlotPacket(-2, stateId, player.getInventory().selected, player.getMainHandItem()));

        // Off-hand (slot 40)
        player.connection.send(new ClientboundContainerSetSlotPacket(-2, stateId, 40, player.getOffhandItem()));
    }

    public static void InitializeEquipmentLevelingDataOnInventoryItems(Inventory inventory) {
        for (ItemStack item : inventory.items) {
            if (EquipmentLevelingData.UPGRADABLE_ITEM_CLASSES.contains(item.getItem().getClass())) {
                if (!item.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                    item.set(
                        EssorDataComponents.EQUIPMENT_LEVELING_DATA.get(),
                        new EquipmentLevelingData()
                    );
                }

                ChallengesFactory.AssignChallenges(item);

                EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                if (data == null) return;
                data.SetPrestigeExperienceMultiplier((float) Math.round((data.GetPrestige() * 0.25) * 100f) / 100f);
                data.SetChallengeExperienceMultiplier(0.00f);
                for (ChallengeProgress challenge : data.GetChallenges().GetChallenges()) {
                    float challengeExperienceMultiplier = (float) Math.round((challenge.GetCurrentTier() * EquipmentLevelingData.challengeExperienceMultiplierStep) * 1000) / 1000;
                    data.SetChallengeExperienceMultiplier(data.GetChallengeExperienceMultiplier() + challengeExperienceMultiplier);
                }
                data.SetTotalExperienceMultiplier();
            }
        }

        for (ItemStack item : inventory.armor) {
            if (EquipmentLevelingData.UPGRADABLE_ARMOR_CLASSES.contains(item.getItem().getClass())) {
                if (!item.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                    item.set(
                        EssorDataComponents.EQUIPMENT_LEVELING_DATA.get(),
                        new EquipmentLevelingData()
                    );
                }

                ChallengesFactory.AssignChallenges(item);
            }
        }

        for (ItemStack item : inventory.offhand) {
            if (EquipmentLevelingData.UPGRADABLE_ITEM_CLASSES.contains(item.getItem().getClass())) {
                if (!item.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                    item.set(
                        EssorDataComponents.EQUIPMENT_LEVELING_DATA.get(),
                        new EquipmentLevelingData()
                    );
                }

                ChallengesFactory.AssignChallenges(item);

                EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                if (data == null) return;
                data.SetPrestigeExperienceMultiplier((float) Math.round((data.GetPrestige() * 0.25) * 100f) / 100f);
                data.SetChallengeExperienceMultiplier(0.00f);
                for (ChallengeProgress challenge : data.GetChallenges().GetChallenges()) {
                    float challengeExperienceMultiplier = (float) Math.round((challenge.GetCurrentTier() * EquipmentLevelingData.challengeExperienceMultiplierStep) * 1000) / 1000;
                    data.SetChallengeExperienceMultiplier(data.GetChallengeExperienceMultiplier() + challengeExperienceMultiplier);
                }
                data.SetTotalExperienceMultiplier();
            }
        }
    }
}
