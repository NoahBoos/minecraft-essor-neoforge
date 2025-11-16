package fr.noahboos.essor.event;

import fr.noahboos.essor.command.EssorCommands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class EssorCommandEventHandler {
    @SubscribeEvent
    public static void OnRegisterCommands(RegisterCommandsEvent event) {
        EssorCommands.RegisterCommands(event.getDispatcher());
    }
}
