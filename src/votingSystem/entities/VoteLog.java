package votingSystem.entities;

import java.util.Date;
import java.text.SimpleDateFormat;

public class VoteLog {
    private final String voterId;
    private final String candidateId;
    private final long timestamp;

    public VoteLog(String voterId, String candidateId, long timestamp) {
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.timestamp = timestamp;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return "Vote cast by " + voterId + " for candidate " + candidateId +
                " at " + sdf.format(new Date(timestamp));
    }
}
