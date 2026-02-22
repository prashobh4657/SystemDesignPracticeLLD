package featureFlag;

import featureFlag.entities.User;
import featureFlag.featureFlagEvaluation.FeatureFlagEvaluationStrategy;

import java.util.HashMap;
import java.util.Map;

public class FeatureFlagManager {
    private static FeatureFlagManager INSTANCE = new FeatureFlagManager();
    private Map<String, FeatureFlagEvaluationStrategy> featureFlagMap = new HashMap<>();

    public static FeatureFlagManager getInstance() {
        return INSTANCE;
    }

    public void registerFeature(String featureName, FeatureFlagEvaluationStrategy featureFlagEvaluationStrategy) {
        featureFlagMap.put(featureName, featureFlagEvaluationStrategy);
    }

    public boolean isEnabled(String featureName, User user) {
        FeatureFlagEvaluationStrategy strategy = featureFlagMap.get(featureName);
        if (strategy == null) {
            return false;
        }
        return strategy.isEnable(user);
    }
}
