package fr.noahboos.essor.component;

import net.minecraft.world.item.ItemStack;

public class ProgressionManager {
    public static void AddExperience(ItemStack item, float experience) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        data.SetCurrentExperience((float) Math.round((data.GetCurrentExperience() + experience) * 1000f) / 1000f);
    }
}