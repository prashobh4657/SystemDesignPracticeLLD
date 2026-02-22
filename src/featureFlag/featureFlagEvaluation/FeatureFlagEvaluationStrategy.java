package featureFlag.featureFlagEvaluation;

import featureFlag.entities.User;

public interface FeatureFlagEvaluationStrategy {
    boolean isEnable(User user);
}
