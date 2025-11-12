package fr.noahboos.essor;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

@EventBusSubscriber
public class EssorEventHandler {
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
        }
    }

    @SubscribeEvent
    public static void OnItemPickedUp(ItemEntityPickupEvent event) {

    }

    @SubscribeEvent
    public static void OnBlockBreak(BlockEvent.BreakEvent event) {

    }

    @SubscribeEvent
    public static void OnBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {

    }

    @SubscribeEvent
    public static void OnEntityHurt(LivingDamageEvent event) {

    }

    @SubscribeEvent
    public static void OnEntityDeath(LivingDeathEvent event) {

    }

    @SubscribeEvent
    public static void OnPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {

    }

    @SubscribeEvent
    public static void OnItemTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        ItemStack item = event.getItemStack();
        if (item.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
            EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());

            StringBuilder prestigeProgressBar = new StringBuilder();
            int prestigeSegments = 10;
            int prestigeFilledSegments = data.GetPrestige();
            prestigeProgressBar.append("§6★".repeat(Math.max(0, prestigeFilledSegments)));
            prestigeProgressBar.append("§7☆".repeat(Math.max(0, prestigeSegments - prestigeFilledSegments)));
            tooltip.add(Component.empty());
            tooltip.add(Component.literal(Component.translatable("tooltip.essor.prestige", data.GetPrestige()).getString() + " " + prestigeProgressBar.toString()));
            tooltip.add(Component.translatable("tooltip.essor.totalExperienceMultiplier", data.GetTotalExperienceMultiplier()));
            tooltip.add(Component.translatable("tooltip.essor.prestigeExperienceMultiplier", data.GetPrestigeExperienceMultiplier()));
            tooltip.add(Component.translatable("tooltip.essor.challengeExperienceMultiplier", data.GetChallengeExperienceMultiplier()));
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.essor.level", data.GetLevel(), data.GetCurrentExperience(), data.GetRequiredExperienceToLevelUp()));
            StringBuilder levelProgressBar = new StringBuilder();
            int levelSegments = 25;
            int levelFilledSegments = (int) (((float) data.GetCurrentExperience() / (float) data.GetRequiredExperienceToLevelUp()) * levelSegments);
            levelProgressBar.append("§a■".repeat(Math.max(0, levelFilledSegments)));
            levelProgressBar.append("§7□".repeat(Math.max(0, levelSegments - levelFilledSegments)));
            tooltip.add(Component.literal(levelProgressBar.toString()));
        }
    }
}