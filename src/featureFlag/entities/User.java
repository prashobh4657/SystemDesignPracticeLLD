package featureFlag.entities;

public class User {
    private final String userId;
    private final String cityLocation;

    public User(String userId, String cityLocation) {
        this.userId = userId;
        this.cityLocation = cityLocation;
    }

    public String getUserId() {
        return userId;
    }

    public String getCityLocation() {
        return cityLocation;
    }
}
