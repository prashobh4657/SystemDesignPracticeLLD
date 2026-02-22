package featureFlag.featureFlagEvaluation;

import featureFlag.entities.User;

import java.util.Collections;
import java.util.Set;

public class LocationBasedRule implements FeatureFlagEvaluationStrategy {
    private final Set<String> cities;

    public LocationBasedRule(Set<String> cities) {
        this.cities = cities == null ? Collections.emptySet() : cities;
    }

    @Override
    public boolean isEnable(User user) {
        if (user == null) {
            return false;
        }
        String cityLocation = user.getCityLocation();
        if (cityLocation == null) {
            return false;
        }
        return cities.contains(cityLocation);
    }

    public Set<String> getCities() {
        return cities;
    }
}
