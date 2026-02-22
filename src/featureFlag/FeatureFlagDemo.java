package featureFlag;

import featureFlag.entities.User;
import featureFlag.featureFlagEvaluation.GlobalOnRule;
import featureFlag.featureFlagEvaluation.LocationBasedRule;
import featureFlag.featureFlagEvaluation.PercentageRolloutRule;
import featureFlag.featureFlagEvaluation.UserTargetRule;

import java.util.*;

public class FeatureFlagDemo {
    public static void run() {
        FeatureFlagManager featureFlagManager = FeatureFlagManager.getInstance();
        User user1 = new User(FeatureFlagConstants.USER_ID_1, FeatureFlagConstants.CITY_BENGALURU);
        User user2 = new User(FeatureFlagConstants.USER_ID_2, FeatureFlagConstants.CITY_MUMBAI);
        User user3 = new User(FeatureFlagConstants.USER_ID_3, FeatureFlagConstants.CITY_DELHI);

        Set<String> allowedUsers = new HashSet<>();
        allowedUsers.add(FeatureFlagConstants.USER_ID_1);
        allowedUsers.add(FeatureFlagConstants.USER_ID_2);

        featureFlagManager.registerFeature(FeatureFlagConstants.FEATURE_BETA_DASHBOARD, new UserTargetRule(allowedUsers));

        System.out.println(FeatureFlagConstants.FEATURE_BETA_DASHBOARD + " for " + user1.getUserId() + " => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_BETA_DASHBOARD, user1));
        System.out.println(FeatureFlagConstants.FEATURE_BETA_DASHBOARD + " for " + user2.getUserId() + " => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_BETA_DASHBOARD, user2));
        System.out.println(FeatureFlagConstants.FEATURE_BETA_DASHBOARD + " for " + user3.getUserId() + " => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_BETA_DASHBOARD, user3));

        featureFlagManager.registerFeature(FeatureFlagConstants.FEATURE_GLOBAL_SEARCH, new GlobalOnRule());

        System.out.println(FeatureFlagConstants.FEATURE_GLOBAL_SEARCH + " for " + user1.getUserId() + " => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_GLOBAL_SEARCH, user1));

        featureFlagManager.registerFeature(FeatureFlagConstants.FEATURE_NEW_CHECKOUT, new PercentageRolloutRule(30));
        System.out.println(FeatureFlagConstants.FEATURE_NEW_CHECKOUT + " for " + user1.getUserId() + " => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_NEW_CHECKOUT, user1));
        System.out.println(FeatureFlagConstants.FEATURE_NEW_CHECKOUT + " for " + user2.getUserId() + " => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_NEW_CHECKOUT, user2));
        System.out.println(FeatureFlagConstants.FEATURE_NEW_CHECKOUT + " for " + user3.getUserId() + " => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_NEW_CHECKOUT, user3));

        Set<String> offerCities = new HashSet<>();
        offerCities.add(FeatureFlagConstants.CITY_BENGALURU);
        offerCities.add(FeatureFlagConstants.CITY_MUMBAI);
        featureFlagManager.registerFeature(FeatureFlagConstants.FEATURE_LOCATION_BASED_OFFER, new LocationBasedRule(offerCities));
        System.out.println(FeatureFlagConstants.FEATURE_LOCATION_BASED_OFFER + " for " + user1.getUserId() + "(" + user1.getCityLocation() + ") => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_LOCATION_BASED_OFFER, user1));
        System.out.println(FeatureFlagConstants.FEATURE_LOCATION_BASED_OFFER + " for " + user2.getUserId() + "(" + user2.getCityLocation() + ") => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_LOCATION_BASED_OFFER, user2));
        System.out.println(FeatureFlagConstants.FEATURE_LOCATION_BASED_OFFER + " for " + user3.getUserId() + "(" + user3.getCityLocation() + ") => " + featureFlagManager.isEnabled(FeatureFlagConstants.FEATURE_LOCATION_BASED_OFFER, user3));
    }
}
