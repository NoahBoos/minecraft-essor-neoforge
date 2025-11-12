package fr.noahboos.essor.event;

import fr.noahboos.essor.util.InventoryUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class EssorPlayerEventHandler {
    @SubscribeEvent
    public static void OnPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        InventoryUtils.InitializeEquipmentLevelingDataOnInventoryItems(event.getEntity().getInventory());
    }
}
