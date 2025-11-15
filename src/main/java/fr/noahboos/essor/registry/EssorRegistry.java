package fr.noahboos.essor.registry;

import fr.noahboos.essor.component.challenge.ChallengeDefinition;
import fr.noahboos.essor.util.JsonLoader;
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
        "kill-entities"
    );

    private static Map<Integer, Map<String, Integer>> LoadEnchantmentTable(String file) {
        return JsonLoader.LoadRewardData(PATH_TO_ENCHANTMENT_TABLES + file + ".json");
    }

    private static Map<String, Float> LoadExperienceTable(String file) {
        return JsonLoader.LoadExperienceData(PATH_TO_EXPERIENCE_TABLES + file + ".json");
    }

    public static final Map<ArmorItem.Type, Map<Integer, Map<String, Integer>>> ARMOR_ENCHANTMENT_REWARD_TABLES = Map.of(
            ArmorItem.Type.HELMET, LoadEnchantmentTable("helmet"),
            ArmorItem.Type.CHESTPLATE, LoadEnchantmentTable("chestplate"),
            ArmorItem.Type.LEGGINGS, LoadEnchantmentTable("leggings"),
            ArmorItem.Type.BOOTS, LoadEnchantmentTable("boots")
    );

    public static final Map<Class<?>, Map<Integer, Map<String, Integer>>> NON_ARMOR_ITEM_ENCHANTMENT_REWARD_TABLES = Map.ofEntries(
            Map.entry(AxeItem.class, LoadEnchantmentTable("axe")),
            Map.entry(BowItem.class, LoadEnchantmentTable("bow")),
            Map.entry(CrossbowItem.class, LoadEnchantmentTable("crossbow")),
            Map.entry(HoeItem.class, LoadEnchantmentTable("hoe")),
            Map.entry(MaceItem.class, LoadEnchantmentTable("mace")),
            Map.entry(PickaxeItem.class, LoadEnchantmentTable("pickaxe")),
            Map.entry(ShieldItem.class, LoadEnchantmentTable("shield")),
            Map.entry(ShovelItem.class, LoadEnchantmentTable("shovel")),
            Map.entry(SwordItem.class, LoadEnchantmentTable("sword")),
            Map.entry(TridentItem.class, LoadEnchantmentTable("trident"))
    );

    public static Map<Integer, Map<String, Integer>> GetEnchantmentRewardTable(ItemStack item) {
        if (item.getItem() instanceof ArmorItem armorItem) {
            return EssorRegistry.ARMOR_ENCHANTMENT_REWARD_TABLES.get(armorItem.getType());
        } else {
            for (var entry : NON_ARMOR_ITEM_ENCHANTMENT_REWARD_TABLES.entrySet()) {
                if (entry.getKey().isAssignableFrom(item.getItem().getClass())) {
                    return EssorRegistry.NON_ARMOR_ITEM_ENCHANTMENT_REWARD_TABLES.get(entry.getKey());
                }
            }
        }
        return Map.of();
    }

    public static final Map<Class<?>, Map<String, Float>> PRIMARY_ACTION_EXPERIENCE_TABLES = Map.ofEntries(
            Map.entry(AxeItem.class, LoadExperienceTable("axe-breakable")),
            Map.entry(BowItem.class, LoadExperienceTable("bow-killable")),
            Map.entry(CrossbowItem.class, LoadExperienceTable("crossbow-killable")),
            Map.entry(HoeItem.class, LoadExperienceTable("hoe-breakable")),
            Map.entry(MaceItem.class, LoadExperienceTable("mace-killable")),
            Map.entry(PickaxeItem.class, LoadExperienceTable("pickaxe-breakable")),
            Map.entry(ShieldItem.class, LoadExperienceTable("shield-killable")),
            Map.entry(ShovelItem.class, LoadExperienceTable("shovel-breakable")),
            Map.entry(SwordItem.class, LoadExperienceTable("sword-killable")),
            Map.entry(TridentItem.class, LoadExperienceTable("trident-killable"))
    );

    public static final Map<Class<?>, Map<String, Float>> SECOND_ACTION_EXPERIENCE_TABLES = Map.ofEntries(
//            Map.entry("armor", LoadExperienceTable("armor"))
            Map.entry(AxeItem.class, LoadExperienceTable("axe-strippable")),
            Map.entry(HoeItem.class, LoadExperienceTable("hoe-tillable")),
            Map.entry(ShovelItem.class, LoadExperienceTable("shovel-diggable"))
    );

    public record ExperienceResult(boolean isRewardable, float experience) {}

    public static ExperienceResult GetExperience(Map<Class<?>, Map<String, Float>> tables, ItemStack item, String id) {
        for (var entry : tables.entrySet()) {
            if (entry.getKey().isAssignableFrom(item.getItem().getClass())) {
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
            ChallengeDefinition challengeDefinition = JsonLoader.LoadChallengeDefinition(fileName);
            if (challengeDefinition == null) {
                System.err.println("Failed to load challenge definition data from " + fileName + ".json.");
                continue;
            }
            CHALLENGE_DEFINITION_MAP.put(challengeDefinition.GetId(), challengeDefinition);
        }
    }
}
