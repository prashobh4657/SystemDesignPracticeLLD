package featureFlag;

import featureFlag.featureFlagEvaluation.GlobalOnRule;
import featureFlag.featureFlagEvaluation.PercentageRolloutRule;
import featureFlag.featureFlagEvaluation.UserTargetRule;

import java.util.*;

public class FeatureFlagDemo {
    public static void run() {
        FeatureFlagManager featureFlagManager = new FeatureFlagManager();
        Set<String> allowedUsers = new HashSet<>();
        allowedUsers.add("u1");
        allowedUsers.add("u2");

        featureFlagManager.registerFeature("F1", new UserTargetRule(allowedUsers));

        System.out.println("F1 for u1 => " + featureFlagManager.isEnabled("F1", "u1"));
        System.out.println("F1 for u2 => " + featureFlagManager.isEnabled("F1", "u2"));
        System.out.println("F1 for u3 => " + featureFlagManager.isEnabled("F1", "u3"));

        featureFlagManager.registerFeature("F2", new GlobalOnRule());

        System.out.println("F2 for u1 => " + featureFlagManager.isEnabled("F2", "u1"));

        featureFlagManager.registerFeature("F3", new PercentageRolloutRule(30));
        System.out.println("F3 for u1 => " + featureFlagManager.isEnabled("F3", "u1"));
        System.out.println("F3 for u2 => " + featureFlagManager.isEnabled("F3", "u2"));
        System.out.println("F3 for u3 => " + featureFlagManager.isEnabled("F3", "u3"));
    }
}
