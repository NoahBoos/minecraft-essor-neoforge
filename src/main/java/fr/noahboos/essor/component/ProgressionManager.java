package fr.noahboos.essor.component;

import net.minecraft.world.item.ItemStack;

public class ProgressionManager {
    public static void AddExperience(ItemStack item, float experience) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        data.SetCurrentExperience((float) Math.round((data.GetCurrentExperience() + experience) * 1000f) / 1000f);
    }

    public static void LevelUp(ItemStack item) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        while (data.GetCurrentExperience() > data.GetRequiredExperienceToLevelUp()) {
            data.SetLevel(data.GetLevel() + 1);
            data.SetCurrentExperience(data.GetCurrentExperience() - data.GetRequiredExperienceToLevelUp());
            data.SetRequiredExperienceToLevelUp(100 + (100 * data.GetLevel()));
            item.setDamageValue(0);
        }
    }
}