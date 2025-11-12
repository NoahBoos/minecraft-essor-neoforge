package fr.noahboos.essor.component.challenge;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.registry.EssorRegistry;
import net.minecraft.world.item.*;

public class ChallengesFactory {
    public static void AssignChallenges(ItemStack item) {
        if (!EquipmentLevelingData.CHALLENGEABLE_ITEM_CLASSES.contains(item.getItem().getClass())) return;

        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());

        if (data == null || (data.GetChallenges() != null && !data.GetChallenges().challenges.isEmpty())) return;

        Item type = item.getItem();

        switch (type) {
            case AxeItem axeItem -> data.SetChallenges(AddChallengeToAxe());
            case BowItem bowItem -> data.SetChallenges(AddChallengeToRangedWeapon());
            case CrossbowItem crossbowItem -> data.SetChallenges(AddChallengeToRangedWeapon());
            case HoeItem hoeItem -> data.SetChallenges(AddChallengeToHoe());
            case MaceItem maceItem -> data.SetChallenges(AddChallengeToWeapon());
            case PickaxeItem pickaxeItem -> data.SetChallenges(AddChallengeToPickaxe());
            case ShovelItem shovelItem -> data.SetChallenges(AddChallengeToShovel());
            case ShieldItem shieldItem -> data.SetChallenges(AddChallengeToWeapon());
            case SwordItem swordItem -> data.SetChallenges(AddChallengeToWeapon());
            case TridentItem tridentItem -> data.SetChallenges(AddChallengeToTrident());
            default -> {}
        }
    }

    private static Challenges AddBossChallenges() {
        Challenges challenges = new Challenges();
        return challenges;
    }

    private static Challenges AddChallengeToWeapon() {
        Challenges challenges = AddBossChallenges();
        return challenges;
    }

    private static Challenges AddChallengeToRangedWeapon() {
        Challenges challenges = AddChallengeToWeapon();
        return challenges;
    }

    private static Challenges AddChallengeToTrident() {
        Challenges challenges = AddChallengeToWeapon();
        return challenges;
    }

    private static Challenges AddChallengeToTool() {
        Challenges challenges = new Challenges();
        challenges.challenges.add(new ChallengeProgress(EssorRegistry.CHALLENGE_DEFINITION_MAP.get("Essor:Challenge:BreakBlocks")));
        return challenges;
    }

    private static Challenges AddChallengeToPickaxe() {
        Challenges challenges = AddChallengeToTool();
        return challenges;
    }

    private static Challenges AddChallengeToAxe() {
        Challenges challenges = AddChallengeToTool();
        return challenges;
    }

    private static Challenges AddChallengeToShovel() {
        Challenges challenges = AddChallengeToTool();
        return challenges;
    }

    private static Challenges AddChallengeToHoe() {
        Challenges challenges = AddChallengeToTool();
        return challenges;
    }
}
