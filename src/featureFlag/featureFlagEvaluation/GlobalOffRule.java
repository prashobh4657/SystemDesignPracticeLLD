package featureFlag.featureFlagEvaluation;

import featureFlag.entities.User;

public class GlobalOffRule implements FeatureFlagEvaluationStrategy {

    @Override
    public boolean isEnable(User user) {
        return false;
    }
}
