package fr.noahboos.essor.component.challenge;

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
}
