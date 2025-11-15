package fr.noahboos.essor.util;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;

public class EquipmentType {
    public static  E_EquipmentType GetEquipmentType(ItemStack item) {
        if (item.is(ItemTags.AXES)) return E_EquipmentType.AXE;
        if (item.is(ItemTags.CHEST_ARMOR)) return E_EquipmentType.CHESTPLATE;
        if (item.is(ItemTags.FOOT_ARMOR)) return E_EquipmentType.BOOTS;
        if (item.is(ItemTags.HEAD_ARMOR)) return E_EquipmentType.HELMET;
        if (item.is(ItemTags.HOES)) return E_EquipmentType.HOE;
        if (item.is(ItemTags.LEG_ARMOR)) return E_EquipmentType.LEGGINGS;
        if (item.is(ItemTags.SHOVELS)) return E_EquipmentType.SHOVEL;
        if (item.is(ItemTags.PICKAXES)) return E_EquipmentType.PICKAXE;
        if (item.is(ItemTags.SWORDS)) return E_EquipmentType.SWORD;

        switch (item.getItem()) {
            case BowItem bowItem -> {
                return E_EquipmentType.BOW;
            }
            case CrossbowItem crossbowItem -> {
                return E_EquipmentType.CROSSBOW;
            }
            case MaceItem maceItem -> {
                return E_EquipmentType.MACE;
            }
            case ShieldItem shieldItem -> {
                return E_EquipmentType.SHIELD;
            }
            case TridentItem tridentItem -> {
                return E_EquipmentType.TRIDENT;
            }
            default -> { }
        }

        return E_EquipmentType.OTHER;
    }
}
