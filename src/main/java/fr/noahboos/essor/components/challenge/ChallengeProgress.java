package fr.noahboos.essor.components.challenge;

public class ChallengeProgress {
    private String id;
    private int currentTier;
    private int progress;

    public ChallengeProgress(ChallengeDefinition definition) {
        this.id = definition.GetId();
        this.currentTier = 0;
        this.progress = 0;
    }

    public ChallengeProgress(String id, int currentTier, int progress) {
        this.id = id;
        this.currentTier = currentTier;
        this.progress = progress;
    }

    public String GetId() {
        return this.id;
    }

    public int GetCurrentTier() {
        return this.currentTier;
    }
    public void SetCurrentTier(int currentTier) {
        this.currentTier = currentTier;
    }

    public int GetProgress() {
        return this.progress;
    }
    public void SetProgress(int progress) {
        this.progress = progress;
    }


}
