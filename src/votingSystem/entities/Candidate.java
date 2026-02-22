package votingSystem.entities;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Candidate {
    private final String candidateId;
    private final String name;
    private final AtomicInteger voteCount = new AtomicInteger(0);

    public Candidate(String candidateId, String name) {
        this.candidateId = candidateId;
        this.name = name;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getName() {
        return name;
    }

    public int getVoteCount() {
        return voteCount.get();
    }

    public void incrementVote() {
        voteCount.incrementAndGet();
    }

}
