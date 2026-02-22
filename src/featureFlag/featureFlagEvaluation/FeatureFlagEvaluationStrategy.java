package featureFlag.featureFlagEvaluation;

public interface FeatureFlagEvaluationStrategy {
    boolean isEnable(String userId);
}
