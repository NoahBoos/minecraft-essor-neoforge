package fr.noahboos.essor;

import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.event.*;
import fr.noahboos.essor.registry.EssorRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Essor.MODID)
public class Essor
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "essor";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Essor(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        EssorDataComponents.REGISTRAR.register(modEventBus);
        NeoForge.EVENT_BUS.register(EssorBlockEventHandler.class);
        NeoForge.EVENT_BUS.register(EssorCommandEventHandler.class);
        NeoForge.EVENT_BUS.register(EssorEntityEventHandler.class);
        NeoForge.EVENT_BUS.register(EssorItemEventHandler.class);
        NeoForge.EVENT_BUS.register(EssorPlayerEventHandler.class);
        NeoForge.EVENT_BUS.register(EssorTooltipEventHandler.class);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            EssorRegistry.InitializeChallengeDefinitionMap();
        });
    }
}
