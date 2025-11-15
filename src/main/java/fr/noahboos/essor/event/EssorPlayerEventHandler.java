package fr.noahboos.essor.event;

import fr.noahboos.essor.util.InventoryUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class EssorPlayerEventHandler {
    @SubscribeEvent
    public static void OnPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            InventoryUtils.InitializeEquipmentLevelingDataOnInventoryItems(event.getEntity().getInventory());
        }
    }
}
