package fr.noahboos.essor.registry;

import fr.noahboos.essor.util.JsonLoader;
import net.minecraft.world.item.*;

import java.util.Map;

public class EssorRegistry {
    private static final String PATH_TO_ENCHANTMENT_TABLES = "data/essor/enchantment/";
    private static final String PATH_TO_EXPERIENCE_TABLES = "data/essor/experience/";

    private static Map<Integer, Map<String, Integer>> LoadEnchantmentTable(String file) {
        return JsonLoader.LoadRewardData(PATH_TO_ENCHANTMENT_TABLES + file + ".json");
    }

    private static Map<String, Float> LoadExperienceTable(String file) {
        return JsonLoader.LoadExperienceData(PATH_TO_EXPERIENCE_TABLES + file + ".json");
    }

    public static final Map<ArmorItem.Type, Map<Integer, Map<String, Integer>>> ARMOR_ENCHANTMENT_REWARDS = Map.of(
            ArmorItem.Type.HELMET, LoadEnchantmentTable("HELMER"),
            ArmorItem.Type.CHESTPLATE, LoadEnchantmentTable("CHESTPLATE"),
            ArmorItem.Type.LEGGINGS, LoadEnchantmentTable("LEGGINGS"),
            ArmorItem.Type.BOOTS, LoadEnchantmentTable("BOOTS")
    );

    public static final Map<Class<?>, Map<Integer, Map<String, Integer>>> NON_ARMOR_ITEM_ENCHANTMENT_REWARDS = Map.ofEntries(
            Map.entry(AxeItem.class, LoadEnchantmentTable("AXE")),
            Map.entry(BowItem.class, LoadEnchantmentTable("BOW")),
            Map.entry(CrossbowItem.class, LoadEnchantmentTable("CROSSBOW")),
            Map.entry(HoeItem.class, LoadEnchantmentTable("HOE")),
            Map.entry(MaceItem.class, LoadEnchantmentTable("MACE")),
            Map.entry(PickaxeItem.class, LoadEnchantmentTable("PICKAXE")),
            Map.entry(ShieldItem.class, LoadEnchantmentTable("SHIELD")),
            Map.entry(ShovelItem.class, LoadEnchantmentTable("SHOVEL")),
            Map.entry(SwordItem.class, LoadEnchantmentTable("SWORD")),
            Map.entry(TridentItem.class, LoadEnchantmentTable("TRIDENT"))
    );

    public static final Map<String, Map<String, Float>> ITEM_EXPERIENCE_TABLES = Map.ofEntries(
            Map.entry("armor", LoadExperienceTable("ARMOR")),
            Map.entry("axe-breakable", LoadExperienceTable("AXE_BREAKABLE")),
            Map.entry("axe-strippable", LoadExperienceTable("AXE_STRIPPABLE")),
            Map.entry("bow-killable", LoadExperienceTable("BOW_KILLABLE")),
            Map.entry("crossbow-killable", LoadExperienceTable("CROSSBOW_KILLABLE")),
            Map.entry("hoe-breakable", LoadExperienceTable("HOE_BREAKABLE")),
            Map.entry("hoe-tillable", LoadExperienceTable("HOE_TILLABLE")),
            Map.entry("mace-killable", LoadExperienceTable("MACE_KILLABLE")),
            Map.entry("pickaxe-breakable", LoadExperienceTable("PICKAXE_BREAKABLE")),
            Map.entry("shield-killable", LoadExperienceTable("SHIELD_KILLABLE")),
            Map.entry("shovel-breakable", LoadExperienceTable("SHOVEL_BREAKABLE")),
            Map.entry("shovel-diggable", LoadExperienceTable("SHOVEL_DIGGABLE")),
            Map.entry("sword-killable", LoadExperienceTable("SWORD_KILLABLE")),
            Map.entry("trident-killable", LoadExperienceTable("TRIDENT_KILLABLE"))
    );
}
