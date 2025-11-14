package fr.noahboos.essor.event;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.challenge.ChallengeDefinition;
import fr.noahboos.essor.registry.EssorRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber
public class EssorTooltipEventHandler {
    @SubscribeEvent
    public static void OnItemTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        ItemStack item = event.getItemStack();
        if (item.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
            EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());

            StringBuilder prestigeProgressBar = new StringBuilder();
            int prestigeFilledSegments = data.GetPrestige();
            prestigeProgressBar.append("§6★".repeat(Math.max(0, prestigeFilledSegments)));
            prestigeProgressBar.append("§7☆".repeat(Math.max(0, EquipmentLevelingData.maxPrestige - prestigeFilledSegments)));
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
            if (EquipmentLevelingData.CHALLENGEABLE_ITEM_CLASSES.contains(item.getItem().getClass())) {
                if (Screen.hasShiftDown()) {
                    data.GetChallenges().challenges.forEach(challenge -> {
                        ChallengeDefinition definition = EssorRegistry.CHALLENGE_DEFINITION_MAP.get(challenge.GetId());
                        tooltip.add(Component.empty());
                        StringBuilder challengeProgressBar = new StringBuilder();
                        challengeProgressBar.append("§a■".repeat(Math.max(0, challenge.GetCurrentTier())));
                        challengeProgressBar.append("§7□".repeat(Math.max(0, definition.GetMaximumTier() - challenge.GetCurrentTier())));
                        tooltip.add(Component.translatable(challenge.GetId().replace(":", "."), challenge.GetProgress(), definition.GetThresholds().get(challenge.GetCurrentTier())).append(Component.literal(" - ")).append(Component.literal(challengeProgressBar.toString())));
                    });
                } else {
                    tooltip.add(Component.translatable("translation.holdToSeeChallenges"));
                }
            }
        }
    }
}
