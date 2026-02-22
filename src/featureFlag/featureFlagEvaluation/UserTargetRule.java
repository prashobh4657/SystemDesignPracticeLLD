package featureFlag.featureFlagEvaluation;

import java.util.Collections;
import java.util.Set;

public class UserTargetRule implements FeatureFlagEvaluationStrategy {
    private final Set<String> userIds;

    public UserTargetRule(Set<String> userIds) {
        if (userIds == null) {
            this.userIds = Collections.emptySet();
            return;
        }
        this.userIds = userIds;
    }

    @Override
    public boolean isEnable(String userId) {
        if (userId == null) {
            return false;
        }
        return userIds.contains(userId);
    }

    public Set<String> getUserIds() {
        return userIds;
    }
}
