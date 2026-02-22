package featureFlag.featureFlagEvaluation;

public class PercentageRolloutRule implements FeatureFlagEvaluationStrategy {
    private final int percentage;

    public PercentageRolloutRule(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    @Override
    public boolean isEnable(String userId) {
        if (percentage == 0) {
            return false;
        }
        if (percentage == 100) {
            return true;
        }
        if (userId == null) {
            return false;
        }
        int bucket = stableBucket(userId);
        return bucket < percentage;
    }

    private int stableBucket(String userId) {
        int h = userId.hashCode();
        return Math.floorMod(h, 100);
    }

    public int getPercentage() {
        return percentage;
    }
}
