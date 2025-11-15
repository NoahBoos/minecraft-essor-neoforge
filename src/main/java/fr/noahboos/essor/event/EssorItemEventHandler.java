package fr.noahboos.essor.event;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.challenge.ChallengesFactory;
import fr.noahboos.essor.util.EquipmentType;
import fr.noahboos.essor.util.InventoryUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class EssorItemEventHandler {
    @SubscribeEvent
    public static void OnItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            ItemStack crafted = event.getCrafting();
            if (EquipmentLevelingData.UPGRADABLE_ITEM_CLASSES.contains(EquipmentType.GetEquipmentType(crafted))
                    && !crafted.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                crafted.set(
                        EssorDataComponents.EQUIPMENT_LEVELING_DATA.get(),
                        new EquipmentLevelingData()
                );
                ChallengesFactory.AssignChallenges(crafted);
            }

            InventoryUtils.InitializeEquipmentLevelingDataOnInventoryItems(event.getEntity().getInventory());
            InventoryUtils.InventorySync((ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void OnItemPickedUp(ItemEntityPickupEvent.Post event) {
        if (event.getPlayer().level().isClientSide()) {
            return;
        } else {
            InventoryUtils.InitializeEquipmentLevelingDataOnInventoryItems(event.getPlayer().getInventory());
            InventoryUtils.InventorySync((ServerPlayer) event.getPlayer());
        }
    }
}
