package fr.noahboos.essor.component.challenge;

import fr.noahboos.essor.Essor;
import fr.noahboos.essor.util.JsonLoader;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ChallengeDefinition {
    private String id;
    private String iconId;
    private ResourceLocation icon;
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
        if (buffer.iconId != null) {
            this.icon = ResourceLocation.fromNamespaceAndPath(Essor.MODID, buffer.iconId);
        } else {
            System.err.println("Challenge definition " + this.id + " has no icon defined");
        }
        this.maximumTier = buffer.maximumTier;
        this.thresholds = buffer.thresholds;
        this.targets = buffer.targets;
    }

    public String GetId() {
        return id;
    }

    public ResourceLocation GetIcon() {
        return icon;
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
