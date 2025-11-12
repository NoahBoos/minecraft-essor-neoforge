package fr.noahboos.essor.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class EssorEntityEventHandler {
    @SubscribeEvent
    public static void OnEntityHurt(LivingDamageEvent event) {

    }

    @SubscribeEvent
    public static void OnEntityDeath(LivingDeathEvent event) {

    }
}
