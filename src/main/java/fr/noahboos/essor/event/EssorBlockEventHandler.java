package fr.noahboos.essor.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber
public class EssorBlockEventHandler {
    @SubscribeEvent
    public static void OnBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer().level().isClientSide()) {
            return;
        } else {

        }
    }

    @SubscribeEvent
    public static void OnBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {

        }
    }
}
