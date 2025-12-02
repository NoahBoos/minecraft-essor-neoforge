package fr.noahboos.essor.event;

import fr.noahboos.essor.client.EssorKeyMappings;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class EssorKeyRegistrationEventHandler {
    @SubscribeEvent
    public static void OnRegisterKeys(RegisterKeyMappingsEvent event) {
        EssorKeyMappings.Initialize();
        event.register(EssorKeyMappings.OPEN_EQUIPMENT_GUI);
    }
}
