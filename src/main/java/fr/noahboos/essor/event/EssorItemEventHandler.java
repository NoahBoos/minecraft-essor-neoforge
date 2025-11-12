package fr.noahboos.essor.event;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.util.InventoryUtils;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class EssorItemEventHandler {
    @SubscribeEvent
    public static void OnItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            ItemStack crafted = event.getCrafting();
            if ((EquipmentLevelingData.UPGRADABLE_TOOLS_CLASSES.contains(crafted.getItem().getClass())
                    || EquipmentLevelingData.UPGRADABLE_WEAPON_CLASSES.contains(crafted.getItem().getClass())
                    || EquipmentLevelingData.UPGRADABLE_ARMOR_CLASSES.contains(crafted.getItem().getClass()))
                    && !crafted.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                crafted.set(
                        EssorDataComponents.EQUIPMENT_LEVELING_DATA.get(),
                        new EquipmentLevelingData()
                );
            }

            InventoryUtils.InitializeEquipmentLevelingDataOnInventoryItems(event.getEntity().getInventory());
        }
    }

    @SubscribeEvent
    public static void OnItemPickedUp(ItemEntityPickupEvent.Post event) {
        if (event.getPlayer().level().isClientSide()) {
            return;
        } else {
            InventoryUtils.InitializeEquipmentLevelingDataOnInventoryItems(event.getPlayer().getInventory());
        }
    }
}
