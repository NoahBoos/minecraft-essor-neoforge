package fr.noahboos.essor.component;

import fr.noahboos.essor.component.challenge.Challenges;
import fr.noahboos.essor.util.E_EquipmentType;

import java.util.Objects;
import java.util.Set;

public class EquipmentLevelingData {
    public static final float DEFAULT_XP_CROUCHED = 0.25f;
    public static final float DEFAULT_XP_DAMAGE_DEALT = 0.75f;
    public static final float DEFAULT_XP_DAMAGE_TAKEN = 3.75f;
    public static final float DEFAULT_XP_SHIELD_BLOCK = 7.5f;
    public static final float DEFAULT_XP_ELYTRA_GLIDE = 2.0f;
    public static final float DEFAULT_XP_UNDER_WATER_BREATHING = 0.25f;
    public static final float DEFAULT_XP_MOVING_ON_SOUL_BLOCKS = 0.25f;

    private int prestige;
    private int requiredLevelToPrestige;
    public static int maxPrestige = 10;
    private int level;
    private float totalExperienceMultiplier;
    private float prestigeExperienceMultiplier;
    public static float prestigeExperienceMultiplierStep = 0.5f;
    private float challengeExperienceMultiplier;
    public static float challengeExperienceMultiplierStep = 0.25f;
    private int requiredExperienceToLevelUp;
    private float currentExperience;
    private Challenges challenges;

    public EquipmentLevelingData() {
        this.prestige = 0;
        this.requiredLevelToPrestige = 10;
        this.level = 0;
        this.totalExperienceMultiplier = 1f;
        this.prestigeExperienceMultiplier = 0f;
        this.challengeExperienceMultiplier = 0f;
        this.requiredExperienceToLevelUp = 100;
        this.currentExperience = 0;
        this.challenges = new Challenges();
    }

    public EquipmentLevelingData(
        int prestige,
        int requiredLevelToPrestige,
        int level,
        float totalExperienceMultiplier,
        float prestigeExperienceMultiplier,
        float challengeExperienceMultiplier,
        int requiredExperienceToLevelUp,
        float currentExperience,
        Challenges challenges
    ) {
        this.prestige = prestige;
        this.requiredLevelToPrestige = requiredLevelToPrestige;
        this.level = level;
        this.totalExperienceMultiplier = totalExperienceMultiplier;
        this.prestigeExperienceMultiplier = prestigeExperienceMultiplier;
        this.challengeExperienceMultiplier = challengeExperienceMultiplier;
        this.requiredExperienceToLevelUp = requiredExperienceToLevelUp;
        this.currentExperience = currentExperience;
        this.challenges = challenges;
    }

    public static final Set<E_EquipmentType> UPGRADABLE_ARMOR_CLASSES = Set.of(
        E_EquipmentType.HELMET, E_EquipmentType.CHESTPLATE, E_EquipmentType.LEGGINGS, E_EquipmentType.BOOTS,
        E_EquipmentType.SHIELD, E_EquipmentType.ELYTRA, E_EquipmentType.TURTLE_HELMET
    );

    public static final Set<E_EquipmentType> UPGRADABLE_ITEM_CLASSES = Set.of(
        E_EquipmentType.AXE, E_EquipmentType.BOOTS, E_EquipmentType.BOW, E_EquipmentType.CHESTPLATE,
        E_EquipmentType.CROSSBOW, E_EquipmentType.ELYTRA, E_EquipmentType.HELMET, E_EquipmentType.HOE,
        E_EquipmentType.LEGGINGS, E_EquipmentType.MACE, E_EquipmentType.PICKAXE, E_EquipmentType.SHOVEL,
        E_EquipmentType.SHIELD, E_EquipmentType.SWORD, E_EquipmentType.TRIDENT, E_EquipmentType.TURTLE_HELMET
    );

    public static final Set<E_EquipmentType> CHALLENGEABLE_ITEM_CLASSES = Set.of(
        E_EquipmentType.AXE, E_EquipmentType.BOW, E_EquipmentType.CROSSBOW, E_EquipmentType.HOE,
        E_EquipmentType.MACE, E_EquipmentType.PICKAXE, E_EquipmentType.SHOVEL, E_EquipmentType.SHIELD,
        E_EquipmentType.SWORD, E_EquipmentType.TRIDENT
    );

    public int GetPrestige() {
        return this.prestige;
    }
    public void SetPrestige(int prestige) {
        this.prestige = prestige;
    }

    public int GetRequiredLevelToPrestige() {
        return this.requiredLevelToPrestige;
    }
    public void SetRequiredLevelToPrestige(int requiredLevelToPrestige) {
        this.requiredLevelToPrestige = requiredLevelToPrestige;
    }

    public int GetLevel() {
        return level;
    }
    public void SetLevel(int level) {
        this.level = level;
    }

    public float GetTotalExperienceMultiplier() {
        return this.totalExperienceMultiplier;
    }
    public void SetTotalExperienceMultiplier() {
        this.totalExperienceMultiplier = 1.0f + this.prestigeExperienceMultiplier + this.challengeExperienceMultiplier;
    }

    public float GetPrestigeExperienceMultiplier() {
        return this.prestigeExperienceMultiplier;
    }

    public void SetPrestigeExperienceMultiplier(float prestigeExperienceMultiplier) {
        this.prestigeExperienceMultiplier = prestigeExperienceMultiplier;
    }

    public float GetChallengeExperienceMultiplier() {
        return this.challengeExperienceMultiplier;
    }

    public void SetChallengeExperienceMultiplier(float challengeExperienceMultiplier) {
        this.challengeExperienceMultiplier = challengeExperienceMultiplier;
    }

    public int GetRequiredExperienceToLevelUp() {
        return requiredExperienceToLevelUp;
    }

    public void SetRequiredExperienceToLevelUp(int requiredExperienceToLevelUp) {
        this.requiredExperienceToLevelUp = requiredExperienceToLevelUp;
    }

    public float GetCurrentExperience() {
        return currentExperience;
    }

    public void SetCurrentExperience(float currentExperience) {
        this.currentExperience = currentExperience;
    }

    public Challenges GetChallenges() {
        return this.challenges;
    }
    public void SetChallenges(Challenges challenges) {
        this.challenges = challenges;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.prestige,
            this.requiredLevelToPrestige,
            this.level,
            this.totalExperienceMultiplier,
            this.prestigeExperienceMultiplier,
            this.challengeExperienceMultiplier,
            this.requiredExperienceToLevelUp,
            this.currentExperience,
            this.challenges
        );
    }

    @Override public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else {
            return object instanceof EquipmentLevelingData obj
                && this.prestige == obj.prestige
                && this.requiredLevelToPrestige == obj.requiredLevelToPrestige
                && this.level == obj.level
                && this.totalExperienceMultiplier == obj.totalExperienceMultiplier
                && this.prestigeExperienceMultiplier == obj.prestigeExperienceMultiplier
                && this.challengeExperienceMultiplier == obj.challengeExperienceMultiplier
                && this.requiredExperienceToLevelUp == obj.requiredExperienceToLevelUp
                && this.currentExperience == obj.currentExperience
                && this.challenges == obj.challenges;
        }
    }
}
