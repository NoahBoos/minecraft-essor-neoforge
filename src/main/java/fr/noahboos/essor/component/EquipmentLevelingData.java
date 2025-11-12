package fr.noahboos.essor.components;

import fr.noahboos.essor.components.challenge.Challenges;
import net.minecraft.world.item.*;

import java.util.Objects;
import java.util.Set;

public class EquipmentLevelingData {
    private int prestige;
    private int requiredLevelToPrestige;
    private int level;
    private float totalExperienceMultiplier;
    private float prestigeExperienceMultiplier;
    private float challengeExperienceMultiplier;
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

    public static final Set<Class<?>> UPGRADABLE_TOOLS_CLASSES = Set.of(
        AxeItem.class, HoeItem.class, PickaxeItem.class, ShovelItem.class
    );

    public static final Set<Class<?>> UPGRADABLE_WEAPON_CLASSES = Set.of(
        AxeItem.class, BowItem.class, CrossbowItem.class, MaceItem.class,
        SwordItem.class, TridentItem.class
    );

    public static final Set<Class<?>> UPGRADABLE_ARMOR_CLASSES = Set.of(
        ArmorItem.class
    );

    public static final Set<Class<?>> UPGRADABLE_ITEM_CLASSES = Set.of(
        ArmorItem.class, AxeItem.class, BowItem.class, CrossbowItem.class,
        MaceItem.class, PickaxeItem.class, ShovelItem.class, SwordItem.class,
        TridentItem.class
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
