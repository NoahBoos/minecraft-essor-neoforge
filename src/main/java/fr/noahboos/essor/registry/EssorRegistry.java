package fr.noahboos.essor.registry;

import fr.noahboos.essor.component.challenge.ChallengeDefinition;
import fr.noahboos.essor.util.E_EquipmentType;
import fr.noahboos.essor.util.EquipmentType;
import fr.noahboos.essor.util.JsonLoader;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EssorRegistry {
    private static final String PATH_TO_ENCHANTMENT_TABLES = "data/essor/enchantment_rewards/";
    private static final String PATH_TO_EXPERIENCE_TABLES = "data/essor/experience/";
    private static final List<String> CHALLENGE_FILE_NAME_LIST = List.of(
        "axe/break-acacia",
        "axe/break-birch",
        "axe/break-cherry",
        "axe/break-crimson",
        "axe/break-dark-oak",
        "axe/break-jungle",
        "axe/break-mangrove",
        "axe/break-oak",
        "axe/break-pale-oak",
        "axe/break-spruce",
        "axe/break-warped",
        "hoe/break-nether-wart",
        "hoe/break-shroomlight",
        "hoe/break-warped-wart",
        "hoe/till-dirt",
        "pickaxe/break-ancient-debris",
        "pickaxe/break-coal-ore",
        "pickaxe/break-copper-ore",
        "pickaxe/break-diamond-ore",
        "pickaxe/break-emerald-ore",
        "pickaxe/break-gold-ore",
        "pickaxe/break-iron-ore",
        "pickaxe/break-lapis-ore",
        "pickaxe/break-nether-quartz-ore",
        "pickaxe/break-redstone-ore",
        "pickaxe/break-stone",
        "pickaxe/break-deepslate",
        "shears/break-acacia-leaves",
        "shears/break-birch-leaves",
        "shears/break-cherry-leaves",
        "shears/break-dark-oak-leaves",
        "shears/break-jungle-leaves",
        "shears/break-mangrove-leaves",
        "shears/break-oak-leaves",
        "shears/break-pale-oak-leaves",
        "shears/break-spruce-leaves",
        "shears/shear-sheep",
        "shovel/break-clay",
        "shovel/break-dirt",
        "shovel/break-gravel",
        "shovel/break-mycelium",
        "shovel/break-podzol",
        "shovel/break-sand",
        "shovel/break-snow",
        "shovel/break-soul-sand",
        "weapons/kill-creepers",
        "weapons/kill-cubes",
        "weapons/kill-elder-guardian",
        "weapons/kill-ender-dragon",
        "weapons/kill-flyers",
        "weapons/kill-guardians",
        "weapons/kill-humans",
        "weapons/kill-skeletons",
        "weapons/kill-spiders",
        "weapons/kill-warden",
        "weapons/kill-wither",
        "weapons/kill-zombies",
        "break-blocks",
        "breathe-underwater",
        "fly-long-distance",
        "kill-entities",
        "take-damages"
    );

    private static Map<Integer, Map<String, Integer>> LoadEnchantmentTable(String file) {
        return JsonLoader.LoadRewardData(PATH_TO_ENCHANTMENT_TABLES + file + ".json");
    }

    private static Map<String, Float> LoadExperienceTable(String file) {
        return JsonLoader.LoadExperienceData(PATH_TO_EXPERIENCE_TABLES + file + ".json");
    }

    public static final Map<E_EquipmentType, Map<Integer, Map<String, Integer>>> ARMOR_ENCHANTMENT_REWARD_TABLES = Map.of(
            E_EquipmentType.HELMET, LoadEnchantmentTable("helmet"),
            E_EquipmentType.TURTLE_HELMET, LoadEnchantmentTable("helmet"),
            E_EquipmentType.CHESTPLATE, LoadEnchantmentTable("chestplate"),
            E_EquipmentType.ELYTRA, LoadEnchantmentTable("elytra"),
            E_EquipmentType.LEGGINGS, LoadEnchantmentTable("leggings"),
            E_EquipmentType.BOOTS, LoadEnchantmentTable("boots")
    );

    public static final Map<E_EquipmentType, Map<Integer, Map<String, Integer>>> NON_ARMOR_ITEM_ENCHANTMENT_REWARD_TABLES = Map.ofEntries(
            Map.entry(E_EquipmentType.AXE, LoadEnchantmentTable("axe")),
            Map.entry(E_EquipmentType.BOW, LoadEnchantmentTable("bow")),
            Map.entry(E_EquipmentType.CROSSBOW, LoadEnchantmentTable("crossbow")),
            Map.entry(E_EquipmentType.HOE, LoadEnchantmentTable("hoe")),
            Map.entry(E_EquipmentType.MACE, LoadEnchantmentTable("mace")),
            Map.entry(E_EquipmentType.PICKAXE, LoadEnchantmentTable("pickaxe")),
            Map.entry(E_EquipmentType.SHEARS, LoadEnchantmentTable("shears")),
            Map.entry(E_EquipmentType.SHIELD, LoadEnchantmentTable("shield")),
            Map.entry(E_EquipmentType.SHOVEL, LoadEnchantmentTable("shovel")),
            Map.entry(E_EquipmentType.SWORD, LoadEnchantmentTable("sword")),
            Map.entry(E_EquipmentType.TRIDENT, LoadEnchantmentTable("trident"))
    );

    public static Map<Integer, Map<String, Integer>> GetEnchantmentRewardTable(ItemStack item) {
        if (item.is(ItemTags.HEAD_ARMOR) || item.is(ItemTags.CHEST_ARMOR) || item.is(ItemTags.LEG_ARMOR) || item.is(ItemTags.FOOT_ARMOR) || item.is(Items.ELYTRA)) {
            return EssorRegistry.ARMOR_ENCHANTMENT_REWARD_TABLES.get(EquipmentType.GetEquipmentType(item));
        } else {
            for (var entry : NON_ARMOR_ITEM_ENCHANTMENT_REWARD_TABLES.entrySet()) {
                if (entry.getKey().equals(EquipmentType.GetEquipmentType(item))) {
                    return EssorRegistry.NON_ARMOR_ITEM_ENCHANTMENT_REWARD_TABLES.get(entry.getKey());
                }
            }
        }
        return Map.of();
    }

    public static final Map<E_EquipmentType, Map<String, Float>> PRIMARY_ACTION_EXPERIENCE_TABLES = Map.ofEntries(
            Map.entry(E_EquipmentType.AXE, LoadExperienceTable("axe-breakable")),
            Map.entry(E_EquipmentType.BOW, LoadExperienceTable("bow-killable")),
            Map.entry(E_EquipmentType.CROSSBOW, LoadExperienceTable("crossbow-killable")),
            Map.entry(E_EquipmentType.HOE, LoadExperienceTable("hoe-breakable")),
            Map.entry(E_EquipmentType.MACE, LoadExperienceTable("mace-killable")),
            Map.entry(E_EquipmentType.PICKAXE, LoadExperienceTable("pickaxe-breakable")),
            Map.entry(E_EquipmentType.SHEARS, LoadExperienceTable("shears-breakable")),
            Map.entry(E_EquipmentType.SHIELD, LoadExperienceTable("shield-killable")),
            Map.entry(E_EquipmentType.SHOVEL, LoadExperienceTable("shovel-breakable")),
            Map.entry(E_EquipmentType.SWORD, LoadExperienceTable("sword-killable")),
            Map.entry(E_EquipmentType.TRIDENT, LoadExperienceTable("trident-killable"))
    );

    public static final Map<E_EquipmentType, Map<String, Float>> SECOND_ACTION_EXPERIENCE_TABLES = Map.ofEntries(
            Map.entry(E_EquipmentType.AXE, LoadExperienceTable("axe-strippable")),
            Map.entry(E_EquipmentType.HOE, LoadExperienceTable("hoe-tillable")),
            Map.entry(E_EquipmentType.SHEARS, LoadExperienceTable("shears-trimmable")),
            Map.entry(E_EquipmentType.SHOVEL, LoadExperienceTable("shovel-diggable"))
    );

    public record ExperienceResult(boolean isRewardable, float experience) {}

    public static ExperienceResult GetExperience(Map<E_EquipmentType, Map<String, Float>> tables, ItemStack item, String id) {
        for (var entry : tables.entrySet()) {
            if (entry.getKey().equals(EquipmentType.GetEquipmentType(item))) {
                Float experience = entry.getValue().get(id);
                if (experience == null) return new ExperienceResult(false, 0f);
                return new ExperienceResult(true, experience);
            }
        }
        return new ExperienceResult(false, 0f);
    }

    public static final Map<String, ChallengeDefinition> CHALLENGE_DEFINITION_MAP = new HashMap<>();

    public static void InitializeChallengeDefinitionMap() {
        for (String fileName : CHALLENGE_FILE_NAME_LIST) {
            ChallengeDefinition challengeDefinition = new ChallengeDefinition(fileName);
            if (challengeDefinition == null) {
                System.err.println("Failed to load challenge definition data from " + fileName + ".json.");
                continue;
            }
            CHALLENGE_DEFINITION_MAP.put(challengeDefinition.GetId(), challengeDefinition);
        }
    }
}
