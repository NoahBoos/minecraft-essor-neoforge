package fr.noahboos.essor.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class EssorPlayerEventHandler {
    @SubscribeEvent
    public static void OnPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {

    }
}
