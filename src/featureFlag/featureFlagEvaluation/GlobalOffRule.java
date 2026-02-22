package featureFlag.featureFlagEvaluation;

public class GlobalOffRule implements FeatureFlagEvaluationStrategy {

    @Override
    public boolean isEnable(String userId) {
        return false;
    }
}
