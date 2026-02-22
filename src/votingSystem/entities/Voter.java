package votingSystem.entities;

public class Voter {
    private final String voterId;
    private final String name;

    public Voter(String voterId, String name) {
        this.voterId = voterId;
        this.name = name;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getName() {
        return name;
    }
}
