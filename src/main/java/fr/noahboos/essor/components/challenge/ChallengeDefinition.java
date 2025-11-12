package fr.noahboos.essor.components.challenge;

import fr.noahboos.essor.utils.JsonLoader;

import java.util.List;

public class ChallengeDefinition {
    private String id;
    private int maximumTier;
    private List<Integer> thresholds;
    private List<String> targets;

    public ChallengeDefinition(String path) {
        ChallengeDefinition buffer = JsonLoader.LoadChallengeDefinition(path);

        if (buffer == null) {
            System.err.println("Failed to load challenge definition data from " + path);
            return;
        }

        this.id = buffer.id;
        this.maximumTier = buffer.maximumTier;
        this.thresholds = buffer.thresholds;
        this.targets = buffer.targets;
    }

    public String GetId() {
        return id;
    }

    public int GetMaximumTier() {
        return maximumTier;
    }

    public List<Integer> GetThresholds() {
        return thresholds;
    }

    public List<String> GetTargets() {
        return targets;
    }
}
