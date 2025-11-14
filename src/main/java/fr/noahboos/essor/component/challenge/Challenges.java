package fr.noahboos.essor.component.challenge;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.registry.EssorRegistry;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Challenges {
    public List<ChallengeProgress> challenges;

    public Challenges() {
        this.challenges = new ArrayList<>();
    }

    public Challenges(List<ChallengeProgress> challenges) {
        this.challenges = challenges;
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
                ProgressionManager.IncrementChallenge(challenge, definition);
                ProgressionManager.LevelUpChallenge(item, challenge, definition);
            }
        }
    }
}
