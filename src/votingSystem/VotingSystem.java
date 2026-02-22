package votingSystem;

import votingSystem.entities.Candidate;
import votingSystem.entities.VoteLog;
import votingSystem.entities.Voter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VotingSystem {
    private static final VotingSystem INSTANCE = new VotingSystem();

    private final Map<String, Candidate> candidatesById = new ConcurrentHashMap<>();
    private final Map<String, Voter> votersById = new ConcurrentHashMap<>();
    private final Set<String> votersWhoVoted = ConcurrentHashMap.newKeySet();
    private final Queue<VoteLog> voteLogs = new ConcurrentLinkedQueue<>();

    private volatile int lastFailures = 0;
    private volatile boolean lastVotingCompletedInTime = true;

    private VotingSystem() {
    }

    public static VotingSystem getInstance() {
        return INSTANCE;
    }

    public void clear() {
        candidatesById.clear();
        votersById.clear();
        votersWhoVoted.clear();
        voteLogs.clear();
    }

    public void registerCandidate(Candidate candidate) {
        if (candidate == null) {
            throw new IllegalArgumentException("candidate is required");
        }

        String candidateId = candidate.getCandidateId();
        Candidate prev = candidatesById.putIfAbsent(candidateId, candidate);
        if (prev != null) {
            throw new IllegalStateException("Candidate already registered: " + candidateId);
        }
    }

    public void registerVoter(Voter voter) {
        if (voter == null) {
            throw new IllegalArgumentException("voter is required");
        }

        String voterId = voter.getVoterId();
        Voter prev = votersById.putIfAbsent(voterId, voter);
        if (prev != null) {
            throw new IllegalStateException("Voter already registered: " + voterId);
        }
    }

    public void castVote(String voterId, String candidateId) {
        if (voterId == null || voterId.trim().isEmpty()) {
            throw new IllegalArgumentException("voterId is required");
        }
        if (candidateId == null || candidateId.trim().isEmpty()) {
            throw new IllegalArgumentException("candidateId is required");
        }

        Voter voter = votersById.get(voterId);
        if (voter == null) {
            throw new IllegalArgumentException("Unknown voterId: " + voterId);
        }
        Candidate candidate = candidatesById.get(candidateId);
        if (candidate == null) {
            throw new IllegalArgumentException("Unknown candidateId: " + candidateId);
        }

        boolean firstTime = votersWhoVoted.add(voter.getVoterId());
        if (!firstTime) {
            throw new IllegalStateException("Voter has already voted: " + voterId);
        }

        candidate.incrementVote();
        voteLogs.add(new VoteLog(voter.getVoterId(), candidate.getCandidateId(), System.currentTimeMillis()));
    }

    public void simulateVoting() {
        if (candidatesById.isEmpty()) {
            throw new IllegalStateException("No candidates registered");
        }
        if (votersById.isEmpty()) {
            throw new IllegalStateException("No voters registered");
        }

        int threadCount = VotingSystemConstants.THREAD_COUNT;
        validateThreadCount(threadCount);

        votersWhoVoted.clear();
        voteLogs.clear();
        lastFailures = 0;
        lastVotingCompletedInTime = true;

        List<Candidate> candidates = new ArrayList<>(candidatesById.values());
        List<Voter> voters = new ArrayList<>(votersById.values());

        AtomicInteger failures = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (Voter voter : voters) {
            executorService.submit(() -> {
                try {
                    int delayMs = ThreadLocalRandom.current().nextInt(VotingSystemConstants.MAX_VOTE_DELAY_MS + 1);
                    Thread.sleep(delayMs);
                    int idx = ThreadLocalRandom.current().nextInt(candidates.size());
                    Candidate candidate = candidates.get(idx);
                    castVote(voter.getVoterId(), candidate.getCandidateId());
                } catch (Exception e) {
                    failures.incrementAndGet();
                }
            });
        }

        executorService.shutdown();
        boolean done;
        try {
            done = executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            done = false;
        }

        lastFailures = failures.get();
        lastVotingCompletedInTime = done;
    }

    public void printResult() {
        System.out.println("Registered Candidates: " + getRegisteredCandidateCount());
        System.out.println("Registered Voters: " + getRegisteredVoterCount());
        System.out.println("Total votes cast: " + getTotalVotesCast());
        System.out.println("Failed vote attempts: " + lastFailures);
        if (!lastVotingCompletedInTime) {
            System.out.println("Voting did not complete in time");
        }

        System.out.println("Final Results:");
        Map<Candidate, Integer> results = getResults();
        Candidate winner = null;
        for (Map.Entry<Candidate, Integer> entry : results.entrySet()) {
            System.out.println(entry.getKey().getCandidateId() + " (" + entry.getKey().getName() + ") => " + entry.getValue());
            if (winner == null) {
                winner = entry.getKey();
            }
        }
        if (winner != null) {
            System.out.println("Winner: " + winner.getCandidateId() + " (" + winner.getName() + ") with " + winner.getVoteCount() + " votes");
        }
    }

    public void printLogs() {
        System.out.println("Vote Logs:");
        for (VoteLog log : voteLogs) {
            System.out.println(log);
        }
    }

    private void validateThreadCount(int threadCount) {
        if (threadCount <= 0) {
            throw new IllegalArgumentException("threadCount must be > 0");
        }
    }

    public Map<Candidate, Integer> getResults() {
        List<Candidate> candidates = new ArrayList<>(candidatesById.values());
        candidates.sort(
                Comparator.comparingInt(Candidate::getVoteCount).reversed()
                        .thenComparing(Candidate::getCandidateId)
        );

        Map<Candidate, Integer> results = new LinkedHashMap<>();
        for (Candidate c : candidates) {
            results.put(c, c.getVoteCount());
        }
        return results;
    }

    public int getRegisteredCandidateCount() {
        return candidatesById.size();
    }

    public int getRegisteredVoterCount() {
        return votersById.size();
    }

    public int getTotalVotesCast() {
        return votersWhoVoted.size();
    }

    public Collection<Candidate> getCandidates() {
        return Collections.unmodifiableCollection(candidatesById.values());
    }

    public Collection<Voter> getVoters() {
        return Collections.unmodifiableCollection(votersById.values());
    }
}
