package fr.noahboos.essor.component.challenge;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.registry.EssorRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Challenges {
    public List<ChallengeProgress> challenges;

    public Challenges() {
        this.challenges = new ArrayList<>();
    }

    public Challenges(List<ChallengeProgress> challenges) {
        this.challenges = new ArrayList<>(challenges);
    }

    public List<ChallengeProgress> GetChallenges() {
        return this.challenges;
    }

    public static void AttemptToLevelUpChallenges(ItemStack item, String id) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        for (var challenge : data.GetChallenges().challenges) {
            ChallengeDefinition definition = EssorRegistry.CHALLENGE_DEFINITION_MAP.get(challenge.GetId());
            if (challenge.IsTarget(id) || definition.GetTargets().isEmpty()) {
                ProgressionManager.IncrementChallenge(challenge, definition, 1);
                ProgressionManager.LevelUpChallenge(item, challenge, definition);
            }
        }
    }

    public static void AttemptToLevelUpChallenges(ItemStack item, int progress) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        for (var challenge : data.GetChallenges().challenges) {
            if (challenge.GetDefinition().GetTargets().isEmpty()) {
                ProgressionManager.IncrementChallenge(challenge, challenge.GetDefinition(), progress);
                ProgressionManager.LevelUpChallenge(item, challenge, challenge.GetDefinition());
            }
        }
    }

    public static void AttemptToLevelUpChallenges(ItemStack item, String id, int progress) {
        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
        if (data == null) return;
        for (var challenge : data.GetChallenges().challenges) {
            ChallengeDefinition definition = EssorRegistry.CHALLENGE_DEFINITION_MAP.get(challenge.GetId());
            if (challenge.IsTarget(id) || definition.GetTargets().isEmpty()) {
                ProgressionManager.IncrementChallenge(challenge, definition, progress);
                ProgressionManager.LevelUpChallenge(item, challenge, definition);
            }
        }
    }
}
