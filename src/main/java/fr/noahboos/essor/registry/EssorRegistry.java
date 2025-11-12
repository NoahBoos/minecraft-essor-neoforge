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
        "break-block"
    );

    private static Map<Integer, Map<String, Integer>> LoadEnchantmentTable(String file) {
        return JsonLoader.LoadRewardData(PATH_TO_ENCHANTMENT_TABLES + file + ".json");
    }

    private static Map<String, Float> LoadExperienceTable(String file) {
        return JsonLoader.LoadExperienceData(PATH_TO_EXPERIENCE_TABLES + file + ".json");
    }

    public static final Map<ArmorItem.Type, Map<Integer, Map<String, Integer>>> ARMOR_ENCHANTMENT_REWARDS = Map.of(
            ArmorItem.Type.HELMET, LoadEnchantmentTable("helmet"),
            ArmorItem.Type.CHESTPLATE, LoadEnchantmentTable("chestplate"),
            ArmorItem.Type.LEGGINGS, LoadEnchantmentTable("leggings"),
            ArmorItem.Type.BOOTS, LoadEnchantmentTable("boots")
    );

    public static final Map<Class<?>, Map<Integer, Map<String, Integer>>> NON_ARMOR_ITEM_ENCHANTMENT_REWARDS = Map.ofEntries(
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

    public static final Map<String, Map<String, Float>> ITEM_EXPERIENCE_TABLES = Map.ofEntries(
            Map.entry("armor", LoadExperienceTable("armor")),
            Map.entry("axe-breakable", LoadExperienceTable("axe-breakable")),
            Map.entry("axe-strippable", LoadExperienceTable("axe-strippable")),
            Map.entry("bow-killable", LoadExperienceTable("bow-killable")),
            Map.entry("crossbow-killable", LoadExperienceTable("crossbow-killable")),
            Map.entry("hoe-breakable", LoadExperienceTable("hoe-breakable")),
            Map.entry("hoe-tillable", LoadExperienceTable("hoe-tillable")),
            Map.entry("mace-killable", LoadExperienceTable("mace-killable")),
            Map.entry("pickaxe-breakable", LoadExperienceTable("pickaxe-breakable")),
            Map.entry("shield-killable", LoadExperienceTable("shield-killable")),
            Map.entry("shovel-breakable", LoadExperienceTable("shovel-breakable")),
            Map.entry("shovel-diggable", LoadExperienceTable("shovel-diggable")),
            Map.entry("sword-killable", LoadExperienceTable("sword-killable")),
            Map.entry("trident-killable", LoadExperienceTable("trident-killable"))
    );

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
