package featureFlag.featureFlagEvaluation;

public class GlobalOnRule implements FeatureFlagEvaluationStrategy {

    @Override
    public boolean isEnable(String userId) {
        return true;
    }
}
