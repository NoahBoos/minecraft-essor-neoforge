package fr.noahboos.essor.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class EssorKeyMappings {
    public static KeyMapping OPEN_EQUIPMENT_GUI;

    public static void Initialize() {
        OPEN_EQUIPMENT_GUI = new KeyMapping("key.essor.open_equipment_gui", InputConstants.KEY_R, "key.categories.essor");
    }
}
