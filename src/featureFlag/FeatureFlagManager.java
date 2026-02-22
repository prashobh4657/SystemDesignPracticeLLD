package featureFlag;

import featureFlag.featureFlagEvaluation.FeatureFlagEvaluationStrategy;

import java.util.HashMap;
import java.util.Map;

public class FeatureFlagManager {
    private Map<String, FeatureFlagEvaluationStrategy> featureFlagMap=new HashMap<>();

    public void registerFeature(String featureName, FeatureFlagEvaluationStrategy featureFlagEvaluationStrategy) {
        featureFlagMap.put(featureName, featureFlagEvaluationStrategy);
    }

    public boolean isEnabled(String featureName, String userId) {
        FeatureFlagEvaluationStrategy strategy = featureFlagMap.get(featureName);
        if (strategy == null) {
            return false;
        }
        return strategy.isEnable(userId);
    }
}
