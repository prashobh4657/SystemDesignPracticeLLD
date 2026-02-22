package votingSystem;

import votingSystem.entities.Candidate;
import votingSystem.entities.Voter;

import java.util.HashSet;
import java.util.Set;

public class VotingSystemDemo {
    public static void run() {
        VotingSystem votingSystem = VotingSystem.getInstance();
        votingSystem.clear();
        registerCandidates(votingSystem);
        registerVoters(votingSystem);
        votingSystem.simulateVoting();
        votingSystem.printLogs();
        votingSystem.printResult();
    }

    private static void registerCandidates(VotingSystem votingSystem) {
        int candidateCount = VotingSystemConstants.CANDIDATE_COUNT;
        if (candidateCount <= 0) {
            throw new IllegalArgumentException("candidateCount must be > 0");
        }

        Set<String> candidateIds = new HashSet<>();
        for (int i = 0; i < candidateCount; i++) {
            String candidateId = "C" + (i + 1);
            if (!candidateIds.add(candidateId)) {
                throw new IllegalStateException("Duplicate candidateId generated: " + candidateId);
            }
            votingSystem.registerCandidate(new Candidate(candidateId, "Candidate-" + candidateId));
        }
    }

    private static void registerVoters(VotingSystem votingSystem) {
        int voterCount = VotingSystemConstants.VOTER_COUNT;
        if (voterCount <= 0) {
            throw new IllegalArgumentException("voterCount must be > 0");
        }

        Set<String> voterIds = new HashSet<>();
        for (int i = 1; i <= voterCount; i++) {
            String voterId = "V" + i;
            if (!voterIds.add(voterId)) {
                throw new IllegalStateException("Duplicate voterId generated: " + voterId);
            }
            votingSystem.registerVoter(new Voter(voterId, "Voter-" + voterId));
        }
    }
}
