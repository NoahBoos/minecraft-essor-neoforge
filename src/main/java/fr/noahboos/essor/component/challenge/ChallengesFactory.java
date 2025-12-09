package fr.noahboos.essor.component.challenge;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.registry.EssorRegistry;
import fr.noahboos.essor.util.E_EquipmentType;
import fr.noahboos.essor.util.EquipmentType;
import net.minecraft.world.item.*;

public class ChallengesFactory {
    public static void AssignChallenges(ItemStack item) {
        if (!EquipmentLevelingData.CHALLENGEABLE_ITEM_CLASSES.contains(EquipmentType.GetEquipmentType(item))) return;

        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());

        if (data == null) return;
        Challenges challengesToAdd = null;

        E_EquipmentType equipmentType = EquipmentType.GetEquipmentType(item);

        switch (equipmentType) {
            case E_EquipmentType.AXE -> challengesToAdd = AddChallengeToAxe();
            case E_EquipmentType.HELMET, E_EquipmentType.TURTLE_HELMET -> challengesToAdd = AddChallengeToHelmet();
            case E_EquipmentType.ELYTRA -> challengesToAdd = AddChallengeToElytra();
            case E_EquipmentType.CHESTPLATE, E_EquipmentType.LEGGINGS, E_EquipmentType.BOOTS -> challengesToAdd = AddChallengeToArmor();
            case E_EquipmentType.BOW, E_EquipmentType.CROSSBOW -> challengesToAdd = AddChallengeToRangedWeapon();
            case E_EquipmentType.HOE -> challengesToAdd = AddChallengeToHoe();
            case E_EquipmentType.MACE, E_EquipmentType.SHIELD, E_EquipmentType.SWORD -> challengesToAdd = AddChallengeToWeapon();
            case E_EquipmentType.PICKAXE -> challengesToAdd = AddChallengeToPickaxe();
            case E_EquipmentType.SHEARS -> challengesToAdd = AddChallengeToShears();
            case E_EquipmentType.SHOVEL -> challengesToAdd = AddChallengeToShovel();
            case E_EquipmentType.TRIDENT -> challengesToAdd = AddChallengeToTrident();
        }

        if (challengesToAdd != null) {
            Challenges currentChallenges = data.GetChallenges();
            if (currentChallenges == null || currentChallenges.GetChallenges().isEmpty()) {
                data.SetChallenges(challengesToAdd);
            } else {
                for (ChallengeProgress challengeToAdd : challengesToAdd.GetChallenges()) {
                    boolean exists = false;
                    for (ChallengeProgress challenge : currentChallenges.GetChallenges()) {
                        if (challenge.GetId().equals(challengeToAdd.GetId())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        currentChallenges.challenges.add(challengeToAdd);
                    }
                }
            }
        }
    }

    private static Challenges AddBossChallenges() {
        Challenges challenges = new Challenges();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillEnderDragon")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillElderGuardian")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillWither")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillWarden")));
        return challenges;
    }

    private static Challenges AddChallengeToArmor() {
        Challenges challenges = new Challenges();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:TakeDamages")));
        return challenges;
    }

    private static Challenges AddChallengeToHelmet() {
        Challenges challenges = AddChallengeToArmor();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreatheUnderwater")));
        return challenges;
    }

    private static Challenges AddChallengeToElytra() {
        Challenges challenges = AddChallengeToArmor();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:FlyLongDistanceWithTheElytra")));
        return challenges;
    }

    private static Challenges AddChallengeToWeapon() {
        Challenges challenges = new Challenges();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:KillEntities")));
        challenges.challenges.addAll(AddBossChallenges().GetChallenges());
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
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDarkOak")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakJungle")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakMangrove")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakOak")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakPaleOak")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSpruce")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakCrimson")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakWarped")));
        return challenges;
    }

    private static Challenges AddChallengeToPickaxe() {
        Challenges challenges = AddChallengeToTool();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakStone")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDeepslate")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakCoalOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakCopperOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakIronOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakGoldOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakRedstoneOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakLapisOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDiamondOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakEmeraldOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakNetherQuartzOre")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakAncientDebris")));
        return challenges;
    }

    private static Challenges AddChallengeToHoe() {
        Challenges challenges = AddChallengeToTool();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:TillDirt")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakShroomlight")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakNetherWart")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakWarpedWart")));
        return challenges;
    }

    private static Challenges AddChallengeToShears() {
        Challenges challenges = AddChallengeToTool();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:ShearSheep")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakAcaciaLeaves")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakBirchLeaves")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakCherryLeaves")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDarkOakLeaves")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakJungleLeaves")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakMangroveLeaves")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakOakLeaves")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakPaleOakLeaves")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSpruceLeaves")));
        return challenges;
    }

    private static Challenges AddChallengeToShovel() {
        Challenges challenges = AddChallengeToTool();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakDirt")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakGravel")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSand")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakClay")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSnow")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakMycelium")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakPodzol")));
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakSoulSand")));
        return challenges;
    }
}
