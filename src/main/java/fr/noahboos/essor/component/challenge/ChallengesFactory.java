package fr.noahboos.essor.component.challenge;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.registry.EssorRegistry;
import fr.noahboos.essor.util.EquipmentType;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;

public class ChallengesFactory {
    // TODO - Change this method behaviour, so instead of brute assigning challenges, it contextually assigns them to each item, depending on what challenges are missing.
    public static void AssignChallenges(ItemStack item) {
        if (!EquipmentLevelingData.CHALLENGEABLE_ITEM_CLASSES.contains(EquipmentType.GetEquipmentType(item))) return;

        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());

        if (data == null || (data.GetChallenges() != null && !data.GetChallenges().challenges.isEmpty())) return;

        Item type = item.getItem();

        switch (type) {
            case AxeItem axeItem -> data.SetChallenges(AddChallengeToAxe());
            case BowItem bowItem -> data.SetChallenges(AddChallengeToRangedWeapon());
            case CrossbowItem crossbowItem -> data.SetChallenges(AddChallengeToRangedWeapon());
            case HoeItem hoeItem -> data.SetChallenges(AddChallengeToHoe());
            case MaceItem maceItem -> data.SetChallenges(AddChallengeToWeapon());
            case ShovelItem shovelItem -> data.SetChallenges(AddChallengeToShovel());
            case ShieldItem shieldItem -> data.SetChallenges(AddChallengeToWeapon());
            case TridentItem tridentItem -> data.SetChallenges(AddChallengeToTrident());
            default -> {
                if (item.is(ItemTags.SWORDS)) {
                    data.SetChallenges(AddChallengeToWeapon());
                } else if (item.is(ItemTags.PICKAXES)) {
                    data.SetChallenges(AddChallengeToPickaxe());
                }
            }
        }
    }

    private static Challenges AddBossChallenges() {
        Challenges challenges = new Challenges();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillElderGuardian")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillEnderDragon")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillWarden")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillWither")));
        return challenges;
    }

    private static Challenges AddChallengeToWeapon() {
        Challenges challenges = AddBossChallenges();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillEntities")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillCreepers")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillCubes")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillHumans")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillSkeletons")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillSpiders")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillZombies")));
        return challenges;
    }

    private static Challenges AddChallengeToRangedWeapon() {
        Challenges challenges = AddChallengeToWeapon();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillFlyers")));
        return challenges;
    }

    private static Challenges AddChallengeToTrident() {
        Challenges challenges = AddChallengeToWeapon();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillGuardians")));
        return challenges;
    }

    private static Challenges AddChallengeToTool() {
        Challenges challenges = new Challenges();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakBlocks")));
        return challenges;
    }

    private static Challenges AddChallengeToAxe() {
        Challenges challenges = AddChallengeToTool();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakAcacia")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakBirch")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakCherry")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakCrimson")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDarkOak")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakJungle")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakMangrove")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakOak")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakPaleOak")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSpruce")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakWarped")));
        return challenges;
    }

    private static Challenges AddChallengeToPickaxe() {
        Challenges challenges = AddChallengeToTool();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakAncientDebris")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakCoalOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakCopperOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDiamondOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakEmeraldOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakGoldOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakIronOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakLapisOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakNetherQuartzOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakRedstoneOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakStone")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDeepslate")));
        return challenges;
    }

    private static Challenges AddChallengeToHoe() {
        Challenges challenges = AddChallengeToTool();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakNetherWart")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakShroomlight")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakWarpedWart")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:TillDirt")));
        return challenges;
    }

    private static Challenges AddChallengeToShovel() {
        Challenges challenges = AddChallengeToTool();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakClay")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDirt")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakGravel")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakMycelium")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakPodzol")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSand")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSnow")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSoulSand")));
        return challenges;
    }
}
