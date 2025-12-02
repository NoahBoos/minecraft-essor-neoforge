package fr.noahboos.essor.event;

import fr.noahboos.essor.client.EssorKeyMappings;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class EssorKeyEventHandler {
    @SubscribeEvent
    public static void OnClientTick(ClientTickEvent.Post event) {
        if (EssorKeyMappings.OPEN_EQUIPMENT_GUI.consumeClick()) {
            System.out.println("Equipment GUI Opened");
        }
    }
}
