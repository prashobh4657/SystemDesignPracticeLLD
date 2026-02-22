package featureFlag.featureFlagEvaluation;

import featureFlag.entities.User;

public class GlobalOnRule implements FeatureFlagEvaluationStrategy {

    @Override
    public boolean isEnable(User user) {
        return true;
    }
}
